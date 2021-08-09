package com.mdw.replica.controller.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.reports.ReportResponseEntity;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.services.reports.ReportLogService;
import com.mdw.replica.utilities.DeserializationObject;
import com.mdw.replica.utilities.Environment;

@RestController
@RequestMapping(Environment.pathControllerReporteLog)
public class ReportLogController {
	private static DeserializationObject deseralizar = new DeserializationObject();
	
	public static final String pathFindLogError = "/error";
	public static final String pathFindLogAuditoria = "/auditoria";
	
	@Autowired
	private ReportLogService reportLogServi;
	
	
	@GetMapping(pathFindLogError)
	public ResponseEntity<RSEntity<List<ReportResponseEntity>>> findLogError(@RequestParam String jsonReportEntity){
		try {
			ReportEntity reportEntity = new ReportEntity();
			reportEntity = deseralizar.setAsText(jsonReportEntity, ReportEntity.class);
			return this.reportLogServi.findReportError(reportEntity).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportResponseEntity>>().send(e);
		}
	}
	
	@GetMapping(pathFindLogAuditoria)
	public ResponseEntity<RSEntity<List<ReportResponseEntity>>> findLogAuditoria(@RequestParam String jsonReportEntity){
		try {
			ReportEntity reportEntity = new ReportEntity();
			reportEntity = deseralizar.setAsText(jsonReportEntity, ReportEntity.class);
			return this.reportLogServi.findReportAuditory(reportEntity).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportResponseEntity>>().send(e);
		}
	}
	
}
