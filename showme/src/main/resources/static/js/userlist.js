function authSign(userId){
			
			let data = {
					userId:userId
				}
			
			$.ajax({
				type:"put",
				url:`/api/user/${userId}/authSign`,
				data:JSON.stringify(data),
				contentType:"application/json; charset=utf-8",
				dataType:"json"
			}).done(res=>{
				alert("승인완료");
			}).fail(error=>{
				alert("승인실패");
			});
		}