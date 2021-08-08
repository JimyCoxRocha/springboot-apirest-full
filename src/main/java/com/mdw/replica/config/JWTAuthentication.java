package com.mdw.replica.config;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mdw.replica.entities.http.MessageEntity;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.securitys.UserAuthenticatedEntity;
import com.mdw.replica.utilities.Environment;
import com.mdw.replica.utilities.Helpers;

@Component
public class JWTAuthentication {
	public String encodeToken(UserAuthenticatedEntity data) throws RSExceptionEntity{
			try {
				JsonWebEncryption jwe = new JsonWebEncryption();
				jwe.setPayload(Helpers.toJson(data).toString());
				jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
				jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
				jwe.setKey(Environment.key);
				return jwe.getCompactSerialization();
			}catch (JoseException e) {
				// TODO: handle exception
				throw new RSExceptionEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
			} catch (Exception e) {
				// TODO: handle exception
				throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	public UserAuthenticatedEntity decodeToken(String token) throws RSExceptionEntity{
		JsonWebEncryption  jwe = new JsonWebEncryption();
		jwe.setAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, 
		        KeyManagementAlgorithmIdentifiers.A128KW));
		jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, 
		        ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
		jwe.setKey(Environment.key);
		
		try {
			jwe.setCompactSerialization(token);
			return new Gson().fromJson(jwe.getPayload(), UserAuthenticatedEntity.class);
		}catch (JoseException e) {
			// TODO: handle exception
			throw new RSExceptionEntity(MessageEntity.INVALID_TOKEN.getMessage(), HttpStatus.UNAUTHORIZED);		
		} catch (Exception e) {
			// TODO: handle exception
			throw new RSExceptionEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}
}
