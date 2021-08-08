package com.mdw.replica.entities.utilities;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SelectorEntity {
	private Map<String, Object> operator = new HashMap<String, Object>();
	
	public SelectorEntity(String operator, Object value){
		this.operator.put(operator, value);
	}
	
	public SelectorEntity(String operator1, Object value1, String operator2, Object value2) {
		this.operator.put(operator1, value1);
		this.operator.put(operator2, value2);
	}
	
	public void setOperator(String key, String value) {
		this.operator.put(key, value);
	}
}
