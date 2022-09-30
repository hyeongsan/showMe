package com.cos.showme.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.showme.config.auth.PrincipalDetails;
import com.cos.showme.domain.image.Image;
import com.cos.showme.domain.image.ImageRepository;
import com.cos.showme.handler.ex.CustomApiException;
import com.cos.showme.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional
	public void 게시글삭제(int id) {			
		try {
			imageRepository.deleteById(id);
		}catch(Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
	
	@Transactional(readOnly=true)
	public List<Image> 인기사진(int principalId){
		//return imageRepository.mPopular();
		
		List<Image> images = imageRepository.mPopular();
		//게시글 본인 여부
		
		images.forEach((image)->{
			if(image.getUser().getId()==principalId) {
				image.setImageState(true);
			}	
		});
		
		
		return images;
	}
	
	@Transactional(readOnly=true)
	public List<Image> 최근사진(int principalId){
		//return imageRepository.mPopular();
		
		List<Image> images = imageRepository.mRecent();
		//게시글 본인 여부
		
		images.forEach((image)->{
			if(image.getUser().getId()==principalId) {
				image.setImageState(true);
			}	
		});
		
		
		return images;
	}
	
	@Transactional(readOnly=true) // select만할꺼니까 readOnly, 그리고 readOnly면 영속성 컨텍스트 변경감지를 해서, 더티체킹, flush(반영) X
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		
		// 2(cos)로그인
		// images에 좋아요 상태 담기
		images.forEach((image)->{
			
			image.setLikeCount(image.getLikes().size());
			
			image.getLikes().forEach((like)->{
				if(like.getUser().getId()==principalId) { // 이 두개가 같으면 좋아요를 했다는거
					image.setLikeState(true);
				}
			});
			
			//게시글 본인 여부
			if(image.getUser().getId()==principalId) {
				image.setImageState(true);
			}
		
			
		});
		
		return images;
	}
	
	@Value("${file.path}") // application.yml 의 file.path
	private String uploadFolder;
	
	//사진업로드 함수 만들꺼임
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
				
		UUID uuid = UUID.randomUUID(); // uuid 사용자가 동일 1.jpg라는 사진을 업로드할 경우 덮어씌어지므로
									   // uuid란? 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규악.
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 이렇게하면 imageFileName에 실제 파일이름이 들어감
		
		System.out.println("이미지파일이름:"+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신 혹은 I/O 가 일어날 때 -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //이미지가 업로드되다가 여기서 실패를 하면 익셉션 처리 해줘야함
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());// imageFileName : 5cf513d-ct34-343b-e5235d_1.jpg이렇게만 넣을 건데
		Image imageEntity = imageRepository.save(image);
		System.out.println(imageEntity.toString());
				
	}
}
