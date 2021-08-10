package com.mdw.replica.entities.http;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RSEntity<T> {

	private Boolean error = false;
	private String typeError;
	private Integer numRows;
	private List<String> messages;
	private T data;
	
	public RSEntity(String typeError, String message) {
		this.error = true;
		this.messages = new ArrayList<String>();
		this.messages.add(message);
		this.typeError = typeError;
		this.numRows = 0;
	}
	
	public RSEntity(String typeError, List<String> messages) {
		this.error = true;
		this.messages = messages;
		this.typeError = typeError;
		this.numRows = 0;
	}
	
	public RSEntity(String message) {
		this.setOk();
		this.error = false;
		this.typeError = "";
		this.numRows = 0;
	}
	
	public RSEntity(T data, Integer numRows) {
		this.error = false;
		this.setOk();
		this.typeError = "";
		this.numRows = numRows;
		this.data = data;
	}
	
	private void setOk() {
		this.messages = new ArrayList<String>();
		this.messages.add("Procesos Ejecutado correctamente");
	}
	
	public ResponseEntity<RSEntity<T>> send() {
		return new ResponseEntity<RSEntity<T>>(this, HttpStatus.OK);
	}
	
	public ResponseEntity<RSEntity<T>> send(RSExceptionEntity e) {
		RSEntity<T> aux = new RSEntity<>(this.getTypeError(e.getCode()), e.getMessages());
		return new ResponseEntity<RSEntity<T>>(aux, e.getCode());
	}
	
	public void setMessages(String message) {
		this.messages.add(message);
	}
	
	private String getTypeError(HttpStatus code) {
		Integer codeHttp = code.value();
		
		if(codeHttp > 300 && codeHttp < 500) {
			return "CLIENT";
		}else {
			return "SYSTEM";
		}
	}
}
