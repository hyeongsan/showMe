package com.cos.showme.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.showme.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//조금 위험함. 코드 수정이 필요할 예정
	public User toEntity() {
		return User.builder()
				.name(name) // 이름을 기재 안했으면 문제!! VAlidation 체크를 해야함
				.password(password) // 패스워드를 기재 안했으면 문제!! Validation 체크를 해야함
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
