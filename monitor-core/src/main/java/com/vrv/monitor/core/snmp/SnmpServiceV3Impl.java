package com.vrv.monitor.core.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.RetrievalEvent;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.*;

public class SnmpServiceV3Impl implements SnmpService {

	private final static Logger logger = LoggerFactory.getLogger(SnmpServiceV3Impl.class);
	/**
	 * 安全级别 机密(AuthNoPriv/AuthPriv) 不加密(NoAuthNoPriv)
	 */
	private String securitylevel;
	private Snmp snmp = null;
	private TransportMapping<?> transport;
	private boolean canCall = true;
	private Address targetAddress = null;
	private OctetString securityNameOctet = null;

	public boolean getCanCall() {
		return canCall;
	}

	public SnmpServiceV3Impl(String ip, int port, String securitylevel, String securityName, String authProtocol, String authPassphrase, String privProtocol,
                             String privacyPassphrase) {
		this.securityNameOctet = new OctetString(securityName);
		this.securitylevel = securitylevel;
		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);
			USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
			SecurityModels.getInstance().addSecurityModel(usm);
			transport.listen();

			OctetString octetauthPassphrase = new OctetString(authPassphrase);
			OctetString octetprivacyPassphrase = new OctetString(privacyPassphrase);
			OID authProtocolOid = SnmpServiceV3Impl.getAuthProtocol(authProtocol);
			OID privProtocolOid = SnmpServiceV3Impl.getPrivProtocol(privProtocol);
			UsmUser user = new UsmUser(securityNameOctet, authProtocolOid, octetauthPassphrase, privProtocolOid, octetprivacyPassphrase);
			snmp.getUSM().addUser(new OctetString(securityName), user);
			// targetAddress = new UdpAddress("192.168.118.241/161");
			targetAddress = GenericAddress.parse("UDP:" + ip + "/" + port + "");
		} catch (IOException e) {
			canCall = false;
		}
	}

	public Object oid(String oid) {
		PDU pdu = new ScopedPDU();
		pdu.setType(PDU.GET);
		pdu.add(new VariableBinding(new OID(oid)));
		String[] oids = { oid };
		Vector<? extends VariableBinding> recVBs = this.getResponseVariable(oids, pdu);
		if (recVBs != null) {
			if (recVBs.size() == 1) {
				Variable recVB = recVBs.elementAt(0).getVariable();
				return recVB;
			}
		}
		return null;
	}

	public Map<String, Object> oids(String[] oids, String[] names) {
		Map<String, Object> map = new HashMap<String, Object>();
		PDU pdu = new ScopedPDU();
		pdu.setType(PDU.GET);
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		Vector<? extends VariableBinding> recVBs = this.getResponseVariable(oids, pdu);
		if (recVBs != null) {
			if (recVBs.size() > 1) {
				for (int i = 0; i < recVBs.size(); i++) {
					VariableBinding recVB = recVBs.elementAt(i);
					map.put(names[i], recVB.getVariable());
				}
			} else if (recVBs.size() == 1) {
				VariableBinding recVB = recVBs.elementAt(0);
				map.put(names[0], recVB.getVariable());
			}
		}
		return map;
	}

	/**
	 * 得到加密的oid
	 */
	private static OID getAuthProtocol(String authProtocol) {
		OID _authProtocol = AuthMD5.ID;
		if (authProtocol.equalsIgnoreCase("MD5")) {
			_authProtocol = AuthMD5.ID;
		} else if (authProtocol.equalsIgnoreCase("SHA")) {
			_authProtocol = AuthSHA.ID;
		}
		return _authProtocol;
	}

	/**
	 * 得到加密的oid
	 */
	private static OID getPrivProtocol(String privProtocol) {
		OID _privProtocol = PrivDES.ID;
		if (privProtocol.equalsIgnoreCase("DES")) {
			_privProtocol = PrivDES.ID;
		} else if ((privProtocol.equalsIgnoreCase("AES128")) || (privProtocol.equalsIgnoreCase("AES"))) {
			_privProtocol = PrivAES128.ID;
		} else if (privProtocol.equalsIgnoreCase("AES192")) {
			_privProtocol = PrivAES192.ID;
		} else if (privProtocol.equalsIgnoreCase("AES256")) {
			_privProtocol = PrivAES256.ID;
		}
		return _privProtocol;
	}

	/**
	 * 得到认证权限
	 */
	private static Integer getSecurityLevel(String authLevel) {
		Integer securityLevel = null;
		if (authLevel.equalsIgnoreCase("NoAuthNoPriv")) {
			securityLevel = SecurityLevel.NOAUTH_NOPRIV;
		} else if (authLevel.equalsIgnoreCase("AuthNoPriv")) {
			securityLevel = SecurityLevel.AUTH_NOPRIV;
		} else {
			securityLevel = SecurityLevel.AUTH_PRIV;
		}
		return securityLevel;
	}

	public Vector<? extends VariableBinding> getResponseVariable(String[] oids, PDU pdu) {
		ResponseEvent respEvnt = null;
		Target target = this.getV3Target();
		try {
			respEvnt = snmp.send(pdu, target);
		} catch (IOException e) {
			String key = Arrays.toString(oids);
			logger.error("snmp获取oid的值失败!!", e);
			return null;
		}
		if (respEvnt != null) {
			PDU response = respEvnt.getResponse();
			if (response != null && response.getErrorStatus() == PDU.noError) {
				Vector<? extends VariableBinding> recVBs = response.getVariableBindings();
				return recVBs;
			} else {
				logger.error("Error:" + response.getErrorStatusText());
			}

		} else {
			logger.error("TimeOut...");
		}
		return null;
	}

	private Target getV3Target() {
		Target target = new UserTarget();
		target.setVersion(SnmpConstants.version3);
		target.setAddress(targetAddress);
		target.setSecurityLevel(SnmpServiceV3Impl.getSecurityLevel(securitylevel));
		target.setSecurityName(securityNameOctet);
		target.setTimeout(1500);
		target.setRetries(2);
		return target;
	}

	@Override
	/**
	 * 通过oid，探索它以下的oid的值 utils.getTable中的参数代表的是lowerBoundIndex, upperBoundIndex
	 * 如果为空，则表示获取所有的(If not null, all returned rows have an index in a range)
	 * utils.getTable(target, columnOids, new OID("3"), new OID("10"))
	 * 
	 * 假如oid的值为1.3.6.1.2.1.1.9.1.2 new OID("3"), new OID("10")
	 * 表示的是一个范围，lowerBoundIndex和upperBoundIndex的索引 那么钻取的值从oid (
	 * 1.3.6.1.2.1.1.9.1.2.4 ~~ 1.3.6.1.2.1.1.9.1.2.9);
	 * 
	 * 如果包含多个oid则会钻取多个值
	 */
	public List<Map<String, Object>> oidTables(String[] origOid, String[] inputOid, String[] names, int step) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// GETNEXT or GETBULK
		OctetString contextEngineId = new OctetString("0002651100[02]");
		TableUtils utils = new TableUtils(snmp, new MyDefaultPDUFactory(PDU.GETNEXT, contextEngineId));
		// only for GETBULK, set max-repetitions,
		utils.setMaxNumRowsPerPDU(2);
