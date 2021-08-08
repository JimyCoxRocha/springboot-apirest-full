package com.mdw.replica.services.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.mdw.replica.config.CouchDBConnection;
import com.mdw.replica.entities.http.MessageEntity;
import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.logs.LogModuleEntity;
import com.mdw.replica.entities.securitys.ModuleEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;

@Service
public class ModuleService {

	@Autowired
	private CouchDBConnection couch;
	
	public ModuleEntity findById(Integer idModule) throws RSExceptionEntity{
		try {
			Map<String, SelectorEntity> m = new HashMap<String, SelectorEntity>();
			m.put("id_module", new SelectorEntity("$eq", idModule));
			
			List<JsonObject> r = this.couch.find("sgi_modules", m);
			
			if(r == null)
				throw new RSExceptionEntity(MessageEntity.NO_DATA.getMessage(), HttpStatus.FORBIDDEN);
			
			return new ModuleEntity(r.get(0).getAsJsonObject().get("id_module").getAsInt(),
									r.get(0).getAsJsonObject().get("name").getAsString(),
									r.get(0).getAsJsonObject().get("description").getAsString(),
									r.get(0).getAsJsonObject().get("icon").getAsString(),
									r.get(0).getAsJsonObject().get("status").getAsBoolean());
		
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public RSEntity<List<LogModuleEntity>> findAll() throws RSExceptionEntity{
		try {
			List<LogModuleEntity> response = new ArrayList<LogModuleEntity>();
			
			Map<String, SelectorEntity> m = new HashMap<String, SelectorEntity>();
			m.put("status", new SelectorEntity("$eq", true));
			
			List<JsonObject> r = this.couch.find("ctg_module", m);
			
			if(r.size() != 0) {
				for(JsonObject ob : r) {
					response.add(new LogModuleEntity(ob.get("key").getAsString(), 
							ob.get("name").getAsString(), ob.get("key_name").getAsString()));
				}
			}
			
			return new RSEntity<List<LogModuleEntity>>(response, response.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
