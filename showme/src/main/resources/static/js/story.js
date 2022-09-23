/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */
 
// (0) 현재 로긴한 사용자 아이디
let principalId = $("#principalId").val();




// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
	$.ajax({
		type:"get", //get은 디폴트라 타입안넣어도
		url:`/api/image?page=${page}`,
		dataType:"json"
	}).done(res=>{
		console.log(res);
		
		res.data.content.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		})
		
	}).fail(error=>{
		console.log("오류",error);
	});
}

storyLoad();



function getStoryItem(image) {
	
	let item = `
	
	<div class="preview__card">
		<div class="preview__card__wrap" onclick="toggleComment(${image.id})">
		
			<div class="preview__profile">
					<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
					<div class="preview__username"><a href="/user/${image.user.id}">${image.user.name}</a></div>
					<p class="preview__caption">${image.caption}</p>
			</div>	
					
		
		</div>
	</div>
	
	`;
	
	item += `<div class="story-list__item" id="story-list__item-${image.id}">
	<div class="sl__item__header">
		<div>
			<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
				onerror="this.src='/images/person.jpeg'" />
		</div>
		<div>${image.user.name}</div>
	</div>


	<div class="sl__item__img">
		<img src="/upload/${image.profileImageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__wrap">
		
			<div class="sl__item__contents__content">
				<p>${image.caption}</p>
			</div>
		
			<div class="sl__item__contents__innerWrap">
		
		<div class="sl__item__contents__icon">

			<button>`;
			
							
				if(image.likeState){
					item+=`<i class="fa-heart fas active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;		
				}else{
					item+=`<i class="fa-heart far" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;	
				}		
							
				
				
		item += `				
			</button>
		</div>

		<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>
	</div>
	
		</div>

		<div id="storyCommentList-${image.id}">`;


	image.comments.forEach((comment)=>{
		item +=`
		<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
		<p class="commentUser">
			<a href="/user/${comment.user.id}">${comment.user.username}</a> : ${comment.content}
		</p>`;
				
				
	if(principalId == comment.user.id){
								
		item += `<button class="deleteBtn" onclick="deleteComment(${comment.id})">
						<i class="fas fa-times"></i>
					</button>`;
					
					}
				
		
		item += `</div>`;	
			
			
			});

	

		item += `</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
			<button type="button" onClick="addComment(${image.id})">게시</button>
		</div>

	</div>
</div>`;

	return item;
}

function toggleComment(imageId){
	$(`#story-list__item-${imageId}`).toggleClass("showComment");
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => { //윈도우의 스크롤 이벤트

	//console.log("윈도우 scrollTop()",$(window).scrollTop()); // 내가 스크롤링 하는 양
	//console.log("문서의 높이",$(document).height()); // 이 문서 전체의 높이
	//console.log("윈도우 높이",$(window).height()); // 내가 창사이즈 줄이면 그에따라 바뀜
	
	// 문서의 높이 - 윈도우 높이 = 내가 스크롤 맨밑에까지 했을 때의 수치
	
	let checkNum = $(window).scrollTop() - ($(document).height()-$(window).height());
	console.log(checkNum);
	
	if(checkNum < 1 && checkNum > -1){ // 대략 이 정도 구간잡으면됨
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	
	if (likeIcon.hasClass("far")) {// 좋아요 하겠다 
		
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); 
			let likeCount = Number(likeCountStr) + 1;
			
			console.log("좋아요카운트증가",likeCount);
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log(error);
		});
		
	} else {											   // 좋아요취소 하겠다 
	
		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text(); 
			let likeCount = Number(likeCountStr) - 1;
			
			console.log("좋아요카운트감소",likeCount);
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		
		}).fail(error=>{
			console.log(error);
		});	
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId:imageId,
		content: commentInput.val()
	}
	
	//alert(data.content);
	console.log(data); // javascript
	console.log(JSON.stringify(data)); // json형식

//	if (data.content === "") {
//		alert("댓글을 작성해주세요!");
//		return;
//	}
	
	//ajax통신
	$.ajax({
		type:"post",
		url:"/api/comment",
		data: JSON.stringify(data),
		contentType:"application/json; charset=utf-8",
		dataType:"json" //응답받을 데이터 (res) 
	}).done(res=>{
		console.log("성공",res);
		
	let comment = res.data;
		
	let content = `
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p>
		      <b><a href="${comment.user.id}"></a>${comment.user.username} :</b>
		      ${comment.content}
		    </p>
		    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
		  </div>`;
		  
	commentList.prepend(content);
	
	}).fail(error=>{
		alert(error.responseJSON.data.content);
		console.log("오류",error.responseJSON.data.content);
	});
	
	
	commentInput.val(""); // 인풋 필드를 깨끗하게 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		console.log("성공",res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("오류",error);
	})
}








