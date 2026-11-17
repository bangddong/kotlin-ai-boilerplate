package io.github.bangddong.credit.infra.memory

import io.github.bangddong.credit.credit.CreditHistory
import io.github.bangddong.credit.credit.TransactionType
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicLong

/**
 * 🔴 수정 금지 — 미션이 제공하는 메모리 저장소. 공개 API 만 사용해 기능을 구현한다.
 *
 * 호출마다 무작위 지연이 있다(경합 재현용).
 */
@Component
class CreditHistoryTable {

	private val table = CopyOnWriteArrayList<CreditHistory>()
	private val cursor = AtomicLong(1)

	fun insert(userId: Long, amount: Long, type: TransactionType, updateMillis: Long): CreditHistory {
		throttle(300)
		val history = CreditHistory(cursor.getAndIncrement(), userId, amount, type, updateMillis)
		table.add(history)
		return history
	}

	fun selectAllByUserId(userId: Long): List<CreditHistory> =
		table.filter { it.userId == userId }

	private fun throttle(maxMillis: Long) {
		Thread.sleep(ThreadLocalRandom.current().nextLong(maxMillis))
	}
}
