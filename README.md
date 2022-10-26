# moais 미니프로젝트 

## 사용기술 
### -> Spring boot, JPA, H2 Database, Swagger3 (http://localhost:8080/swagger-ui/index.html#), JDK 1.8, JWT 

## 추가고려 사항 
### 1. 멀티모듈 구성으로 프로젝트 확장시 용이
### 2. JWT 사용한 확장성 제공
### 3. DDD 를 고려한 Entity 설계 

## 흐름 
- 회원가입 -> 로그인 하여 token 발행 -> Todo, 회원탈퇴 기능은 token 유효성을 가지어 해당 기능 실행가능


