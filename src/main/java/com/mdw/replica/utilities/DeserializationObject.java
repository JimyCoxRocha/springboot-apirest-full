package com.mdw.replica.utilities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mdw.replica.entities.http.RSExceptionEntity;
import com.mdw.replica.entities.request.LogDeleteEntity;

public class DeserializationObject {


    public static <T> T toObject(String text, Class<T> typeParameterClass) throws RSExceptionEntity {
        try {
        	Gson objectMapper = new Gson();
            return objectMapper.fromJson(text, typeParameterClass);
        } catch (JsonSyntaxException e) {
        	throw new RSExceptionEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

	public static List<LogDeleteEntity> toList(String jsonDelete) throws RSExceptionEntity {
		try {
			List<LogDeleteEntity> logsDelete = new ArrayList<>();
			LogDeleteEntity log = null;
			Gson gson = new Gson();
			List<JsonObject> array = gson.fromJson(jsonDelete, new TypeToken<List<JsonObject>>(){
    		}.getType());
			System.out.println(array);
			for(JsonObject elemento : array) {
				log = new LogDeleteEntity();
				log.set_id(elemento.get("_id").getAsString());
				log.set_rev(elemento.get("_rev").getAsString());
				log.setDataBase(elemento.get("dataBase").getAsString());
				System.out.println(elemento);
				logsDelete.add(log);
			}
			
            return logsDelete;
        } catch (JsonSyntaxException e) {
        	throw new RSExceptionEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
	}
	

}