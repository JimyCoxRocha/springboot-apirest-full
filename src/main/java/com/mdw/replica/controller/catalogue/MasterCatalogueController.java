package com.mdw.replica.controller.catalogue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.logs.LogEndPointEntity;
import com.mdw.replica.entities.logs.LogModuleEntity;
import com.mdw.replica.entities.logs.LogServiceEntity;
import com.mdw.replica.services.catalogue.EndPointService;
import com.mdw.replica.services.catalogue.ServiService;
import com.mdw.replica.services.security.ModuleService;


@RestController
@RequestMapping("/v1/catalogues/")
public class MasterCatalogueController {
	
	public static final String pathModuleFindAll = "/modules";
	public static final String pathServiceFindAll = "/services";
	public static final String pathEndPointFindAll = "/endpoints";
	
	@Autowired
	private EndPointService endPointService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ServiService serviService;
	
	@GetMapping(pathEndPointFindAll)
	public ResponseEntity<RSEntity<List<LogEndPointEntity>>> findEndPointAll(){
		try {
			return this.endPointService.findAll().send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<LogEndPointEntity>>().send(e);
		}
	}
	
	@GetMapping(pathModuleFindAll)
	public ResponseEntity<RSEntity<List<LogModuleEntity>>> findModuleAll(){
		try {
			return this.moduleService.findAll().send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<LogModuleEntity>>().send(e);
		}
	}
	
	
	@GetMapping(pathServiceFindAll)
	public ResponseEntity<RSEntity<List<LogServiceEntity>>> findServiceAll(){
		try {
			return this.serviService.findAll().send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<LogServiceEntity>>().send(e);
		}
	}
	
	
}
