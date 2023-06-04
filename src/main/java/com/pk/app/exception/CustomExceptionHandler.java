package com.pk.app.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> processUnmergeException(final MethodArgumentNotValidException ex) {
		  Map<String, Object> responseBody = new LinkedHashMap();
	        responseBody.put("timestamp", new Date());
	        responseBody.put("status", 400);
       List errors = ex.getBindingResult().getAllErrors().stream()
               .map(fieldError -> fieldError.getDefaultMessage())
               .collect(Collectors.toList());
       responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
