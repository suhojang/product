### KaKaopay 과제

- 목적
  * 다량의 트래픽을 고려 
  * SCALE 관리를 용이하기 위한 전략
       
#### Dependency
  + WebFlux : reactive-stack web framework이며 non-blocking에 reactive stream을 지원
    + 고성능, Spring 과 완벽한 통합, netty 지원, 비동기 non-blocking 메세지 처리

  + R2DBC : SQL 데이터베이스를 위한 리액티브 API
    + WebFlux 함께 Non-Blocking 을 위해 추가 

  + Kubernetes 및 Docker Swarm 에서의 배포 전략을 위한 jib
    + CI/CD 를 이용하여 test, build, deploy stage 전략 필요

#### ISSUE
  + R2DBC
    + 기존에 사용하던 JPA의 MappedBy(@OneToMany, @OneToOne, @ManyToOne, @ManyToMany) 지원 되지 않으므로 Mapper Class 를 추가하여 해결
    + 다만 현재 개발 한 R2DBC Repository 에서 Native SQL Query 를 사용하는 방법은 이질감을 느끼게 함.
      + Query DSL과 연계하여 처리 할 수 있는 방법이 있음. 
      + 하지만 이 프로젝트는 과제 이므로 QueryDSL 은 과한 느낌이 들어 추가하지 않음. 

#### API 목록
  + 전체 투자상품 조회
    > curl --location --request GET 'http://localhost:8080/api/products' \
      --header 'Content-Type: application/json' \
      --header 'X-USER-ID: 1'
     
  + 투자하기
    > curl --location --request POST 'http://localhost:8080/api/users/products' \
     --header 'Content-Type: application/json' \
     --header 'X-USER-ID: 1' \
     --data-raw '{
     "product_id" : "1"
     ,"my_investing_amount": "100000"
     }'
    > 

  + 나의 투자상품 조회
    > curl --location --request GET 'http://localhost:8080/api/users/products' \
    --header 'Content-Type: application/json' \
    --header 'X-USER-ID: 1'