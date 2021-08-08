package com.mdw.replica.entities.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.services.reports.LogAuditoryServi;

@Service
public class ReportLogService {

	@Autowired
	private LogAuditoryServi logAuditoryServi;
	
	public RSEntity<List<ReportAuditEntity>> findReportAuditory(ReportEntity report) throws RSExceptionEntity{
		try {
			List<ReportAuditEntity> response = new ArrayList<ReportAuditEntity>();
			response.addAll(logAuditoryServi.findByModule(report));
			
			return new RSEntity<List<ReportAuditEntity>>(response, response.size());
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
