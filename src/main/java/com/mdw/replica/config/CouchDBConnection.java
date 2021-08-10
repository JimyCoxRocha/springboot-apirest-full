package com.mdw.replica.config;

import java.util.List;
import java.util.Map;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.NoDocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;

@Component
@PropertySource("classpath:couchdb.properties")
@Scope("singleton")
public class CouchDBConnection {

	@Value("${couchdb.protocol}")
	private String protocol;
	
	@Value("${couchdb.host}")
	private String host;
	
	@Value("${couchdb.port}")
	private int port;
	
	@Value("${couchdb.username}")
	private String username;
	
	@Value("${couchdb.password}")
	private String password;
	
	
	protected boolean ifNotExist = true;
	
	public CouchDbClient getConnection (String name) throws Exception{
		try {
			CouchDbProperties pro = new CouchDbProperties()
					.setDbName(name)
					.setCreateDbIfNotExist(ifNotExist)
					.setProtocol(protocol)
					.setPort(port)
					.setHost(host)
					.setUsername(username)
					.setPassword(password);
			
			CouchDbClient conn = new CouchDbClient(pro);
			return conn;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
		}
	}
	
	private String getSelector (Map<String, SelectorEntity> params) {
		JsonObject selector = new JsonObject();
		
		
		for (Map.Entry<String, SelectorEntity> pair : params.entrySet()) {
			JsonObject d = new JsonObject();
			
			for(Map.Entry<String, Object> item: pair.getValue().getOperator().entrySet()) {
				switch (item.getValue().getClass().toString()) {
				case "class com.google.gson.JsonObject":
				case "class com.google.gson.JsonArray":
					d.add(item.getKey(), (JsonElement) item.getValue());
					break;
				case "class java.lang.Integer":
					d.addProperty(item.getKey(), Integer.parseInt(item.getValue().toString()));
					break;
				case "class java.lang.Double":
					d.addProperty(item.getKey(), Double.parseDouble(item.getValue().toString()));
					break;
				case "class java.lang.Boolean":
					d.addProperty(item.getKey(), Boolean.parseBoolean(item.getValue().toString()));
					break;
				case "class java.lang.HashMap":
				case "class java.lang.ArrayList":
					d.add("data", toJson(item.getValue()));
					break;
				case "class java.lang.String":
					d.addProperty(item.getKey(), item.getValue().toString());
					break;
				default:
					d.addProperty(item.getKey(), item.getValue().toString());
					break;
				}
			}
			selector.add(pair.getKey(), d);
		}
		JsonObject payload = new JsonObject();
		payload.add("selector", selector);
		return payload.toString();
	}
	
	public List<JsonObject> find (String database, Map<String, SelectorEntity> selector) throws RSExceptionEntity{
		try {
			CouchDbClient connection = this.getConnection(database);
			//La clase que le enviamos es para ver de que tipo queremos la lista, en este caso será del tipo JsonObject
			List<JsonObject> findDocs = connection.findDocs(this.getSelector(selector), JsonObject.class);
			connection.shutdown();
			
			if(findDocs == null)
				throw new RSExceptionEntity("Error: 1", HttpStatus.NOT_FOUND);
			
			return findDocs;
		} catch (RSExceptionEntity e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), e.getCode());	
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);	
		}
	}
	
	public void delete (String database, String _id, String _rev) throws RSExceptionEntity{
		try {
			CouchDbClient connection = this.getConnection(database);
			//La clase que le enviamos es para ver de que tipo queremos la lista, en este caso será del tipo JsonObject
			Object resp =  connection.remove(_id, _rev);
			System.out.println(resp);
			connection.shutdown();
			
//			if(resp == null)
//				throw new RSExceptionEntity("Error: 1", HttpStatus.NOT_FOUND);
			

		} catch (RSExceptionEntity e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (NoDocumentException e) {
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.REQUEST_TIMEOUT);	
		}
	}
	
	private JsonElement toJson(Object data) {
		Gson gsonBuilder = new GsonBuilder().create();
		Gson googleJson = new Gson();
		return googleJson.fromJson(gsonBuilder.toJson(data), JsonElement.class);
	}
}