//		OID[] columnOids = new OID[] { new OID("1.3.6.1.2.1.2.2.1.1"), new OID("1.3.6.1.2.1.2.2.1.2"), new OID("1.3.6.1.2.1.2.2.1.3"),
//				new OID("1.3.6.1.2.1.2.2.1.8") }; 
//		names = new String[] { SysConstants.INTERFACE_IFINDEX, SysConstants.INTERFACE_NAME, SysConstants.INTERFACE_TYPE,
//		SysConstants.INTERFACE_OPERATE_STATUS };
		OID[] columnOids = null;
		if (origOid != null && origOid.length > 0) {
			columnOids = new OID[origOid.length];
			for (int index = 0; index < origOid.length; index++) {
				columnOids[index] = new OID(origOid[index]); 
			}
		} else {
			return result;
		}
		

		Target target = this.getV3Target();
		List<TableEvent> list = utils.getTable(target, columnOids, new OID("0"), new OID("10"));
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> rowObj = new HashMap<String, Object>();
			TableEvent table = list.get(i);
			if (table != null && table.getStatus() == RetrievalEvent.STATUS_OK) {
				VariableBinding[] columns = table.getColumns();
				for (int colindex = 0; colindex < columnOids.length; colindex++) {
					VariableBinding column = columns[colindex];
					if (column != null) {
						String name = names[colindex];
						Variable variable = column.getVariable();
						rowObj.put(name, variable);
					}
				}
				if (rowObj.keySet() != null && rowObj.keySet().size() > 0) {
					result.add(rowObj);
				}
			}
		}
		return result;
	}

	private static class MyDefaultPDUFactory extends DefaultPDUFactory {
		private OctetString contextEngineId = null;

		public MyDefaultPDUFactory(int pduType, OctetString contextEngineId) {
			super(pduType);
			this.contextEngineId = contextEngineId;
		}

		@Override
		public PDU createPDU(Target target) {
			PDU pdu = super.createPDU(target);
			if (target.getVersion() == SnmpConstants.version3) {
				((ScopedPDU) pdu).setContextEngineID(contextEngineId);
			}
			return pdu;
		}
	}

	@Override
	public void close() {
		try {
			if (snmp != null) {
				snmp.close();
			}
			if (transport != null) {
				transport.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String ip = "192.168.118.241";
		int port = 161;
		String securitylevel = "authpriv"; // NoAuthNoPriv/AuthNoPriv/AuthPriv
		String securityName = "test";
		String authProtocol = "MD5";
		String authPassphrase = "testtest";
		String privProtocol = "DES";
		String privacyPassphrase = "testtest";

		SnmpServiceV3Impl v3 = new SnmpServiceV3Impl(ip, port, securitylevel, securityName, authProtocol, authPassphrase, privProtocol, privacyPassphrase);
		Object result = v3.oid("1.3.6.1.2.1.1.3.0");
		StringBuilder sb = new StringBuilder();
		String[] arr = result.toString().trim().split(" ");
		if (arr.length >= 3) {
			String[] time = arr[2].split(":");
			sb.append(arr[0]).append("天").append(time[0]).append("小时").append(time[1]).append("分钟").append(time[2]).append("秒");
		} else {
			String[] time = arr[0].split(":");
			sb.append(time[0]).append("小时").append(time[1]).append("分钟").append(time[2]).append("秒");
		}
		System.out.println(sb.toString());
		System.out.println("------------------------------1.------------------------------------");
		String[] oids = { "1.3.6.1.2.1.1.3.0", "1.3.6.1.2.1.1.5.0" };
		String[] names = { "sysUpTime", "sysName" };
		Map<String, Object> oids2 = v3.oids(oids, names);
		for (Iterator<?> it = oids2.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object value = oids2.get(key);
			System.out.println(key + ": " + value);
		}
		System.out.println("-----------------2.-------------------------------------------------");
		List<Map<String, Object>> oidTables = v3.oidTables(oids, oids, names, 10);

	}
}
