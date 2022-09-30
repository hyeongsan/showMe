/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 유저 프로필 사진 변경
  (4) 사용자 정보 메뉴 열기 닫기
  (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (7) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId,obj) {
	
	if ($(obj).text() === "구독취소") {
		
		$.ajax({
			type:"delete",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독취소실패",error);
		});
		
		
	} else {
		
		$.ajax({
			type:"post",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독하기실패",error);
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");
	
	$.ajax({
		url:`/api/user/${pageUserId}/subscribe`,
		dataType:"json"
	}).done(res=>{
		console.log(res.data);
		
		res.data.forEach((u)=>{ // 한바퀴 돌 때마다 유저정보(u)를 가져온다.
			let item = getSubscribeModalItem(u);
			$("#subscribeModalList").append(item);
		});
		
	}).fail(error=>{
		console.log("구독정보 불러오기 오류",error);
	});
}

function getSubscribeModalItem(u) {
	let item =`<div class="subscribe__item" id="subscribeModalItem-${u.id}">
	<div class="subscribe__img">
		<img src="/upload/${u.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
	</div>
	<div class="subscribe__text">
		<h2>${u.username}</h2>
	</div>
	<div class="subscribe__btn">`;
	
	if(!u.equalUserState){ // 동일한 유저가 아닐 때만 버튼이 만들어져야한다.
	  if(u.subscribeState){ // 구독한 상태
			item+=`<button class="cta blue" onclick="toggleSubscribe(${u.id},this)">구독취소</button>`;		
		}else{ // 구독안한 상태
			item+=`<button class="cta" onclick="toggleSubscribe(${u.id},this)">구독하기</button>`;		
		}
	}
				
	item+=`</div>
			</div>`;
	
	return item;
}




// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload(pageUserId, principalId) {
	
	//console.log("pageUserId",pageUserId);
	//console.log("principalId",principalId);
	
	if(pageUserId!=principalId){
		alert("프로필 사진을 수정할 수 없는 유저입니다.");
		return;
	}
	
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		
		//서버에 이미지를 전송 ( 이미지는 폼태크안에 인풋/파일에 있는것을 가져와야한다.)
		let userProfileImageForm = $('#userProfileImageForm')[0];
		console.log(userProfileImageForm); // form태그 그 자체
		
		//formData 객체를 이용하면 form태그의 필드와 그 값을 나타내는 일련의 key,value를 담을 수 있다.
		let formData = new FormData(userProfileImageForm);// form태그에 있는 값들
		
		$.ajax({
			type:"put",
			url:`/api/user/${principalId}/profileImageUrl`,
			data:formData,
			contentType:false, //contentType은 따로 지정을 안하면 X-www-form-urlencoded 임, 그래서 false -> 이걸로 파싱되는것을 방지함.
			processData:false, //contentType을 false로 두면 내가보내는 데이터타입이 QueryString으로 자동설정됨. 그래서 해제해줘야함.
		    enctype:"multipart/form-data", // enctype을 폼태그에 설정하면 굳이 여기 안적어도 되긴함
		    dataType:"json"
		}).done(res=>{
			
				// 사진 전송 성공시 이미지 변경
				let reader = new FileReader();
				reader.onload = (e) => {
					$("#userProfileImage").attr("src", e.target.result);
				}
				reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
				
		}).fail(error=>{
				console.log("오류",error);
		});

		
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}


/////

$(function(){
	$('.preview__card__wrap').click(function(){
	$(this).parent().find('.story-list__item').toggleClass("showComment");
	})
})


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
		    <p class="commentUser">
		      <b><a href="/user/${comment.user.id}">${comment.user.name}</a> :</b>
		      ${comment.content}
		    </p>
		    <button class="deleteBtn" onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
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

//(6) 게시글 삭제
function deleteImage(imageId){
	$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/delete`,
			dataType:"json"
	}).done(res=>{
		
		let imageCount = $('.imageCount').text();
		
		$(`#storyImageItem-${imageId}`).remove();
		$('.imageCount').text(imageCount-1);	
			
	}).fail(error=>{
		console.log("오류",error);
	})
}


//탭버튼

$(function(){
	//var tabBtnIndex = $('.tabBtn span').index();
	//var tabContent = $('.tabContent > div').eq(tabBtnIndex);
	
	$('.tabBtn span').click(function(){
		var tabBtnIndex = $(this).index();
		var tabContent = $('.tabContent > div').eq(tabBtnIndex);
		
		console.log(tabBtnIndex);
		console.log(tabContent);
		
		$('.tabBtn span').removeClass("active");
		$('.tabContent > div').removeClass("active");
		$(this).addClass("active");
		
		tabContent.addClass("active");
	})
	
	
})

