package com.spring.javawspring.errorTest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/errorPage")
public class ErrorController {

	// 에러연습 폼...
	@RequestMapping("/error")
	public String errorGet() {
		return "errorPage/errorMain";
	}
	
	// jsp파일에서의 에러발생폼을 호출
	@RequestMapping("/error1")
	public String error1Get() {
		return "errorPage/error1";
	}
	
	// 웹에서 400,404,405,500 에러가 발생시 이동할 경로설정 (web.xml)
	@RequestMapping("/errorOk")
	public String errorOkGet() {
		return "errorPage/errorMsg2";
	}
	
	
	// Servlet에서 예외오류 발생....
	@RequestMapping("/error3")
	public String error3Get() {
		String str = null;
		System.out.println("str:"+str.toString());
		return "errorPage/errorMain";
	}
	
	
}
