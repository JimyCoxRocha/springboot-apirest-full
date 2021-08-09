package com.mdw.replica.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Helpers {
	
	//Utility function
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor){
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) ==null;
	}
	
	public static JsonElement toJson(Object data) {
		Gson gsonBuilder = new GsonBuilder().create();
		Gson googleJson = new Gson();
		return googleJson.fromJson(gsonBuilder.toJson(data), JsonElement.class);
	}
	
	public static String jsonToString(String data) {
		Gson gsonBuilder = new GsonBuilder().serializeNulls().create();
		Gson googleJson = new Gson();
		String datoConvertir =  googleJson.fromJson(data, String.class);
		if (datoConvertir != null)
			return datoConvertir;
		return  "";
	}
	
	@SuppressWarnings("deprecation")
	public static JsonElement toJson(String dataRaw) {
		dataRaw = new JsonParser().parse(dataRaw).toString();
		
		Gson googleJson = new Gson();
		return googleJson.fromJson(dataRaw, JsonElement.class);
	}
	
	public static ArrayList<String> addStringArray(String message){
		ArrayList<String> array = new ArrayList<>();
		array.add(message);
		return array;
	}
	
	public static String addHoursFinal(String data) {
		data = data + " 23:59:59";
		return data;
	}
	
	public static String addHoursInitial(String data) {
		data = data + " 00:00:00";
		return data;
	}
	
	public static HashMap<String, Object> toList(String data){
		return new Gson().fromJson(data, new TypeToken<HashMap<String, Object>>(){
		}.getType());
	}
}
