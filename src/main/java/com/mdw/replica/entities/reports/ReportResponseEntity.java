package com.mdw.replica.entities.reports;

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
public class ReportResponseEntity {

	private String _id;
	private String pathController;
	private String pathEndPoint;
	private String date;
	private Object dataInput;
	private Object dataOutput;
//	private Map<String, Object> dataInput = new HashMap<String, Object>();
//	private Map<String, Object> dataOutput = new HashMap<String, Object>();
	
	public ReportResponseEntity(JsonObject data) {
		this._id = Helpers.jsonToString(this.getValue(data, "_id").toString());
		this.pathController = Helpers.jsonToString(this.getValue(data, "pathController").toString());
		this.pathEndPoint = Helpers.jsonToString(this.getValue(data, "pathEndPoint").toString());
		this.date = Helpers.jsonToString(this.getValue(data, "dateOutput").toString());
		this.dataInput = new GsonBuilder().serializeNulls().create().fromJson(this.getValue(data, "dataInput").toString(), Object.class);
		this.dataOutput = new Gson().fromJson(this.getValue(data, "dataOutput").toString(), Object.class);
//		this.dataInput = Helpers.toList(this.getValue(data, "dataInput").toString());
//		this.dataOutput = Helpers.toList(this.getValue(data, "dataOutput").toString());
	}
	
	public Object getValue(JsonObject value, String key) {
		if(value.has(key)) {
			return value.get(key);
		}
		return "";
	}
}
