package com.hsyntech.account.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class AccountDto(//kotlin class ile oluşturduk
        val id:String?,
        val balance: BigDecimal?,
        val creationDate: LocalDateTime?,
        val customer:AccountCustomerDto?,
        val transactions:Set<TransactionDto>?,

)
