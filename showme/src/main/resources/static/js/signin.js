	

		function testAjax(){
			// ajax통신으로 해당 아이디의 isAuth를 받아온다. 
			// isAuth를 조건문걸어서 true일시 self.location="/auth/signin", false일시 alert
			
			/*
			console.log("클릭");
			
			let data = $(".login__input").serialize(); 
			console.log(username);
			let dataUsername = {
				username:username
			}
			 */
			 let username = $('.loginInput').val();
			 console.log(username);
			 console.log("산산");
			 
			$.ajax({
				type:"get", //get은 디폴트라 타입안넣어도
				url:`/apis/user/authChecking/${username}`,
				dataType:"json"
			}).done(res=>{
				console.log(res);
				if(res.data.isAuth==null){
					alert("회원가입 승인 중입니다.");
				}else{
					console.log("?");
					$('.login__input').attr("method","POST").attr("action" , "/auth/signin").submit();		
				}
				
			}).fail(error=>{
				console.log("오류",error);
			});
			
		
			
		};     


     
     
