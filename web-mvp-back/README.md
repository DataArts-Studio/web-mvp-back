# TC Tool Back-End MVP
`TC(Test Case)`를 1인 혹은 소규모 팀에서 보다 **체계적으로 작성, 관리, 실행**하기 위한 프로젝트의 **back-end 리포지토리**입니다.

<br>

## 📁 프로젝트 폴더 구조

아래 구조는 **Hexagonal Architecture(핵사고날 아키텍처)** 원칙을 따르며, 도메인 로직과 인프라가 명확히 분리되도록 설계되었습니다.

```shell
src/main/java/com/data_arts_studio/web_mvp_back
├── project
│   ├── application
│   │   ├── port
│   │   │   ├── in          // UseCase, Command
│   │   │   └── out         // Port 인터페이스
│   │   ├── service         // UseCase 구현
│   │   ├── validator       // 입력/비즈니스 검증
│   │   └── exception       // ErrorCode, BusinessException
│   ├── domain              // 도메인 모델/ID/행위
│   └── adapter
│       ├── in
│       │   └── web
│       │       ├── request   // 요청 DTO
│       │       └── response  // 응답 DTO
│       └── out
│           └── persistence
│               ├── jpa        // JpaEntity, JpaRepository
│               ├── mapper     // Domain <-> Entity 매핑
│               └── *PersistenceAdapter // out port 구현체
├── test_case
├── test_suite
├── milestone
├── test_run
└── shared
    ├── BaseEntity.java                 // 공통 감사 필드(createdAt, updatedAt, archivedAt) + 상태 변경 유틸
    ├── LifecycleStatus.java            // 공통 생명주기 상태(ACTIVE, ARCHIVED, DELETED)
    └── exception
        ├── BaseException.java          // 도메인/애플리케이션 공통 비즈니스 예외 부모
        ├── ErrorCode.java              // 에러코드 공통 인터페이스(코드, 메시지, HttpStatus)
        ├── ErrorResponse.java          // API 에러 응답 포맷
        └── GlobalExceptionHandler.java // 전역 예외 처리(@RestControllerAdvice)
 ```

> 각 aggregate(`project`, `test_case`, `test_suite`, `milestone`, `test_run`)는 동일한 내부 구조(`application` / `domain` / `adapter`)를 따릅니다.

<br>


# 아키텍처 선택 이유 
* 저희 프로젝트에선 다음과 같은 비전이 있습니다. 
    1. Supabase 기반으로 빠르게 MVP 개발
    2. 제품이 안정화되면 Spring Boot 기반으로 마이그레이션
* 이 과정에서 중요한 것은 저장소 교체 및 구조 변경을 도메인 로직 변경 없이 지원하는 것이라고 판단했습니다. 

## 핵사고날 아키텍처를 선택한 구체적 이유
### 1. DB 교체와 구조 변경에 유연함
* 제가 생각하는 테스트 관리 시스템은 프로젝트가 계속 진행되는 과정에서 다음과 같은 변화가 발생할 가능성이 높습니다. (테이블 스키마 수정, 외부 스토리지와의 연동, 저장소 모듈 분리)

* 핵사고날 구조에서는 데이터 접근 로직이 adapter 영역에 격리되어 있어, 도메인 로직에 영향을 주지 않고 유연하게 DB를 교체하거나 수정할 수 있습니다.

### 2. 기능 확장에 강한 구조
* 또한 제가 고려해볼 수 있는 프로젝의 향후 추가될 수 있는 기능은 다음과 같습니다. 
    1. 테스트 실행 자동화
    2. 테스트 실행 후 상세 리포트 생성
* 제가 생각하는 이 기능의 추가를 핵사고날 아키텍처는 도메인을 건드리지 않고 포트와 어댑터 추가만으로 확장하는데 유연함을 제공합니다. 

### 3. 도메인 중심 설계로 안정성 확보
* 이 프로젝트의 핵심은 테스트 케이스(TC)의 도메인 규칙과 흐름을 정확하게 표현하는 것이라고 생각합니다. 
* 핵사고날 구조는 프로젝트의 중심을 아키텍처가 아니라 도메인 모델에 두기 때문에 프로젝트가 커져도 도메인 규칙이 흐트러지는 일을 방지해줍니다. 
