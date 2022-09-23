package com.cos.showme.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

//NotNull = Null값 체크
//NotEmpty = 빈값이거나 null 체크
//NotBlank = 빈값이거나 null 체크 그리고 빈 공백(스페이스)까지

@Data
public class CommentDto {
	@NotBlank // 빈값이거나 null 체크
	private String content;
	@NotNull 
	private Integer imageId;
	
	//toEnetity가 필요없다.
}