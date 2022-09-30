<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<head>
	<link rel="stylesheet" href="/css/userlist.css">
</head>
<body>

		<main class="main">
			<section class="container">
				<!--전체 리스트 시작-->
				<article class="story-list" id="storyList">
		
					<!-- 탭 목록 -->
					<div class="tabListAdmin">
						<div>유저아이디</div>
						<div>사진</div>	
						<div>닉네임</div>
						<div>이메일주소</div>
						<div>전화번호</div>	
						<div></div>						
					</div>
					
					<!--전체 리스트 아이템-->
					<div class="tabContentAdminDiv">
						<c:forEach var="user" items="${userlist}">
							<div class="tabContentAdmin">
								<div>${user.id}</div>
								<div>
									<img class="previewPhoto" src="/upload/${user.profileImageUrl}" alt="" onerror="this.src='/images/person.jpeg'">
								</div>
								<div>${user.username}</div>
								<div>${user.email}</div>
								<div>${user.phone}</div>
								<button class="authBtn" onclick="authSign(${user.id})">승인</button>
							</div>
						</c:forEach>
						
						<div class="modalBackground"></div>
						<div class="modalPhoto">
							<div>
								<img src=""/>
								<button class="closeModal">닫기</button>
							</div>							
						</div>
						
					</div>
				</article>
			</section>
		</main>
</body>
<script>
	$(function(){
		$('.previewPhoto').click(function(){
			var src = $(this).attr('src');
			$('.modalPhoto img').attr('src',src);
			$('.modalPhoto img').addClass('showModal');
			$('.closeModal').addClass('showClose');
			$('.modalBackground').show();
		})
		
		$('.closeModal').click(function(){
			$('.modalPhoto img').removeClass('showModal');
			$('.closeModal').removeClass('showClose');
			$('.modalBackground').hide();
					
		})
		
		
		
	})
</script>
<script src="/js/userlist.js" ></script>
</html>