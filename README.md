# WooriAccount
우리FISA 2기 간이 프로젝트

## 프로젝트 소개
### 목적
- 해당 프로젝트 제작을 통해 금융 도메인 지식 향상을 도모하고자 합니다.
- 더나은 금융 생활을 할수 있도록 편리한 UI, 금융 도메인 기능을 제공하는 웹 애플리케이션을 만들고자 합니다.
  
### 개발 기간
- 1차 고도화 (~2024.01.03)
  - 계좌 송금과 조회에 집중한 프로젝트 개발 
- 2차 고도화 (2024.01.03 ~ 2023.01.10)
  - 스프링 시큐리티 도입
  - 세션 대신 jwt 사용에 따른 해당 토큰 저장을 위한 redis 사용
- 3차 고도화
### 개발 인원
|***팀원***|역할|설명|
|----------|----|----|
|**김민석**|FrontEnd|전체적인 뷰페이지와 관련된 프론트 작업 진행|
|**송봉섭**|BackEnd|회원 도메인과 관련된 기능,테스트 작업 진행|
|**임유리**|BackEnd|계좌 도메인과 관련된 기능, 테스트 작업 진행|
|**염휘주**|BackEnd|계좌 도메인과 인증/인가와 관련된 기능, 테스트 작업 진행|
|**조승빈**|BackEnd|송금 내역과 관련된 기능, 테스트, 코드 리팩토링 작업 진행|


## 개발 구조
### ERD
![image](https://github.com/BullChallenger/WooriAccount/assets/81970382/56a208d9-ff7f-45cc-ac1f-36a32a524a68)

### 개발 환경
![제목 없는 다이어그램](https://github.com/BullChallenger/WooriAccount/assets/81970382/e59c80fe-7c48-4e6d-90ce-31539b290e7b)

### 프로젝트 구조
### 1. domain
- entity
  - BaseEntity
  - Customer
  - Account
  - TxHistory
- dto

### 2. controller
- RestContoller
- Contoller

### 3. service
- service
  
### 4. repository
- common
  - jpa에서 제공하는 repository 기술을 사용하기 위해 메서드 명시를 위한 인터페이스 작성을 이곳에서 진행합니다.
- jpa
  - Repository와 common에서 작성해둔 미완성 메서드를 사용할 수 있도록 extends합니다.
- querydsl
  - querydsl 기술을 적용한 레포지토리입니다.

### 5. Exception
- CustomException

