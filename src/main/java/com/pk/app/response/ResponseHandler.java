package com.pk.app.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pk.app.model.Book;

public class ResponseHandler {
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", responseObj);
        map.put("message", message);
        map.put("status", status.value());    

        return new ResponseEntity<Object>(map,status);
    }
}
