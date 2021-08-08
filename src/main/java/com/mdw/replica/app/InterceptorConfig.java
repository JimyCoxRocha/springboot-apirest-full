package com.mdw.replica.app;

import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mdw.replica.config.JWTAuthentication;
import com.mdw.replica.entities.http.RSEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.securitys.UserAuthenticatedEntity;
import com.mdw.replica.utilities.Environment;
import com.mdw.replica.utilities.Helpers;

//Este es como un intermediario antes de llegar al servltDispatcher que es el que se comunica con el controller
//Por ejemplo, primero llega al controlador Servrl, que ofrece la capa de seguridad y la comunicación con el servidor
//Este interceptor recepta la petción y busca si hay errores
@Component
public class InterceptorConfig implements HandlerInterceptor{

	@Autowired
	JWTAuthentication jwt;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception, ServletException {
	    try {
			this.validate(request);
			return true;
	    } catch (RSExceptionEntity ex) {
	    	// TODO: handle exception
	    	RSEntity<Object> res = new RSEntity<Object>("CLIENT", ex.getMessages());
	    	response.setContentType(Environment.contentType);//Se especifica el formato de salida
	    	response.setCharacterEncoding(Environment.characterEncoding);
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	//Con esto se le envían los datos al usuario, en este caso es de tipo String
	    	response.getWriter().write(Helpers.toJson(res).toString());
	    	return false;
	    	
		} catch (Exception e) {
			// TODO: handle exception
			RSEntity<Object> res = new RSEntity<Object>("CLIENT", e.getMessage());
	    	response.setContentType(Environment.contentType);//Se especifica el formato de salida
	    	response.setCharacterEncoding(Environment.characterEncoding);
	    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	//Con esto se le envían los datos al usuario, en este caso es de tipo String
	    	response.getWriter().write(Helpers.toJson(res).toString());
	    	return false;
		}
	}
	
	@Override
	public void postHandle(
	  HttpServletRequest request, 
	  HttpServletResponse response,
	  Object handler, 
	  ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(
	  HttpServletRequest request, 
	  HttpServletResponse response,
	  Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	public UserAuthenticatedEntity validate(HttpServletRequest request) throws RSExceptionEntity{
		try {
			String token = "";
			String[] paths = { Environment.pathControllerSecurity + "/login" };
			Integer ii = 0;
			
			String url = request.getRequestURI().toString();
			
			for(String path: paths) {
				if(url.trim().equals("/") || url.contains(path)) {
					ii++;
					break;
				}
			}
			
			if(ii == 0) {
				Enumeration<String> headerNames = request.getHeaderNames();
				if(headerNames != null) {
					while(headerNames.hasMoreElements()) {
						String header = headerNames.nextElement();
						if(header.equalsIgnoreCase("Authorization")) {//Compara e ignora si es mayuscula o minuscula
							token = request.getHeader(header);
							break;
						}
					}
				}
				if(token.equals("")) {
					throw new RSExceptionEntity("Autorización requerida", HttpStatus.UNAUTHORIZED);
				}
				return this.jwt.decodeToken(token);
			}
			
			return new UserAuthenticatedEntity();
			
	    } catch (RSExceptionEntity ex) {	
	    	throw new RSExceptionEntity(ex.getMessages(), ex.getCode());
		}
	}
}
