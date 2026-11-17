# devquest 에서 가져올 것

> 원본: `bangddong/switch-job-quest` (archived, 공개). 로컬 클론 `../switch-job-quest`.
> 🔴 **복사하지 말고 다시 짠다** — 학습이 목적이다. 아래는 *"어느 마일스톤에서 참고할지"* 의 지도.
> 원본 퍼머링크는 커밋 `da1dc20` 기준으로 걸 것(브랜치는 움직이지 않지만 습관으로).

## A등급 — 거의 그대로 쓸 만한 설계 (해당 마일스톤에서 *참고*)

| 자산 | 원본 경로 (`be/` 기준) | 무엇이 좋은가 | 쓰는 곳 |
|---|---|---|---|
| `BaseAiEvaluator` | `clients/client-ai/…/support/BaseAiEvaluator.kt` | 프롬프트 인젝션 방지 접미사 · `<user_content>` 감싸기 · JSON 추출 | M2 |
| `AiCallExecutor` · `AiCallContext` · `AiMetricsRecorder` | `clients/client-ai/…/support/` | 재시도 + 지표를 한 곳에서 | M2 |
| `CacheMetricsAdvisor` | `clients/client-ai/…/support/CacheMetricsAdvisor.kt` | 프롬프트 캐시 적중률 측정 | M4 |
| `BaseAiHttpAdapter` · `AiApiRestClientBuilder` | `clients/client-ai-http/…/http/` | 이중 재시도 금지 · String 기반 역직렬화(406 회피) | M8 |
| AiCallLog (모델·포트·엔티티·어댑터) | `core/core-domain/…/model/AiCallLog.kt` · `storage/db-core/…/AiCallLog*.kt` | 모든 LLM 호출 감사 로그 | M2 · M5(인덱스) |
| `SharedRateLimiter` + `RateLimitCounterAdapter` | `core/core-api/…/support/SharedRateLimiter.kt` · `storage/db-core/…/adapter/RateLimitCounterAdapter.kt` | **락 없는 조건부 UPDATE**(`INSERT … ON CONFLICT DO UPDATE … WHERE`) · 실패 시 환불 · DB 장애 시 메모리 버킷 강등 | **M3 비교 대상 5번째** |
| `RateLimitCounterPostgresTest` | `core/core-api/src/test/…/support/` | 실제 Postgres 로 경합 재현(별도 커넥션이 잠금 보유) | M3 테스트 설계 참고 |
| `ApiControllerAdvice` · `CoreException` · `ErrorType` | `core/core-api/…/controller/` · `…/support/error/` | 에러 봉투 · 에러 코드 체계 | M2 (STEP 09) |
| `MdcFilter` · `ClientIpResolver` | `core/core-api/…/support/` | 요청 추적 ID · 신뢰 프록시 뒤 IP | M2 (STEP 09) |
| `logback-spring.xml` | `support/logging/src/main/resources/` | Loki 라벨 설계(app·platform·level) | M7 |
| 스텁 패턴 | `@ConditionalOnProperty` + `@Primary` 스텁 평가기 | 실키 없이 테스트·로컬 | M2 |

## 교훈 — 코드 말고 결정 (일지 `docs/eks-migration-log.md` 에 실측 있음)

- **OSIV 기본 true** → LLM 대기 내내 DB 커넥션 점유(실측 active 1 → OFF 로 0). M5 의 실물 사례.
- **트랜잭션 안의 AI 호출**(`CodingQuestService` `@Transactional` 이 LLM 을 안음, #308) → M5.
- 이중 재시도(클라이언트 + 서버)가 공급사 호출 수를 제곱으로 늘린다 → M2 · M8.
- 모듈 경계를 Gradle 의존성으로 강제(core-domain 은 Spring import 0) → M1.

## C등급 — 가져오지 않는다

devquest 도메인(퀘스트·RPG·면접 회사 카드) · `App.tsx` 계열 FE · EKS/Fly 인프라 일체 · `.claude/` 가드 스위트.
