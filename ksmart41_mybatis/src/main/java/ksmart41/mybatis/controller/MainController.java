package ksmart41.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	/**
	 * @Controller를 정의한 클래스들의 메소드의 반환형이 String인 경우 프로젝트 화면파일의 논리적 경로가 된다.
	 * @return String -> 논리 이름만 쓴다. (ex:src/main/resources/templates/ +"논리이름명" + .html)
	 */
	
	
	
	@GetMapping("/")
	/*
	 * @ResponseBody //응답할 때 문자열로 반환해주는 것
	 */
	public String main() {
		return "main";
	}
}
