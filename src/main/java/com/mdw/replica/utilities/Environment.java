package com.mdw.replica.utilities;

import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;

public class Environment {

	public final static String v1 = "/v1";
	
	//token
	public final static AesKey key = new AesKey(ByteUtil.randomBytes(16));
	
	public final static String contentType = "application/json";
	public final static String characterEncoding = "UTF-8";
	
	public final static String pathControllerSecurity = v1 + "/securitys";
	public final static String pathControllerReporteLog = v1 + "/reports";
	
	public final static String pathControllerModule = v1 + "/modules";
	public final static String pathControllerService = v1 + "/services";
	public final static String pathControllerEndPoints = v1 + "/endpoints";
	
	
}
