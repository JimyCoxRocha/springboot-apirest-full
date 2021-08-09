package com.mdw.replica.utilities;


import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mdw.replica.entities.http.RSExceptionEntity;

public class DeserializationObject {


    public <T> T setAsText(String text, Class<T> typeParameterClass) throws RSExceptionEntity {
        try {
        	Gson objectMapper = new Gson();
            return objectMapper.fromJson(text, typeParameterClass);
        } catch (JsonSyntaxException e) {
        	throw new RSExceptionEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}