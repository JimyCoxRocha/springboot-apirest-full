package com.mdw.replica.services.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.mdw.replica.config.CouchDBConnection;
import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.logs.LogEndPointEntity;
import com.mdw.replica.entities.logs.LogServiceEntity;
import com.mdw.replica.entities.reports.ReportAuditEntity;
import com.mdw.replica.entities.request.ReportEndPointsEntity;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.entities.request.ReportModuleEntity;
import com.mdw.replica.entities.request.ReportRequestEntity;
import com.mdw.replica.entities.request.ReportServiceEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;
import com.mdw.replica.utilities.Helpers;

@Service
public class LogAuditoryServi {
	private ReportRequestEntity reportRequest = new ReportRequestEntity();
	
	@Autowired
	private CouchDBConnection couch;
	
	public List<ReportAuditEntity> findByModule(ReportEntity report) throws RSExceptionEntity{
		List<ReportAuditEntity> response = new ArrayList<ReportAuditEntity>();
		try {
			this.reportRequest.setFechaDesde(report.getFechaDesde());
			this.reportRequest.setFechaHasta(report.getFechaHasta());
			
			for(ReportModuleEntity module : report.getItems()) {
				this.reportRequest.setIdModule(module.getName());
				response.addAll(findByService(module.getServices()));
			}
			
//			return new RSEntity<List<LogServiceEntity>>(response, response.size());
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	
	public List<ReportAuditEntity> findByService(List<ReportServiceEntity> rptService) throws RSExceptionEntity{
		List<ReportAuditEntity> response = new ArrayList<ReportAuditEntity>();
		try {
			for(ReportServiceEntity service : rptService) {
				this.reportRequest.setIdServicio(service.getKey());
				this.reportRequest.setDataBase(service.getDataBase());
				response.addAll(findByEndPoint(service.getEndPoints()));
				
			}
			
//			return new RSEntity<List<LogServiceEntity>>(response, response.size());
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public List<ReportAuditEntity> findByEndPoint(List<ReportEndPointsEntity> rptEndPoints) throws RSExceptionEntity{
		List<ReportAuditEntity> response = new ArrayList<ReportAuditEntity>();
		try {
			for(ReportEndPointsEntity endPoint: rptEndPoints) {
				this.reportRequest.setIdEndPoint(endPoint.getKey());
				response.addAll(searchReport());
				
			}
			
//			return new RSEntity<List<LogServiceEntity>>(response, response.size());
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	public List<ReportAuditEntity> searchReport() throws RSExceptionEntity{
		List<ReportAuditEntity> response = new ArrayList<ReportAuditEntity>();
		Map<String, SelectorEntity> m = new HashMap<>();
		
		try {
			m.put("dateInput", 
					new SelectorEntity("$gte", Helpers.addHoursInitial(reportRequest.getFechaDesde()), 
							"$lte", Helpers.addHoursFinal(reportRequest.getFechaHasta())));
			
			m.put("pathController", new SelectorEntity("$eq", reportRequest.getIdServicio()));
			m.put("pathEndPoint", new SelectorEntity("$eq", reportRequest.getIdEndPoint()));

			List<JsonObject> l = this.couch.find(this.reportRequest.getDataBase(), m);
			
			if(l.size() != 0) {
				for(JsonObject ob : l) {
					response.add(new ReportAuditEntity(ob));
				}
			}
			
			return response;
		
		} catch (RSExceptionEntity e) {
			throw new RSExceptionEntity(e.getMessage(), e.getCode());
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
