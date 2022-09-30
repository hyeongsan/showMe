<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!--인기 게시글-->
<main class="popular">
	<div class="exploreContainer">

		<!--인기게시글 갤러리(GRID배치)-->
		<div class="popular-gallery">	
			
			<div class="tab">
				<span class="tabRecent"><a href="/image/recent">최신순</a></span>
				<span class="tabPopular"><a href="/image/popular">인기순</a></span>
			</div>	
		
			<c:forEach var="image" items="${images}">
				<div class="p-img-box">
				<!-- <a href="/user/${image.user.id }"> <img src="/upload/${image.profileImageUrl}" />
					</a> -->
					<div class="preview__card pPage" id="storyImageItem-${image.id}">
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
							
					<div class="story-list__item">
							<div id="storyCommentList-${image.id}">
								<c:forEach var="comment" items="${image.comments}">	
									
										<div class="sl__item__contents">
											<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
												<p class="commentUser">
													<b><a href="/user/${comment.user.id}">${comment.user.name}</a>  :</b> ${comment.content}
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

	</div>
</main>
<script src="/js/popular.js" ></script>
<%--
<%@ include file="../layout/footer.jsp"%>
--%>
