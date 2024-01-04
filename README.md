# WooriAccount
우리FISA 2기 간이 프로젝트
뽕섭님 ERD랑 쿼리문 보내주세요 

## 프로젝트 소개
- 미니 뱅킹 시스템
- 금융권의 여러 도메인 중 계좌 송금, 조회에 집중한 프로젝트(1차 고도화 작업) 

## ERD
![image](https://github.com/BullChallenger/WooriAccount/assets/81970382/56a208d9-ff7f-45cc-ac1f-36a32a524a68)

## 개발 환경
![제목 없는 다이어그램](https://github.com/BullChallenger/WooriAccount/assets/81970382/e59c80fe-7c48-4e6d-90ce-31539b290e7b)


## 프로젝트 구조
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

