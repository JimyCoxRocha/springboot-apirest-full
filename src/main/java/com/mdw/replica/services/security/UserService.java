package com.mdw.replica.services.security;

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
import com.mdw.replica.entities.securitys.LoginInEntity;
import com.mdw.replica.entities.securitys.UserEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;

@Service
public class UserService {

	@Autowired
	private CouchDBConnection couch;
	
	public UserEntity findByUsernameAndPassword(LoginInEntity user) throws RSExceptionEntity{
		try {
			Map<String, SelectorEntity> m = new HashMap<String, SelectorEntity>();
			m.put("user", new SelectorEntity("$eq", user.getUsername()));
			m.put("password", new SelectorEntity("$eq", user.getPassword()));
			
			List<JsonObject> r = this.couch.find("sgi_users", m);
			
//			JsonObject valor = new JsonObject();
//			valor.getAsDouble();
			
			if(r.size() == 0)
				throw new RSExceptionEntity(MessageEntity.NOT_USER.getMessage(), HttpStatus.UNAUTHORIZED);
			
			UserEntity userEntity = new UserEntity(); 
			userEntity.setIdUser(r.get(0).get("id_user").getAsInt());
			userEntity.setUser(r.get(0).getAsJsonObject().get("user").getAsString());
			userEntity.setLasName(r.get(0).getAsJsonObject().get("last_name").getAsString());
			userEntity.setName(r.get(0).getAsJsonObject().get("name").getAsString());
			
			return userEntity;			
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
