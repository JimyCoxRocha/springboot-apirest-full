package com.mdw.replica.controller.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.reports.ReportResponseEntity;
import com.mdw.replica.entities.request.LogDeleteEntity;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.services.reports.ReportLogService;
import com.mdw.replica.utilities.DeserializationObject;
import com.mdw.replica.utilities.Environment;

@RestController
@RequestMapping(Environment.pathControllerReporteLog)
public class ReportLogController {
	
	public static final String pathFindLogError = "/error";
	public static final String pathFindLogAuditoria = "/auditoria";
	public static final String pathAgruppationReports = "/agrupar";
	public static final String pathDeleteReport = "";
	public static final String pathAllReports = "";
	
	@Autowired
	private ReportLogService reportLogServi;
	
	@GetMapping(pathAllReports)
	public ResponseEntity<RSEntity<List<ReportResponseEntity>>> findLogDelete(@RequestParam String jsonReportEntity){
		try {
			return this.reportLogServi.findAllReports( DeserializationObject.toObject(jsonReportEntity, ReportEntity.class)).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportResponseEntity>>().send(e);
		}
	}
	
	@GetMapping(pathFindLogError)
	public ResponseEntity<RSEntity<List<ReportResponseEntity>>> findLogError(@RequestParam String jsonReportEntity){
		try {
			return this.reportLogServi.findReportError(DeserializationObject.toObject(jsonReportEntity, ReportEntity.class)).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportResponseEntity>>().send(e);
		}
	}
	
	@GetMapping(pathFindLogAuditoria)
	public ResponseEntity<RSEntity<List<ReportResponseEntity>>> findLogAuditoria(@RequestParam String jsonReportEntity){
		try {
			return this.reportLogServi.findReportAuditory(DeserializationObject.toObject(jsonReportEntity, ReportEntity.class)).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<List<ReportResponseEntity>>().send(e);
		}
	}
	
	@GetMapping(pathAgruppationReports)
	public ResponseEntity<RSEntity<Map<String,List<ReportResponseEntity>>>> findLogAgrupar(@RequestParam String jsonReportEntity){
		try {
			return this.reportLogServi.findAgruppationReports(DeserializationObject.toObject(jsonReportEntity, ReportEntity.class)).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<Map<String,List<ReportResponseEntity>>>().send(e);
		}
	}
	
	@DeleteMapping(pathDeleteReport)
	public ResponseEntity<RSEntity<String>> deleteLog(@RequestParam String jsonDelete){
		try {
			List<LogDeleteEntity> objt =  new ArrayList<LogDeleteEntity>();
			objt = DeserializationObject.toList(jsonDelete);
			return this.reportLogServi.deleteReportById(objt).send();
		} catch (RSExceptionEntity e) {
			return new RSEntity<String>().send(e);
		}
	}
	
}
