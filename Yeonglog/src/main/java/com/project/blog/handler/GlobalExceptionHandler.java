package com.project.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.dto.ResponseDto;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	//익셉션 모든 것을 처리하도록 실행
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500
	}
}
