package io.github.bangddong.ai.infra.memory

import io.github.bangddong.ai.credit.UserCredit
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom

/**
 * 🔴 수정 금지 — 미션이 제공하는 메모리 저장소. 공개 API 만 사용해 기능을 구현한다.
 *
 * 호출마다 무작위 지연이 있다(경합 재현용).
 * 자료구조 자체는 스레드 안전하다. 안전하지 않은 것은 **조회 → 계산 → 저장 사이**다.
 */
@Component
class CreditTable {

	private val table = ConcurrentHashMap<Long, UserCredit>()

	fun selectById(id: Long): UserCredit {
		throttle(200)
		return table[id] ?: UserCredit.empty(id)
	}

	fun insertOrUpdate(id: Long, amount: Long): UserCredit {
		throttle(300)
		val userCredit = UserCredit(id, amount, System.currentTimeMillis())
		table[id] = userCredit
		return userCredit
	}

	private fun throttle(maxMillis: Long) {
		Thread.sleep(ThreadLocalRandom.current().nextLong(maxMillis))
	}
}
