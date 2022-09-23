package com.cos.showme.web.dto.user;

import java.util.List;

import com.cos.showme.domain.image.Image;
import com.cos.showme.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private boolean pageOwnerState; // 이 페이지의 주인인지 아닌지에 대한 여부
	private int imageCount;
	private boolean subscribeState; // 구독을 한 상태인지
	private int subscribeCount; // 구독자수
	private List<Image> answerImages;
	private User user;	
}
