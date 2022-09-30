package com.cos.showme.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.showme.domain.image.Image;
import com.cos.showme.domain.image.ImageRepository;
import com.cos.showme.domain.subscribe.SubscribeRepository;
import com.cos.showme.domain.user.User;
import com.cos.showme.domain.user.UserRepository;
import com.cos.showme.handler.ex.CustomApiException;
import com.cos.showme.handler.ex.CustomException;
import com.cos.showme.handler.ex.CustomValidationApiException;
import com.cos.showme.web.dto.subscribe.SubscribeDto;
import com.cos.showme.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	
	private final UserRepository userRepository; // userRepository 필요
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SubscribeRepository subscribeRepository;
	private final ImageRepository imageRepository;
	
	@Value("${file.path}") // application.yml 의 file.path
	private String uploadFolder;
	
	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile) {
		UUID uuid = UUID.randomUUID(); 
		String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); 
		
		System.out.println("이미지파일이름:"+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신 혹은 I/O 가 일어날 때 -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, profileImageFile.getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName); // userRepository에서 Return된 User객체이므로 (@Entity걸려있는) DB영향주는 더티체킹일어남
		
		return userEntity;
	}// 더티체킹으로 업데이트 됨 ( Transactional이 걸려있으므로 ) 
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		
		//해당유저가 가지고 있는 모든 사진을 가져올 것이다.
		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			//해당 유저아이디로 검색이 될 수도 있고, 안될 수도 있다. 안될 수도 있기 때문에 orElseThrow를 적어주었음
			//해당유저를 못찾으면 익셉션 발동
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
		//userEntity.getImages().get(0);
		
		List<Image> images = imageRepository.mAnswer(pageUserId);
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId==principalId); // 1은 페이지 주인, -1은 주인이 아님
		dto.setImageCount(userEntity.getImages().size());
		dto.setAnswerImages(images);
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState==1); // SubscribeState은 boolean 타입이라서 파라미터 이렇게
		dto.setSubscribeCount(subscribeCount);
		
		//좋아요 카운트 추가하기
		userEntity.getImages().forEach((image)->{			
			image.setLikeCount(image.getLikes().size());
			
				image.getComments().forEach((comment)->{
				
				if(principalId==comment.getUser().getId()) {
					comment.setEqualUserState(true);
					}	
				});
				
				//게시글 본인여부
				if(image.getUser().getId()==principalId) {
					image.setImageState(true);
				}
				
			
		});
		
	

		
		return dto;
	}
	
	@Transactional
	public User 회원수정(int id, User user) {
		
		//0.영속화
		//1.무조건 찾았다. 걱정마 get() 2.못찾았어 익셉션 발동시킬게 orElseThrow()라는 함수
		User userEntity = userRepository.findById(id).orElseThrow(()->{				
			return new CustomValidationApiException("찾을 수 없는 id입니다.");			
		}); 

		
		//2. 이 영속화되어있는 데이터에 수정을 해주면 DB에도 반영이 되어진다.
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;	//더티체킹이 일어나지고 업데이트(수정)가 완료됨.
				
	}
	@Transactional(readOnly = true)
	public List<User> 유저목록(){
		
		return userRepository.mUserList();			
	}
	
	@Transactional
	public void 회원승인(int userId){
		userRepository.mUserAuthSign(userId);
	}
	
	@Transactional(readOnly = true)
	public User 유저검색(String username) {
		User userEntity = userRepository.mfindByUsername(username);
		System.out.println("zz"+userEntity);
		return userEntity;
	}
	
	}
