package com.mdw.replica.services.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.reports.ReportResponseEntity;
import com.mdw.replica.entities.request.ReportEntity;

@Service
public class ReportLogService {
	private static String REPORT_ERROR = "";
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
}
