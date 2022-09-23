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
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
    <div class="container">
        <main class="loginMain">
        <!--로그인섹션-->
            <section class="login">
               <!--로그인박스-->
                <article class="login__form__container">
                   <!--로그인 폼-->
                   <div class="login__form">
                       <h1 style="font-style:oblique; font-size: 30px;">show me</h1>
                        
                        <!--로그인 인풋-->
                        <form class="login__input">
                            <input class="loginInput" type="text" name="username" placeholder="유저네임" required="required" />
                            <input type="password" name="password" placeholder="비밀번호" required="required" />
                            <button class="signInBtn" type="button" onclick="testAjax()">로그인</button>
                           <!-- <button class="testBtn" type="button" onclick="testBtn()">테스트</button>-->
                        </form>
                        
                      
                        
                        <!--로그인 인풋end-->
                        
                        <!-- 또는 -->
                        <div class="login__horizon">
                            <div class="br"></div>
                          <!--   <div class="or">또는</div> -->
                            <div class="br"></div>
                        </div>
                        <!-- 또는end -->
                        
                        <!-- Oauth 소셜로그인 
                        <div class="login__facebook">
                            <button onclick="javascript:location.href='/oauth2/authorization/facebook'">
                                <i class="fab fa-facebook-square"></i>
                            </button>
                        </div>-->
                        <!-- Oauth 소셜로그인end -->
                    </div>
                    
                    <!--계정이 없으신가요?-->
                    <div class="login__register">
                        <span>계정이 없으신가요?</span>
                        <a href="/auth/signup">가입하기</a>
                    </div>
                    <!--계정이 없으신가요?end-->
                </article>
            </section>
        </main>
        
    </div>
</body>
<script src="/js/signin.js"></script>
</html>