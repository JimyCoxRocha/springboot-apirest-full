package com.mdw.replica.services.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.reports.ReportResponseEntity;
import com.mdw.replica.entities.request.LogDeleteEntity;
import com.mdw.replica.entities.request.ReportEntity;

@Service
public class ReportLogService {
	private static String REPORT_ERROR = "";
	private static String REPORT_ALL = "ALL";
	private static String REPORT_AUDITORY = "OK";
	
	@Autowired
	private LogService logServi;
	
	
	public RSEntity<List<ReportResponseEntity>> findReportAuditory(ReportEntity report) throws RSExceptionEntity{
		try {
			List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
			response.addAll(logServi.findByModule(report, REPORT_AUDITORY));
			
			return new RSEntity<List<ReportResponseEntity>>(response, response.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public RSEntity<List<ReportResponseEntity>> findReportError(ReportEntity report) throws RSExceptionEntity{
		try {
			List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
			response.addAll(logServi.findByModule(report, REPORT_ERROR));
			
			return new RSEntity<List<ReportResponseEntity>>(response, response.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public RSEntity<Map<String,List<ReportResponseEntity>>> findAgruppationReports(ReportEntity report) throws RSExceptionEntity{
		try {
			Map<String,List<ReportResponseEntity>> logAuditoria = new HashMap<String,List<ReportResponseEntity>>();
			
			logAuditoria.put("LogAuditoria", logServi.findByModule(report, REPORT_AUDITORY));
			logAuditoria.put("LogError", logServi.findByModule(report, REPORT_ERROR));
			
			return new RSEntity<Map<String,List<ReportResponseEntity>>>(logAuditoria, logAuditoria.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public RSEntity<List<ReportResponseEntity>> findAllReports(ReportEntity report) throws RSExceptionEntity{
		try {
			List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
			response.addAll(logServi.findByModule(report, REPORT_ALL));
			
			return new RSEntity<List<ReportResponseEntity>>(response, response.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public RSEntity<String> deleteReportById(List<LogDeleteEntity> deleteEntity) throws RSExceptionEntity{
		List<String> listDataDelete = new ArrayList<>();
		String elementDelete = "";
		try {
			System.out.println(deleteEntity.getClass().getSimpleName());
			System.out.println(deleteEntity.getClass().toString());
			
			for(LogDeleteEntity delete: deleteEntity) {
				elementDelete = logServi.deleteById(delete.getDataBase(), delete.get_id(), delete.get_rev());
				listDataDelete.add(elementDelete);
				elementDelete = "";
			
			}
//			List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
//			response.addAll(logServi.findByModule(report, REPORT_ALL));
//			if(listDataDelete.size() == 0)
//				throw new RSExceptionEntity(e.getMessage(), e.getCode());
			
			RSEntity<String> rsEntity = new RSEntity<String>("data eliminada correctamente");
			
			for (String element : listDataDelete)
				rsEntity.setMessages(element);
				
			return rsEntity;
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage() , e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
