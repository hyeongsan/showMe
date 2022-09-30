<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<%@ include file="../layout/header.jsp" %>

    <!--사진 업로드페이지 중앙배치-->
        <main class="uploadContainer">
           <!--사진업로드 박스-->
            <section class="upload">
               
               <!--사진업로드 로고-->
                <div class="upload-top">
                 
                       <h1 style="font-style:oblique; font-size: 30px;">show me</h1>
                   
                    <p style="margin-top: 20px;">질문 등록</p>
                </div>
                <!--사진업로드 로고 end-->
                
                <!--사진업로드 Form-->
                <form class="upload-form" action="/image" method="post" enctype="multipart/form-data">
                    <input  type="file" name="file"  onchange="imageChoose(this)" style="display:none;"/>
                   <!--  <div class="upload-img">
                        <img src="/images/person.jpeg" alt="" id="imageUploadPreview" /> 
                    </div> -->
                    
                    <!--사진설명 + 업로드버튼-->
                    <span style="margin: 35px 0;">구체적으로 질문 할 수록 좋은 답변을 받을 확률이 올라가요.</span>
                    <div class="upload-form-detail">
                   		 <input type="text" placeholder="질문을 등록해주세요" name="caption"/>
                        <button class="cta blue">업로드</button>
                    </div>
                    <!--사진설명end-->
                    
                </form>
                <!--사진업로드 Form-->
            </section>
            <!--사진업로드 박스 end-->
        </main>
        <br/><br/>
	
	<script src="/js/upload.js" ></script>
	<%-- 
    <%@ include file="../layout/footer.jsp" %>
    --%>