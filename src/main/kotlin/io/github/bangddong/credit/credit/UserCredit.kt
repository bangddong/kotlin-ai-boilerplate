package io.github.bangddong.credit.credit

data class UserCredit(
	val id: Long,
	val credit: Long,
	val updateMillis: Long,
) {
	companion object {
		fun empty(id: Long): UserCredit = UserCredit(id, 0, System.currentTimeMillis())
	}
}
