package com.cos.showme.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.showme.domain.user.User;
import com.cos.showme.domain.user.UserRepository;
import com.cos.showme.handler.ex.CustomApiException;
import com.cos.showme.handler.ex.CustomException;
import com.cos.showme.handler.ex.CustomValidationApiException;
import com.cos.showme.handler.ex.CustomValidationException;
import com.cos.showme.util.Script;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 이걸 붙이는 순간 Ioc에 올라감
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	/*1.패스워드는 알아서 체킹하니까 신경 쓸 필요없다.*/
	/*2.리턴이 잘되면 자동으로 UserDetails타입을 세션을 만들어준다 (내부적으로)*/
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException  {

		User userEntity = userRepository.findByUsername(username);
		
		
		if(userEntity==null) { // null이면 못찾았다는 뜻 (DB에 없다는 뜻)
			return null;
		}
		
		if(userEntity.getIsAuth()!=null) {				
			return new PrincipalDetails(userEntity);
		}else {			
			throw new CustomException("가입 심사 중입니다");	
		}
		
	}
	
}
