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
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.reports.ReportResponseEntity;
import com.mdw.replica.entities.request.ReportEndPointsEntity;
import com.mdw.replica.entities.request.ReportEntity;
import com.mdw.replica.entities.request.ReportModuleEntity;
import com.mdw.replica.entities.request.ReportRequestEntity;
import com.mdw.replica.entities.request.ReportServiceEntity;
import com.mdw.replica.entities.utilities.SelectorEntity;
import com.mdw.replica.utilities.Helpers;

@Service
public class LogService {
	private ReportRequestEntity reportRequest = new ReportRequestEntity();
	private String typeReport;
	
	@Autowired
	private CouchDBConnection couch;

	
	public String deleteById(String dataBase, String _id, String _rev) throws RSExceptionEntity {
		try {
			this.couch.delete(dataBase, _id, _rev);
//			return new RSEntity<List<LogServiceEntity>>(response, response.size());
			
			return "Delete element by id: "+ _id;
		} catch (RSExceptionEntity e) {
			return "Error: " + e.getMessage()+ " |in element by id: "+ _id + " |CODE ERROR: "+e.getCode();
		} catch (Exception e) {
			// TODO: handle exception
			return "Error: " + e.getMessage()+ " |in element by id: "+ _id + " CODE ERROR: "+HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	
	
	public List<ReportResponseEntity> findByModule(ReportEntity report, String typeReport) throws RSExceptionEntity{
		List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
		this.typeReport = typeReport;
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
	
	
	public List<ReportResponseEntity> findByService(List<ReportServiceEntity> rptService) throws RSExceptionEntity{
		List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
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
	
	public List<ReportResponseEntity> findByEndPoint(List<ReportEndPointsEntity> rptEndPoints) throws RSExceptionEntity{
		List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
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
	
	public List<ReportResponseEntity> searchReport() throws RSExceptionEntity{
		List<ReportResponseEntity> response = new ArrayList<ReportResponseEntity>();
		Map<String, SelectorEntity> m = new HashMap<>();
		
		try {
			m.put("dateInput", 
					new SelectorEntity("$gte", Helpers.addHoursInitial(reportRequest.getFechaDesde()), 
							"$lte", Helpers.addHoursFinal(reportRequest.getFechaHasta())));
			
			m.put("pathController", new SelectorEntity("$eq", reportRequest.getIdServicio()));
			m.put("pathEndpoint", new SelectorEntity("$eq", reportRequest.getIdEndPoint()));
			
			if(this.typeReport.equals("OK")) {
				m.put("dataOutput.typeError", new SelectorEntity("$eq", this.typeReport));
				
			}else if(this.typeReport.equals("")) {
				m.put("dataOutput.typeError", new SelectorEntity("$ne", "OK"));
			}
			
			List<JsonObject> l = this.couch.find(this.reportRequest.getDataBase(), m);
			
			//CREANDO UNA NUEVA INSTANCIA
			
			if(l.size() != 0) {
				for(JsonObject ob : l) {
					response.add(new ReportResponseEntity(ob));
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
