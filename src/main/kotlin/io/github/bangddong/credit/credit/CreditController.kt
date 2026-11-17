package io.github.bangddong.credit.credit

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 엔드포인트 껍데기 — 본문을 구현한다(M0 미션).
 */
@RestController
@RequestMapping("/credits")
class CreditController {

	/** 특정 사용자의 크레딧 잔액을 조회한다. */
	@GetMapping("/{id}")
	fun credit(@PathVariable id: Long): UserCredit = TODO("M0")

	/** 특정 사용자의 크레딧 충전/사용 내역을 조회한다. */
	@GetMapping("/{id}/histories")
	fun histories(@PathVariable id: Long): List<CreditHistory> = TODO("M0")

	/** 특정 사용자의 크레딧을 충전한다. */
	@PatchMapping("/{id}/charge")
	fun charge(@PathVariable id: Long, @RequestBody amount: Long): UserCredit = TODO("M0")

	/** 특정 사용자의 크레딧을 사용한다. */
	@PatchMapping("/{id}/use")
	fun use(@PathVariable id: Long, @RequestBody amount: Long): UserCredit = TODO("M0")
}
