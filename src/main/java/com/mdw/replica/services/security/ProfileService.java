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
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.securitys.ProfileEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;

@Service
public class ProfileService {

	@Autowired
	private CouchDBConnection couch;
	
	public List<ProfileEntity> findProfileByIdUser(Integer idUser, Boolean status) throws RSExceptionEntity{
		try {
			Map<String, SelectorEntity> m = new HashMap<String, SelectorEntity>();
			m.put("id_user", new SelectorEntity("$eq", idUser));
			m.put("status", new SelectorEntity("$eq", status));
			
			List<JsonObject> r = this.couch.find("sgi_profiles_in_options", m);
			
			if(r == null)
				throw new RSExceptionEntity(MessageEntity.NOT_PROFILE.getMessage(), HttpStatus.FORBIDDEN);
			
			List<ProfileEntity> res = new ArrayList<ProfileEntity>();
		
			for(JsonObject ob : r) {
				res.add(new ProfileEntity(ob.get("id_profile").getAsInt(), 
						ob.get("id_option").getAsInt(), null, ob.get("id_user").getAsInt()));
			}
			return res;
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
