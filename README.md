## TDD-Tutorial

- 멤버십을 적립하는 서비스를 개발한다.
- 현재 지원중인 멤버십은 네이버, 카카오, 라인 3가지 멤버십이 있으며, 사용자는 원하는 멤버십을 등록할 수 있다.
- 포인트 적립비율은 결제금액의 1%로 고정되며, 추후에 고정 금액(1000원)으로 확장하여 적립될 수 있어야 한다.

 

#### 기능 요구 사항

- 멤버십 연결하기, 멤버십 조회, 멤버십 연결끊기, 포인트 적립 API를 구현한다.
- 사용자 식별값은 문자열 형태이며 "X-USER-ID" 라는 HTTP Header 로 전달된다. 이 값은 포인트 적립할 때 바코드 대신 사용된다.
- Content-type 응답 형태는 application/json(JSON) 형식을 사용한다.
- 각 기능 및 제약사항에 대한 개발을 TDD, 단위테스트를 기반으로 진행한다.

 

#### 상세 기술 구현 사항

- 멤버십 등록 API
  - 기능: 멤버십을 등록한다.
  - 요청: 사용자 식별값, 멤버십 이름, 포인트
  - 응답: 멤버십 ID, 멤버십 이름
- 멤버십 전체 조회 API
  - 기능: 모든 멤버십을 조회한다.
  - 요청: 사용자 식별값
  - 응답: {멤버십 ID, 멤버십 이름, 포인트, 가입 일시}의 멤버십 리스트
- 멤버십 상세 조회 API
  - 기능: 1개의 멤버십을 상세 조회한다.
  - 요청: 사용자 식별값, 멤버십 ID
  - 응답: 멤버십 ID, 멤버십 이름, 포인트, 가입일시
- 멤버십 삭제 API
  - 기능: 멤버십을 삭제한다.
  - 요청: 사용자 식별값, 멤버십 번호
  - 응답: X
- 멤버십 포인트 적립 API
  - 기능: 멤버십 포인트를 결제 금액의 1%만큼 적립한다.
  - 요청: 사용자 식별값, 멤버십 ID, 사용 금액
  - 응답: X

 

#### 기술 요구 사항

- 개발 언어: Java 8
- Framework: Spring Boot
- ORM: JPA
- DB: MsSQL/H2