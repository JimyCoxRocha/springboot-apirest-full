package com.mdw.replica.services.catalogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.mdw.replica.config.CouchDBConnection;
import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.logs.LogEndPointEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;

@Service
public class EndPointService {

	@Autowired
	private CouchDBConnection couch;
	
	public RSEntity<List<LogEndPointEntity>> findAll() throws RSExceptionEntity{
		try {
			List<LogEndPointEntity> response = new ArrayList<LogEndPointEntity>();
			Map<String, SelectorEntity> m = new HashMap<String, SelectorEntity>();
			m.put("status", new SelectorEntity("$eq", true));
			
			
			List<JsonObject> l = this.couch.find("ctg_endpoint", m);
			
			if(l.size() != 0) {
				for(JsonObject ob : l) {
					response.add(new LogEndPointEntity(ob.get("key_service").getAsString(), 
							ob.get("key").getAsString(), ob.get("name").getAsString()));
				}
			}
			return new RSEntity<List<LogEndPointEntity>>(response, response.size());
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
