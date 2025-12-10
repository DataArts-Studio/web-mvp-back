# TC Tool Back-End MVP
`TC(Test Case)`를 1인 혹은 소규모 팀에서 보다 **체계적으로 작성, 관리, 실행**하기 위한 프로젝트의 **back-end 리포지토리**입니다.

<br>

## 📁 프로젝트 폴더 구조

아래 구조는 **Hexagonal Architecture(핵사고날 아키텍처)** 원칙을 따르며, 도메인 로직과 인프라가 명확히 분리되도록 설계되었습니다.

```shell
src/main/java/com/dataarts/webmvpback
 ├── project
 │   │
 │   ├─ application                  // 유즈케이스 계층 (비즈니스 흐름 담당)
 │   │   ├─ port
 │   │   │   ├─ in                  // 입력 포트: 유즈케이스 정의 (Controller -> Service)
 │   │   │   │   ├─ CreateProjectUseCase      // 프로젝트 생성 기능 인터페이스 (입력 포트) 
 │   │   │   │   └─ CreateProjectCommand      // 유즈케이스 실행에 필요한 입력값 DTO
 │   │   │   └─ out                 // 출력 포트: 인프라 호출 인터페이스 (Service -> Adapter)
 │   │   │       ├─ SaveProjectPort          // 프로젝트 저장 요청 인터페이스
 │   │   │       └─ CheckProjectNamePort     // 프로젝트 이름 중복 확인 인터페이스
 │   │   │
 │   │   ├─ service                 // 유즈케이스 구현체 (비즈니스 로직 핵심)
 │   │   │   └─ ProjectCreateService        // CreateProjectUseCase 구현, 검증/도메인 생성/저장 처리
 │   │   │
 │   │   └─ validator               // 유즈케이스 입력 및 규칙 검증
 │   │       └─ ProjectCreateValidator       // 프로젝트 생성 관련 검증 로직
 │   │
 │   ├─ domain                      // 순수 도메인 계층 (비즈니스 규칙)
 │   │   ├─ Project                 // Aggregate Root: 핵심 엔티티, 비즈니스 상태, 행위 보유
 │   │   └─ ProjectId               // VO: UUID 기반 프로젝트 식별자
 │   │
 │   └─ adapter                     // 외부 시스템과 연결
 │       └─ out
 │           └─ persistence         // 데이터베이스 어댑터
 │               ├─ jpa
 │               │   ├─ ProjectJpaEntity      // JPA 엔티티 - DB 테이블 매핑
 │               │   └─ ProjectJpaRepository  // Spring Data JPA 인터페이스
 │               │
 │               ├─ ProjectPersistenceAdapter // SaveProjectPort 구현체 (DB 저장 실행)
 │               │
 │               └─ mapper
 │                   └─ ProjectMapper         // Domain <-> JPA Entitiy 변환 전담
 │
 ├── testcase
 ├── testsuite 
 ├── milestone
 └── shared
 ```

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
* 이 프로젝트의 핵심은 테스트 케이스(TC)의 도메인 규칙과 흐름을 정확하게 표현하는 것으로 생각됩니다. 
* 핵사고날 구조는 프로젝트의 중심을 아키텍처가 아니라 도메인 모델에 두기 때문에
프로젝트가 커져도 도메인 규칙이 흐트러지는 일을 방지해줍니다. 