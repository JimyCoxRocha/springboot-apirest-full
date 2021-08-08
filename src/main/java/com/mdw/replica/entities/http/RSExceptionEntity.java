package com.mdw.replica.entities.http;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

public class RSExceptionEntity extends Exception{
	
	private static final long serialVersionUID = 1L;
	public ArrayList<String> messages;
	public final HttpStatus code;
	
	public RSExceptionEntity(String m, HttpStatus code) {
		super(m);
		this.messages = new ArrayList<>();
		this.messages.add(m);
		this.code = code;
	}
	
	public RSExceptionEntity(ArrayList<String> m, HttpStatus code) {
		super(String.join(",", m));
		this.messages = m;
		this.code = code;
	}
	
	public RSExceptionEntity(ArrayList<String> m, Integer code) {
		super(String.join(",", m));
		this.messages = m;
		this.code = this.get(code);
	}
	
	public ArrayList<String> getMessages(){
		return this.messages;
	}
	
	public void setMessage(ArrayList<String> messages){
		this.messages = messages;
	}
	
	public HttpStatus getCode() {
		return code;
	}
	
	private HttpStatus get(Integer code) {
		HttpStatus[] http = HttpStatus.values();
		HttpStatus e = null;
		
		for(HttpStatus item: http) {
			if(item.value() == code) {
				e = item;
				break;
			}
		}
		return e;
	}
	
}
