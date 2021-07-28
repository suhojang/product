### KaKaopay 과제

- 목적
  * 다량의 트래픽을 고려 
  * SCALE 관리를 용이하기 위한 전략
       
#### Dependency
  + WebFlux : reactive-stack web framework이며 non-blocking에 reactive stream을 지원
    + 장점 : 고성능, Spring 과 완벽한 통합, netty 지원, 비동기 non-blocking 메세지 처리
    + 단점 : 오류처리가 다소 복잡하다. Back Pressure 기능 없음
      

  + R2DBC : SQL 데이터베이스를 위한 리액티브 API
    + WebFlux 함께 Non-Blocking 을 위해 추가 
    

  + Kubernetes 및 Docker Swarm 에서의 배포 전략을 위한 jib

#### ISSUE
  + R2DBC
    + 기존에 사용하던 JPA의 MappedBy(@OneToMany, @OneToOne, @ManyToOne, @ManyToMany) 지원 되지 않으므로 Mapper Class 를 추가하여 해결
    + 다만 현재 개발 한 R2DBC Repository 에서 Native SQL Query 를 사용하는 방법은 이질감을 느끼게 함.
      + Query DSL과 연계하여 처리 할 수 있는 방법이 있음. 
      + 하지만 이 프로젝트는 과제 이므로 QueryDSL 은 과한 느낌이 들어 추가하지 않음. 
