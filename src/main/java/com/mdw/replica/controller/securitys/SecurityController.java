package com.mdw.replica.controller.securitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.securitys.LoginInEntity;
import com.mdw.replica.entities.securitys.LoginInOutEntity;
import com.mdw.replica.services.security.SecurityService;
import com.mdw.replica.utilities.Environment;

@RestController
@RequestMapping(Environment.pathControllerSecurity)
public class SecurityController {
	
	public final static String pathController = Environment.pathControllerSecurity;
	public final static String pathLogin = "/login";
	
	@Autowired 
	SecurityService segService;
	
	@PostMapping(pathLogin)
	public ResponseEntity<RSEntity<LoginInOutEntity>> login(@RequestBody LoginInEntity login){
		try {
			return this.segService.login(login).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<LoginInOutEntity>().send(e);
		}
	}

}
