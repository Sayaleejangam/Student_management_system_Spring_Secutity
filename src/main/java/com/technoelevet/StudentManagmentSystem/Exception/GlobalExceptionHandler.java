package com.technoelevet.StudentManagmentSystem.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.technoelevet.StudentManagmentSystem.Responce.ResponcesStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataAlreadyFoundException.class)
	public ResponseEntity<ResponcesStructure> getDataAlreadyFoundException(DataAlreadyFoundException e) {
		return ResponseEntity.badRequest().body(new ResponcesStructure(true  , "Student Details Added", null));
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ResponcesStructure> getDataNotFoundException(DataNotFoundException e) {
		return ResponseEntity.badRequest().body(new ResponcesStructure(true  , "Student Details Added", null));
	}
}
