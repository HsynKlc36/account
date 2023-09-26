package com.hsyntech.account.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CustomerAccountDto(
  val id:String,
  val balance:BigDecimal?= BigDecimal.ZERO,
        val transactionDto: Set<TransactionDto>,
        val creationDate:LocalDateTime
)