# [M6 · STEP 17~18] Kafka — 사용량 이벤트를 놓치지 않기

> 원문: Notion 「3-4 대용량 트래픽 & 데이터 처리」
> https://app.notion.com/p/1583c40121388151adc4cafc71b4e1d9
> 🆕 항해 때 **하지 않은** 챕터.

## 이번 챕터 목표

- 애플리케이션 이벤트를 서비스 경계 밖으로 확장한다 — Kafka.
- *"커밋은 됐는데 발행이 안 됐다"* 를 구조로 막는다 — **Transactional Outbox**.
- 결과적 일관성(Eventual Consistency)을 설계 언어로 설명할 수 있다.

## 먼저 알아야 할 것 (원문 1~2장)

Producer · Consumer · Broker · Topic/Partition · 키 기반 파티셔닝(순서 보장) · Consumer Group · Rebalancing · Replication · Kafka vs Redis Pub/Sub vs RabbitMQ.
설치는 **KRaft 모드**(ZooKeeper 없음 — Kafka 4.x 는 KRaft 전용).

## STEP 17 — 기본

- [ ] `docker-compose` 로 Kafka 실행, 애플리케이션 연결
- [ ] Producer · Consumer 연동 + 테스트 (통합 테스트는 Testcontainers Kafka)

## STEP 18 — 심화

- [ ] M5 의 사용량 애플리케이션 이벤트 → **Kafka 발행**으로 전환
- [ ] **Transactional Outbox** — 평가 결과 저장과 outbox 적재를 같은 트랜잭션에
- [ ] 발행 실패 **재처리** (스케줄러) — 일정 시간 넘게 미발행인 outbox 재발행
- [ ] Consumer **멱등 처리** — 같은 메시지 2번 받아도 사용량이 2번 집계되지 않는다

## 통과 기준 (멘토 판정)

| # | 기준 | 판정 방법 |
|:-:|---|---|
| 1 | 브로커를 내린 상태에서 평가 요청 → 성공 + outbox 에 미발행으로 남음 | 실행 (`docker stop`) |
| 2 | 브로커 복구 후 재처리로 발행되고 상태가 갱신됨 | 실행 |
| 3 | 같은 메시지 재전달 시 집계 1회 | 테스트 |
| 4 | 메시지 키 선택(사용자? 평가 ID?)의 근거가 순서 요구에서 나온다 | 문서/PR 본문 |

> **기본 미달이면 심화 Fail.**

## 면접형 질문

1. 파티션 3 · 컨슈머 5 이면 무슨 일이 생기는가? 처리량을 올리려면 무엇을 늘려야 하는가?
2. Outbox 를 써도 **중복 발행**은 일어난다. 언제인가? 그래서 컨슈머가 지켜야 할 것은?
3. 컨슈머 처리가 `max.poll.interval.ms` 를 넘기면 무슨 일이 연쇄적으로 생기는가?
