package com.cos.showme.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cos.showme.config.auth.PrincipalDetails;
import com.cos.showme.domain.user.User;
import com.cos.showme.handler.ex.CustomValidationApiException;
import com.cos.showme.service.SubscribeService;
import com.cos.showme.service.UserService;
import com.cos.showme.web.dto.CMRespDto;
import com.cos.showme.web.dto.subscribe.SubscribeDto;
import com.cos.showme.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService;
	private final SubscribeService SubscribeService;
	

	
	@GetMapping("/apis/user/authChecking/{username}")
	public ResponseEntity<?> authCheck(@PathVariable String username){
		System.out.println("zzzz"+username);
		User userEntity = userService.유저검색(username);
		return new ResponseEntity<>(new CMRespDto<>(1,"회원승인성공1",userEntity),HttpStatus.OK);
	}
	
	@PutMapping("/api/user/{userId}/authSign")
	public ResponseEntity<?> userAuthSign(@PathVariable int userId){
		System.out.println("승인완료2");
		userService.회원승인(userId);
		return new ResponseEntity<>(new CMRespDto<>(1,"회원승인성공",null),HttpStatus.OK);
	}
	
	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate(@PathVariable int principalId, MultipartFile profileImageFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails){
		User userEntity = userService.회원프로필사진변경(principalId,profileImageFile);
		principalDetails.setUser(userEntity);//세션변경
		return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진변경 성공",null),HttpStatus.OK);
	}
	
	@GetMapping("/api/user/{pageUserId}/subscribe") // 내가 이동한 그 페이지의 주인이 구독하고 있는 모든 정보
	public ResponseEntity<?> subscribeList(@PathVariable int pageUserId,@AuthenticationPrincipal PrincipalDetails principalDetails){		
		
		List<SubscribeDto> subscribeDto =  SubscribeService.구독리스트(principalDetails.getUser().getId(),pageUserId); // 여기다가 정보를 쫙 담아서 리턴 해줄 예정
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보 리스트 불러오기 성공",subscribeDto),HttpStatus.OK);
	}
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 꼭 @Valid가 적혀있는 "다음"파라미터에 적어야함
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
			System.out.println(userUpdateDto);
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);//세션정보변경
			return new CMRespDto<>(1,"회원수정완료",userEntity); //응답시에 userEntity의 모든 getter함수가 호출되고, JSON으로 파싱하여 응답한다.
		
	}	
}
