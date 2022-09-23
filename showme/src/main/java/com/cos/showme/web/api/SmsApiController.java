package com.cos.showme.web.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.showme.domain.user.User;
import com.cos.showme.handler.ex.CustomValidationException;
import com.cos.showme.service.AuthService;
import com.cos.showme.service.certificationService;
import com.cos.showme.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

/*컨트롤러가 되기위해선 @Controller 어노테이션이 붙어있어야한다!! */
/*@Controller=>1.Ioc에 등록이 됬다는 의미이자 2. 파일을 리턴하겠다는 의미*/
@RequiredArgsConstructor // final 필드를 DI 할 때 사용 ㅇㅇㅇ 
@Controller 
public class SmsApiController {
	
	@GetMapping("/check/sendSMS")
    public @ResponseBody
    String sendSMS(String phoneNumber) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        certificationService.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }
}
