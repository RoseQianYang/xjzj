package com.yunqi.common.json;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;

public class JsonUtil {

	public static <T> T fromRequest(byte[] body, Class<T> classOfT) {
		Gson gson = new Gson();
		String str = null;
		try {
			str = new String(body, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			str = new String(body);
		}
		return gson.fromJson(str, classOfT);
	}

	public static <T> T  fromRequest(String body, Class<T> classOfT) {
		Gson gson = new Gson();
		return gson.fromJson(body, classOfT);
	}
	
	public static String toJson(Object src) {
		Gson gson = new Gson();
		return gson.toJson(src);
	}

	public static ResponseEntity<String> toResponse(Object obj) {
		Gson gson = new Gson();
		HttpHeaders responseHeaders = new HttpHeaders();
		// IE 浏览器必须使用 text 模式，因此统一改为 text
		// responseHeaders.set("Content-Type", "application/json; charset=utf-8");
		responseHeaders.set("Content-Type", "text/html; charset=utf-8");
		String output = gson.toJson(obj);
		return new ResponseEntity<String>(output, responseHeaders,
				HttpStatus.OK);
	}

}
