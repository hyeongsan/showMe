package com.cos.showme.util;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public class Script {
	
	
	public static @ResponseBody String back(String msg) {
		System.out.println("여기를 타는지");
		StringBuffer sb = new StringBuffer();
		
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString(); // back(String msg)은 script코드를 하나 만들어서 리턴하는 함수임
	}
}
