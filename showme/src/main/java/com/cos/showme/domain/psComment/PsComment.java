package com.cos.showme.domain.psComment;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cos.showme.domain.image.Image;
import com.cos.showme.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
@Entity
public class PsComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가전략이 데이터베이스를 따라간다.
	private int id;
	
	
	
}
