package com.hsyntech.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Transaction(//builder ve data gibi annotationlar eklemeye gerek kalmaz kotilib data class kullandığımızda!!
        @Id
        @GeneratedValue(generator = "UUID")//benzersiz bir id oluşturmamızı sağlar!
        @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id:String?,
        val transactionType: TransactionType?=TransactionType.INITIAL,
        val amount:BigDecimal?,
        val transactionDate:LocalDateTime?,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_id", nullable = false)
        val account: Account

){
    constructor(amount: BigDecimal,account: Account):this(
            id=null,//id'yi database'e kaydederken kendisi verir!!
            account=account,
            transactionDate = LocalDateTime.now(),
            transactionType = TransactionType.INITIAL,
            amount = amount
    )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Transaction) return false

        if (id != other.id) return false
        if (transactionType != other.transactionType) return false
        if (amount != other.amount) return false
        if (transactionDate != other.transactionDate) return false
        if (account != other.account) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (transactionType?.hashCode() ?: 0)
        result = 31 * result + (amount?.hashCode() ?: 0)
        result = 31 * result + (transactionDate?.hashCode() ?: 0)
        return result
    }
}

enum class TransactionType{
    INITIAL,TRANSFER
}
