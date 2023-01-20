package com.spring.javawspring.errorTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorAdvice {
	
	private Logger logger = LoggerFactory.getLogger(ErrorAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		
		logger.error("예외오류발생 : {}", ex.getMessage());
		
		System.out.println("Exception :" + ex);
		System.out.println("Exception :" + ex.getMessage());
		
		model.addAttribute("msg", "예외 오류가 발생했습니다.<br/>"+ex.getMessage()); 	
		
		return "errorPage/errorMsg3";
	}
		
		// 400에러가 발생시에 처리하는 메소드....
		@ExceptionHandler(NoHandlerFoundException.class)
		@ResponseStatus(value = HttpStatus.NOT_FOUND)
		public String handle404(NoHandlerFoundException ex, Model model) {
			logger.error("404 요청 오류 발생1 : {}",ex.getMessage());
			logger.error("404 요청 오류 발생2 : {}",ex.getRequestURL());
			
			model.addAttribute("msg", "<p><font color='red'>접속한 페이지 정보가 없습니다. 다시한번 확인바랍니다</font></p><p><input type='button' value='이전 페이지로 돌아가기' onclick='history.back();' class='btn btn-secondary'/></p><p>"+ex.getRequestURL()+"</p>");
			
			return "errorPage/errorMsg3";
		}
}
