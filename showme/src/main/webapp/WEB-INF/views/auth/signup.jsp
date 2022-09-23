<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Show Me</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>
<style>
.pointer{
	pointer-events:auto;
	background-color:#0095f6;
}

.pointer.none{
	pointer-events:none;
	background-color:gray;
}

#userProfileImage{
    border-radius: 50%;
    margin-bottom: 30px;
    cursor:pointer;
}

.profileExplain{
    font-size: 12px;
    margin-bottom: 10px;
    }
#inputCertifiedNumber{
    margin: 15px 0 0 0;
    }    
</style>

<body>
    <div class="container">
        <main class="loginMain">
           <!--회원가입섹션-->
            <section class="login">
                <article class="login__form__container">
                  
                   <!--회원가입 폼-->
                    <div class="login__form">
                        <!--로고-->
                        <h1 style="font-style:oblique; font-size: 30px;">show me</h1>
                         <!--로고end-->
                         
                         <!--회원가입 인풋-->
                        <form class="login__input" action="/auth/signup" method="post" enctype="multipart/form-data">
                            <input  type="file" name="file" style="display:none;" id="inputFile" required="required"/>
                        <img width="100px" height="100px" class="profile-image" src=""
					onerror="this.src='/images/noimage.jpg'" id="userProfileImage" />
					<span class="profileExplain">얼굴을 식별할 수 있는 사진을 업로드해주세요. (심사예정)</span>
                            <input type="text" name="username" placeholder="아이디" required="required"/>
                            <input type="password" name="password" placeholder="패스워드" required="required"/>
                            <input type="email" name="email" placeholder="이메일" required="required"/>                           
                            <input type="text" name="name" placeholder="이름" required="required"/>
                             <span style="margin:10px 0;">휴대폰 인증</span>
                            <input id="inputPhoneNumber" type="text" placeholder="휴대폰 번호"/>
                            <button type="button" id="sendPhoneNumber">번호전송</button> 
                            <input id="inputCertifiedNumber" placeholder="인증번호를 입력해주세요"/>
                            <button type="button" id="checkBtn">번호확인</button> 
                            <button class="pointer">가입</button>
                        </form>
                        <!--회원가입 인풋end-->
                    </div>
                    <!--회원가입 폼end-->
                    
                    <!--계정이 있으신가요?-->
                    <div class="login__register">
                        <span>계정이 있으신가요?</span>
                        <a href="/auth/signin">로그인</a>
                    </div>
                    <!--계정이 있으신가요?end-->
                </article>
            </section>
        </main>
    </div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="/js/signup.js" ></script>
</html>