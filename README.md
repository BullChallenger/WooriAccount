# WooriAccount
우리FISA 2기 간이 프로젝트
뽕섭님 ERD랑 쿼리문 보내주세요 


## 프로젝트 구조
### 1. domain
- entity
- dto

### 2. controller

### 3. service

### 4. repository
- common
  - jpa에서 제공하는 repository 기술을 사용하기 위해 메서드 명시를 위한 인터페이스 작성을 이곳에서 진행합니다.
- jpa
  - Repository와 common에서 작성해둔 미완성 메서드를 사용할 수 있도록 extends합니다.
- querydsl
  - querydsl 기술을 적용한 레포지토리입니다.
