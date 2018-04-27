package com.vrv.monitor.core.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.smi.*;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.*;

public class SnmpServiceV2Impl implements SnmpService {
	private final static Logger logger = LoggerFactory.getLogger(SnmpServiceV2Impl.class);
	private Snmp snmp = null;
	private Address targetAddress = null;
	private TransportMapping transport;
	private boolean canCall = true;
	private String publicString = "public";
	
	public boolean getCanCall(){
		return canCall;
	}
	
	public SnmpServiceV2Impl(String ip, int port, String pubkey) {
		try {
			transport = new DefaultUdpTransportMapping();
		} catch (IOException e) {
			logger.error("com.vrv.monitor.snmp init io error",e);
		}
		snmp = new Snmp(transport);
		try {
			transport.listen();
		} catch (IOException e) {
			canCall = false;
		}
		targetAddress = GenericAddress.parse("UDP:"+ip+"/"+port+"");
		this.publicString = pubkey;
	}
	
	public Object oid(String oid) throws IOException {
		CommunityTarget target = new CommunityTarget();
		target.setAddress(targetAddress);
		target.setCommunity(new OctetString(this.publicString));
		// 超时时间
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);

		PDU pdu = new PDU();
		pdu.add(new VariableBinding(new OID(oid)));
		pdu.setType(PDU.GET);
		ResponseEvent respEvnt = snmp.send(pdu, target);

		if (respEvnt != null && respEvnt.getResponse() != null) {
			PDU response = respEvnt.getResponse();
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			if(recVBs.size() == 1){
				return recVBs.elementAt(0).getVariable();
			}
		}
		return null;
	}
	
	public Map<String, Object> oids(String[] oids, String[] names) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置 target
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(this.publicString));
		target.setAddress(targetAddress);
		// 超时时间
		target.setTimeout(2000);   // 有可能时而刷不出出来，timeout的时间引起来的
		target.setVersion(SnmpConstants.version2c);
		// 创建 PDU
		PDU pdu = new PDU();
		for (String oid : oids) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		pdu.setType(PDU.GET);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		if (respEvnt != null && respEvnt.getResponse() != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			if (recVBs != null) {
				for (int i = 0; i < recVBs.size(); i++) {
					VariableBinding recVB = recVBs.elementAt(i);
					map.put(names[i], recVB.getVariable());
				}
			}
		}
		return map;
	}
	
	public List<Map<String, Object>> oidTables(String[] origOid, String[] inputOid, String[] names, int step) throws IOException{
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(this.publicString));
		target.setAddress(targetAddress);
		// 超时时间
		target.setTimeout(1500);
		// 创建 PDU
		PDU pdu = new PDU();
		for (String oid : inputOid) {
			pdu.add(new VariableBinding(new OID(oid)));
		}
		target.setVersion(SnmpConstants.version2c);
		pdu.setType(PDU.GETBULK);
		pdu.setMaxRepetitions(step);
		pdu.setNonRepeaters(0);
		// 向Agent发送PDU，并接收Response
		ResponseEvent respEvnt = snmp.send(pdu, target);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (respEvnt != null && respEvnt.getResponse() != null) {
			Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse().getVariableBindings();
			if (recVBs != null) {
				int colSize = inputOid.length;
				int getSize = recVBs.size();
				int realStep = getSize / colSize;
				for (int i = 0; i < realStep; i++) {
					// 判断是否已越界
					VariableBinding rowIcol1 = recVBs.get(i * colSize + 0);
					if (!rowIcol1.getOid().toString().contains(origOid[0])) {
						return result;
					} else {
						Map<String, Object> rowObj = new HashMap<String, Object>();
						for (int j = 0; j < colSize; j++) {
							// i:行；j:列
							rowObj.put(names[j], recVBs.get(i * colSize + j).getVariable());
						}
						result.add(rowObj);

						//如果第十行还有数据存在，那么说明这个oid表还没遍历完，那就把最后一行的oid设为inputid 继续遍历
						if (i == realStep - 1) {
							for (int j = 0; j < colSize; j++) {
								inputOid[j] = recVBs.get(i * colSize + j).getOid().toString();
							}
						}
					}
				}
			}
		}else{
			return result;
		}
		// 递归出所有的结果
		List<Map<String, Object>> tables = oidTables(origOid, inputOid, names, step);
		result.addAll(tables);

		return result;
	}

	@Override
    public void close() {
        try {
            if(snmp!=null){
                snmp.close();
            }
            if(transport!=null){
                transport.close();
            }
        } catch (IOException e) {
			logger.error("com.vrv.monitor.snmp close false",e);
        }
    }
}
