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
		<div class="sl__item__contents">
		  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
		    <p class="commentUser">
		      <b><a href="/user/${comment.user.id}">${comment.user.name}</a> :</b>
		      ${comment.content}
		    </p>
		    <button class="deleteBtn" onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
		  </div>
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

//(6) 게시물 삭제
function deleteImage(imageId){
	$.ajax({
		type:"delete",
		url:`/api/image/${imageId}/delete`,
		dataType:"json"
	}).done(res=>{
		console.log("성공",res);
		console.log(imageId);
		$(`#storyImageItem-${imageId}`).remove();
	}).fail(error=>{
		console.log("오류",error);
	})
}