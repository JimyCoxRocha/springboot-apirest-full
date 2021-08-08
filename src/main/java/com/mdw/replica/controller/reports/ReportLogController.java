package com.mdw.replica.controller.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.logs.LogEndPointEntity;
import com.mdw.replica.entities.logs.LogModuleEntity;
import com.mdw.replica.entities.reports.ReportAuditEntity;
import com.mdw.replica.entities.reports.ReportLogService;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.services.security.ModuleService;
import com.mdw.replica.utilities.Environment;

@RestController
@RequestMapping(Environment.pathControllerReporteLog)
public class ReportLogController {
	
	public static final String pathFindLogError = "/error";
	public static final String pathFindLogAuditoria = "/auditoria";
	
	@Autowired
	private ModuleService moduleServi;
	
	@Autowired
	private ReportLogService reportLogServi;
	
	
	@GetMapping(pathFindLogError)
	public ResponseEntity<RSEntity<List<LogModuleEntity>>> findLogError(){
		try {
			return this.moduleServi.findAll().send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<LogModuleEntity>>().send(e);
		}
	}
	
	@GetMapping(pathFindLogAuditoria)
	public ResponseEntity<RSEntity<List<ReportAuditEntity>>> findLogAuditoria(@RequestBody ReportEntity reportEntity){
		try {
			return this.reportLogServi.findReportAuditory(reportEntity).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportAuditEntity>>().send(e);
		}
	}
	
}
