# 진행 현황 — 멘토의 기억

> 새 세션의 멘토는 **이 파일부터** 읽는다. 판정·지적·다음 발제를 여기에만 남긴다.

## 현재

| 항목 | 값 |
|---|---|
| 현재 마일스톤 | **M0 준비** — 레포 초기화 전 |
| 다음 할 일 | ① ~~GitHub 레포 생성~~ ✅ 2026-11-06 공개(`bangddong/kotlin-ai-boilerplate`, 빈 레포 · fork PR 워크플로 승인 = `all_external_contributors`) ② `git init` · 레포 `.gitignore`(Kotlin/Gradle + CLAUDE.md 「IaC」 절 패턴) · 이 문서들 첫 커밋 ③ 골격 커밋(멘토 작성 — 사전 고지 완료) ④ M0 발제 Issue 등록(`missions/M0-tdd.md`) |
| Madi | `.mcp.json`(gitignore) 등록 2026-11-09 — stdio 핸드셰이크만 확인(`madi 3.4.5`). **세션 첫 작업으로 검증**: `/mcp` 에 madi 보임 → `madi_context`·`madi_recall` 호출 → 테스트 checkpoint 저장 → 재시작 후 읽기. Vault private Git 미연결(백업이 Mac 한 곳뿐). 2026-11-09 실측: workstream `personal-assistant` 1개(세션 1건)뿐, project 없음 → **이 레포용 project + workstream 등록 필요**(USAGE.md A-3, checkpoint 전 선행). Madi 담당 확인(2026-11-12): project = `kotlin-ai-boilerplate`, workstream = `kotlin-ai-boilerplate-mentoring`(workstream id 는 Vault 전역 유일 — 레포 접두 필수). id 규칙 = 영숫자로 시작, 이후 영숫자·`.`·`_`·`-`. `stale-local-lock` = 잠금 파일 mtime > 1h 일 뿐 사용 중 아님 — 🔴 **잠금 파일 삭제·`doctor --repair` 금지**(여러 프로세스의 동기화 기준일 수 있음). degraded 는 무시하고 진행. **2026-11-12 등록·검증 완료**: project·workstream 등록(하이픈 허용 확인) → stdio MCP 로 `madi_context`·`madi_recall`·`madi_checkpoint` 호출 성공 → 첫 checkpoint `setup-20261112@000001` → 새 프로세스에서 `context` 가 그 head 를 읽음(resolved). 실행기 = Madi 담당이 깐 `~/Library/Application Support/Madi/runtime/madi-python`(release 고정 런처 — mneme-mcp `.venv` 아님). 런처로 바꾼 뒤 `madi_recall "Gradle"` 이 checkpoint 를 찾음(구 실행기에선 0건). Claude Code 세션 안에서의 `/mcp` 연결은 아직 미확인(이 레포에서 세션을 열 때 첫 확인). 다음 checkpoint 는 `expected_parent` = 이 revision, registry generation 은 다시 읽어서 |
| 열린 지적 | 없음 |

## 판정 기록

| M | STEP | PR | 기본 | 심화 | 퀴즈 | 회고 | 날짜 |
|:-:|---|---|:-:|:-:|:-:|:-:|---|
| 0 | 01·02 | — | — | — | — | — | — |

## 배경 (2026-11-06, 이전 세션에서 인계)

- 전신 프로젝트 **devquest**(`bangddong/switch-job-quest`, archived)를 종료하고 이 레포로 왔다.
  재사용할 자산 목록 = `reuse-from-devquest.md`.
- 항해 플러스 BE 6기 수강 당시 레포 `bangddong/E-commerce`(Java·Boot 3.3·MariaDB·Redisson) — 브랜치가 `step_05`~`step_16`.
  **STEP 17~20 은 안 했다**(브랜치 이름 기준 추정 🟡).
- 항해 STEP 11 보고서(https://dhbang.tistory.com/116)가 스스로 남긴 숙제 3개:
  ① 락 방식 **수치 비교 없음**(5스레드로 재고 0 만 확인) ② 테스트가 비즈니스 로직을 너무 많이 안다
  ③ 트랜잭션 전파 속성 이해 부족 → M0·M3·M5 에서 회수한다.
- 참고(Notion 「인프런 이직 멘토링」의 멘토 피드백): *"능동적으로 찾아서 개선한
  구체 사례"*, *"면접 질문을 유도할 2~3개 답변거리"*, *"기존 아키텍처 분석을 통한 공통 아키텍처 설계"*.
