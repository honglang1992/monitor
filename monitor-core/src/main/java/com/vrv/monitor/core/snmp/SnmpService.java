package com.vrv.monitor.core.snmp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SnmpService {
	
	public boolean getCanCall();
	
	public Object oid(String oid) throws IOException;
	
	public Map<String, Object> oids(String[] oids, String[] names) throws IOException;
	
	public List<Map<String, Object>> oidTables(String[] origOid, String[] inputOid, String[] names, int step) throws IOException;
	
	public void close();
}
