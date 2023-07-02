<div align="center">

  # **데이터 분석과 예측모델링 웹서비스 개발 과정**
</div>

 <div align="center">

 ## **축산물 가격 비교 및 판매/레시피 커뮤니티 서비스**
 </div>
<br>

### **🍖프로젝트 명**
**meatmeet**

<br>

### ⏰**개발 기간**
2023.05.30 ~ 2023.07.04

<br>

### **🏃개발 인원**
4명

<br>

### **🖥️담당 업무**
|    |**박정은(팀장)**|**김정훈(팀원)**|**이소연(팀원)**|**한민주(팀원)**|
|----|---|---|---|---|
|**Front**|**메인 페이지**<br>- 사용자 페이지<br>- 회원가입 페이지<br>- 로그인 페이지|**레시피 페이지**<br>- 목록 페이지<br>- 관리페이지| **장바구니, 주문 페이지**<br>- 장바구니 페이지<br>- 주문 페이지<br>- 주문조회 페이지|**레시피 페이지**<br>- 상세 페이지<br>- 글 작성 페이지<br>- 글 수정 페이지|
|**Back**|**메인 페이지**<br>- 축산물 가격 시세 데이터 반영<br>**사용자 페이지**<br>- 회원가입 및 로그인<br>- 레시피 작성, 수정, 삭제<br>**장바구니 페이지**<br>- 장바구니 조회<br>- 수량변경||**주문 페이지**<br>- 주문<br>- 주문내역 조회<br>- 주문 삭제| **레시피 페이지**<br>- 레시피 조회<br>- 장바구니에 상품 추가<br>- 댓글 기능|

<br>

### ⚙️**개발 환경**
Windows 10, Spring Boot 2.7.12, Eclipse, Gradle, Java 11.0.16.1, python3.8.10, MySQL, HTML, CSS, JS, Thymeleaf, Lombok, GitHub, Git

<br>

### 📌**설계 배경**
일별로 달라지는 축산물 가격 정보 제공 및 레시피 공유가 가능한 커뮤니티

<br>

### 🛠️**설계 목적**
- 축산물의 전일대비 가격정보 제공 및 판매
- 레시피 커뮤니티를 통해 사이트 방문 유도

<br>

### ✨**기대 효과**
매출 향상, 브랜드 이미지 향상

<br>

### 👪**서비스 대상**
요리에 관심이 많은 2, 30대 여성

<br>
<hr>
<br>

### 🔍**주요 기능**
⭐**축산물 일별 가격 정보**
- 당일 가격 변동 매일 아침 9시시에 반영됨

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/5976fde5-8e79-4b04-9706-8d8dd877bc8e" width="600" height="800" border="1px solid">
  
<br><hr><br>

⭐**회원가입 및 로그인**
- 회원가입 시 유효성 검사 및 아이디 중복 검사 진행 

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/b0108228-4dcd-4e07-b307-ffe9dd973e84" width="400" height="300" border="1px solid">

<br><br>

- 로그인 시 아이디 및 비밀번호 확인

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/ef178d73-b2d2-4e4e-87d3-a653eddc6716" width="400" height="300" border="1px solid">

<br><hr><br>

⭐**카테고리별 레시피 조회**
- 돼지, 소, 닭, 계란, 우유로 카테고리를 분류한 후 그에 해당하는 레시피 조회 가능

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/00d5932f-9ec1-4e24-b088-2d84f396155b" width="500" height="300" border="1px solid">

<br><hr><br>

⭐**레시피 등록 및 수정**
- 음식 이미지 첨부 및 카테고리 지정하여 레시피 등록  

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/d3aefeb0-7f27-4759-974f-1b563f5f518a" width="400" height="400" border="1px solid">

<br><br>

- 작성된 레시피 수정 및 삭제 가능

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/00eae867-9654-4739-a714-d80bd695efa2" width="400" height="400" border="1px solid">

<br><hr><br>

⭐**댓글 작성**
- 레시피 상세 페이지에서 해당 레시피에 대한 댓글 작성

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/4ae3b634-e41c-42f5-92aa-b4a614dd44a9" width="400" height="400" border="1px solid">

<br><hr><br>

⭐**상품 장바구니에 담기**
- 카테고리별 레시피 페이지에서 해당 상품 장바구니에 담아 수량 변경 가능

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/bdafc2da-416f-4af5-9089-b1d159f0bd1a" width="600" height="300" border="1px solid">
  
<br><hr><br>

⭐**상품 주문**
- 장바구니에 담긴 상품을 주문자 정보 입력 후 주문하기

<img src="https://github.com/jeongEEEun/meatmeet/assets/109218705/c12246df-02e3-4e4e-866c-37022ca8f74d" width="600" height="400" border="1px solid">

<br><hr><br>

### 📅**추후 작업**
💡댓글 기능 추가 작업
- 사용자가 작성한 댓글 수정/삭제
- 답글 기능 추가

💡관리자 페이지 추가
- 주문 관리(주문 확인, 취소)
- 레시피 게시판 관리

💡상품 상세 페이지
- 리뷰 및 제품 상세 정보 제공

💡회원 정보 관리
- 주문 시 입력한 정보(주소, 전화번호 등) 저장/수정 기능
- 가입 시 입력한 정보 수정
