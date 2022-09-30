<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">

		<!--유저이미지-->
		<div class="profile-left">
			<div class="profile-img-wrap story-border"
				onclick="popup('.modal-image')">
				
				<form id="userProfileImageForm">
					<input type="file" name="profileImageFile" style="display: none;"
						id="userProfileImageInput" />
				</form>

				<img class="profile-image" src="/upload/${dto.user.profileImageUrl}"
					onerror="this.src='/images/person.jpeg'" id="userProfileImage" />
			</div>
		</div>
		<!--유저이미지end-->

		<!--유저정보 및 사진등록 구독하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.user.name}</h2>

				<c:choose>
					<c:when test="${dto.pageOwnerState}">
						<button class="cta" onclick="location.href='/image/upload'">질문등록</button>	
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${dto.subscribeState}">
								<button class="cta blue" onclick="toggleSubscribe(${dto.user.id},this)">구독취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta" onclick="toggleSubscribe(${dto.user.id},this)">구독하기</button>
							</c:otherwise>
						</c:choose>
							
					</c:otherwise>				
				</c:choose>
				
							
				<button class="modi" onclick="popup('.modal-info')">
					<i class="fas fa-cog"></i>
				</button>
			</div>

			<div class="subscribe">
				<ul>
					<li><a href=""> 게시물<span class="imageCount">${dto.imageCount}</span>
					</a></li>
					<li><a href="javascript:subscribeInfoModalOpen(${dto.user.id});"> 구독정보<span>${dto.subscribeCount}</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${user.bio}</h4>
				<h4>${user.website}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 구독하기-->

	</div>
</section>

<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">
			
				<div class="tabBtn">
					<span class="QuestionTab active">질문</span>
					<span class="AnswerTab">답변</span>
				</div>

				<div class="tabContent">
				<!--질문 아이템들-->
				<div class="active">
						<c:forEach var="image" items="${dto.user.images}"> <!-- EL표현식에서 변수명을 적으면 get함수가 자동호출됨 -->
							<div id="storyImageItem-${image.id}">
								<div class="preview__card__wrap">
									<div class="preview__profile">
										<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
									onerror="this.src='/images/person.jpeg'" />
										<div class="preview__username"><a href="/user/${image.user.id}">${image.user.name}</a></div>
										<p class="preview__caption">${image.caption}</p>
									</div>	
								</div>
								
								<c:choose>
										<c:when test="${image.imageState}">
												<button class="deleteBtn" onclick="deleteImage(${image.id})">
													<i class="fas fa-times"></i>
												</button>
										</c:when>
								</c:choose>
							
							<div class="story-list__item showComment">
								<div id="storyCommentList-${image.id}">
									<c:forEach var="comment" items="${image.comments}">	
											<div class="sl__item__contents showComment">
												<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
													<p class="commentUser">
														<b><a href="/user/${comment.user.id}">${comment.user.name}</a> :</b> ${comment.content}
													</p>
																							
												<c:choose>
													<c:when test="${comment.equalUserState}">
														<button class="deleteBtn" onclick="deleteComment(${comment.id})">
															<i class="fas fa-times"></i>
														</button>
													</c:when>
													<c:otherwise>
													
													</c:otherwise>
												</c:choose>
												
												</div>	
											</div>				
									</c:forEach>
							</div>	
										<div class="sl__item__input">
											<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
											<button type="button" onClick="addComment(${image.id})">게시</button>
										</div>
								</div>
							</div>													
						</c:forEach>
					</div>
				<!--답변 아이템들end-->
				 	<div>
						<c:forEach var="answerImage" items="${dto.answerImages}">
							
						
								<div>
									<div class="preview__card__wrap">
										<div class="preview__profile">
											<img class="profile-image" src="/upload/${answerImage.user.profileImageUrl}"
										onerror="this.src='/images/person.jpeg'" />
											<div class="preview__username"><a href="/user/${answerImage.user.id}">${answerImage.user.name}</a></div>
											<p class="preview__caption">${answerImage.caption}</p>
										</div>	
									</div>
								
								<div class="story-list__item showComment">
									<div id="storyCommentList-${answerImage.id}">
										<c:forEach var="answerComment" items="${answerImage.comments}">	
												<div class="sl__item__contents showComment">
													<div class="sl__item__contents__comment" id="storyCommentItem-${answerComment.id}">
														<p>
															<b><a href="/user/${answerComment.user.id}">${answerComment.user.name}</a> :</b> ${answerComment.content}
														</p>
																								
													<c:choose>
														<c:when test="${answerComment.equalUserState}">
															<button class="deleteBtn" onclick="deleteComment(${answerComment.id})">
																<i class="fas fa-times"></i>
															</button>
														</c:when>
														<c:otherwise>
														
														</c:otherwise>
													</c:choose>
													
													</div>	
												</div>				
										</c:forEach>
								</div>	
										<div class="sl__item__input">
											<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${answerImage.id}" />
											<button type="button" onClick="addComment(${answerImage.id})">게시</button>
										</div>
									</div>
						
								</div>
							</c:forEach>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</section>

<!--로그아웃, 회원정보변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">
		<button onclick="location.href='/user/1/update'">회원정보 변경</button>
		<button onclick="location.href='/logout'">로그아웃</button>
		<button onclick="closePopup('.modal-info')">취소</button>
	</div>
</div>
<!--로그아웃, 회원정보변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<p>프로필 사진 바꾸기</p>
		<button onclick="profileImageUpload(${dto.user.id},${principal.user.id})">사진 업로드</button>
		<button onclick="closePopup('.modal-image')">취소</button>
	</div>
</div>

<!--프로필사진 바꾸기 모달end-->

<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="subscribeModalList">

		</div>
	</div>

</div>


<script src="/js/profile.js"></script>

