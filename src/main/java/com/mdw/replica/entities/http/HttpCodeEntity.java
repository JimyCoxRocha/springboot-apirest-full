package com.mdw.replica.entities.http;

public enum HttpCodeEntity {
	OK(200), BAD_REQUEST(400), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404), INTERNAL_SERVER_ERROR(500),
	BAD_GATEWAY(502), SERVICE_UNAVAILABLE(503), GATEWAY_TIMEOUT(504);
	
	private int code;
	
	private HttpCodeEntity(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
