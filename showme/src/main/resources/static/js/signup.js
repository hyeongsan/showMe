$(function(){
	
	   $('#sendPhoneNumber').click(function(){
            let phoneNumber = $('#inputPhoneNumber').val();
            //Swal.fire('인증번호 발송 완료!')


            $.ajax({
                type: "GET",
                url: "/check/sendSMS",
                data: {
                    "phoneNumber" : phoneNumber
                },
                success: function(res){
                    $('#checkBtn').click(function(){
                        if($.trim(res) ==$('#inputCertifiedNumber').val()){
                            alert(
                                '인증성공!',
                                '휴대폰 인증이 정상적으로 완료되었습니다.',
                                'success'
                            )
                            
                            $('.pointer').removeClass("none");
/*
                            $.ajax({
                                type: "GET",
                                url: "/update/phone",
                                data: {
                                    "phoneNumber" : $('#inputPhoneNumber').val()
                                }
                            })
                             */
                           // document.location.href="/auth/signin";
                        }else{
                            Swal.fire({
                                icon: 'error',
                                title: '인증오류',
                                text: '인증번호가 올바르지 않습니다!',
                                footer: '<a href="/home">다음에 인증하기</a>'
                            })
                        }
                    })


                }
            })
        });
        
/*사진업로드 */

$('#userProfileImage').click(function(){
	console.log("실행");
	
	$("#inputFile").click();
	$("#inputFile").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}
		
	
			
				// 사진 전송 성공시 이미지 변경
				let reader = new FileReader();
				reader.onload = (e) => {
					$("#userProfileImage").attr("src", e.target.result);
				}
				reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
				
		
	});
})
		  
		  $('.pointer').click(function(){
		
			if($('#inputFile').val() == "") {
				alert("프로필사진을 첨부해주세요.");
			    $("#inputFile").focus();
			    return;
			}
				
			})   


})


