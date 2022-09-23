package com.cos.showme.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.showme.domain.image.Image;
import com.cos.showme.domain.user.User;
import com.cos.showme.domain.user.UserRepository;
import com.cos.showme.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1.Ioc에 올라감 2.트랜잭션관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${file.path}") // application.yml 의 file.path
	private String uploadFolder;
	
	@Transactional // Write(Insert,Update,Delete)
	public User 회원가입(SignupDto signupDto) {
		
		UUID uuid = UUID.randomUUID(); // uuid 사용자가 동일 1.jpg라는 사진을 업로드할 경우 덮어씌어지므로
		   // uuid란? 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규악.
		String signupFileName = uuid+"_"+signupDto.getFile().getOriginalFilename(); // 이렇게하면 imageFileName에 실제 파일이름이 들어감
		
		System.out.println("이미지파일이름:"+signupFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+signupFileName);
		
		// 통신 혹은 I/O 가 일어날 때 -> 예외가 발생 할 수 있다.
		try {
		Files.write(imageFilePath, signupDto.getFile().getBytes()); //이미지가 업로드되다가 여기서 실패를 하면 익셉션 처리 해줘야함
		}catch(Exception e) {
		e.printStackTrace();
		}
		
		//image 테이블에 저장
		User user = signupDto.toEntity(signupFileName);// imageFileName : 5cf513d-ct34-343b-e5235d_1.jpg이렇게만 넣을 건데
		
		
		System.out.println("회원가입중"+signupDto);
		System.out.println("회원가입중"+signupDto.getUsername());
		System.out.println("회원가입중"+user);
		System.out.println("회원가입중"+user.getUsername());
		
		//회원가입진행 (repository필요)
		String rawPassword = user.getPassword(); //입력된 비밀번호
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 비밀번호 해시처리
		user.setPassword(encPassword); // 해시된 비밀번호 셋팅
				
		//관리자의 회원가입 일경우 ( id = admin )
		if(user.getUsername().equals("admin")) {
			System.out.println("admin인경우");
			user.setRole("ROLE_ADMIN");
			user.setIsAuth(1); // 승인없이 바로 회원가입  통과
		}else {
			System.out.println("admin이 아닌경우");
			user.setRole("ROLE_USER"); 
		}
		
		User userEntity = userRepository.save(user); // save를 쓰면 DB에 들어가고 내가 넣은 객체의 타입을 리턴받을 수 있다.
													 // 그리고 그것을 userEntity라는 User객체에 담는다
		return userEntity;
	}
}
