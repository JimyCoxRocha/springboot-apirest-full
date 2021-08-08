package com.mdw.replica.entities.reports;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.mdw.replica.utilities.Helpers;

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
public class ReportAuditEntity {

	private String pathController;
	private String pathEndPoint;
	private String date;
	private Map<String, Object> dataInput = new HashMap<String, Object>();
	private Map<String, Object> dataOutput = new HashMap<String, Object>();
	
	public ReportAuditEntity(JsonObject data) {
		this.pathController = this.getValue(data, "pathController").toString();
		this.pathEndPoint = this.getValue(data, "pathEndPoint").toString();
		this.date = this.getValue(data, "date").toString();
		this.dataInput = Helpers.toList(this.getValue(data, "dataInput").toString());
		this.dataOutput = Helpers.toList(this.getValue(data, "dataOutput").toString());
	}
	
	public Object getValue(JsonObject value, String key) {
		if(value.has(key)) {
			return value.get(key);
		}
		return "";
	}
}
