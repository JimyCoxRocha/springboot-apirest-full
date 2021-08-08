package com.mdw.replica.entities.http;

public enum MessageEntity {
	NOT_USER(
			"ERROR: No se encontró información de usuario ingresado"),
	NOT_PROFILE(
			"ERROR: No se puede encontrar perfil(es), para el usuario ingresado, por favor intente nuevamente"),
	NO_DATA(
			"No se encontró información, por favor, revise nuevamente"),
	INVALID_TOKEN(
			"El token es inválido, por favor, revise el token nuevamente");
	
	private String message;
	
	private MessageEntity(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
