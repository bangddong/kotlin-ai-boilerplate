package io.github.bangddong.ai.credit

data class CreditHistory(
	val id: Long,
	val userId: Long,
	val amount: Long,
	val type: TransactionType,
	val updateMillis: Long,
)

/** 크레딧 트랜잭션 종류 — CHARGE: 충전, USE: 사용 */
enum class TransactionType {
	CHARGE,
	USE,
}
