package com.mdw.replica.entities.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class ReportTypeEntity {

	private Map<String,List<ReportResponseEntity>> logAuditoria = new HashMap<String,List<ReportResponseEntity>>();
	
	public void setLogAuditoria(List<ReportResponseEntity> dato, String tipo) {
		this.logAuditoria.put(tipo, dato);
	}
//	private List<ReportResponseEntity> logError;
	
//	public ReportsEntity(JsonObject data) {
//		this.pathController = Helpers.jsonToString(this.getValue(data, "pathController").toString());
//		this.pathEndPoint = Helpers.jsonToString(this.getValue(data, "pathEndPoint").toString());
//		this.date = Helpers.jsonToString(this.getValue(data, "dateOutput").toString());
//		this.dataInput = new GsonBuilder().serializeNulls().create().fromJson(this.getValue(data, "dataInput").toString(), Object.class);
//		this.dataOutput = new Gson().fromJson(this.getValue(data, "dataOutput").toString(), Object.class);
////		this.dataInput = Helpers.toList(this.getValue(data, "dataInput").toString());
////		this.dataOutput = Helpers.toList(this.getValue(data, "dataOutput").toString());
//	}
//	
//	public Object getValue(JsonObject value, String key) {
//		if(value.has(key)) {
//			return value.get(key);
//		}
//		return "";
//	}
}
