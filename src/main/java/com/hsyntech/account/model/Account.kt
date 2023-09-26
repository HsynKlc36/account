package com.hsyntech.account.model

import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity //veritabanında karşılığı olan bir tablo oluşturmak için
data class Account(//kotlin ile data class oluşturduk sebebi ise getter setter hashcode tostring() gibi metotlarını kendisi oluşturur
        @Id
        @GeneratedValue(generator = "UUID")//benzersiz bir id oluşturmamızı sağlar!
        @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")//id yi hibernate üretecek uuid olarak string sekilde
        val id:String?="",//val veri güvenliğinde önemlidir ve verinin oluşturulduktan sonra değiştirilmesini engeller yani burada Account nesnesi oluşturulduktan sonra id'yi set edemezsin!
        val balance:BigDecimal?=BigDecimal.ZERO,
        val creationDate:LocalDateTime,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])//1-n ilişki fakat yani bir accountun bir customer'ı olurken bir customer'ın birden çok accountu  olabilir!!
        @JoinColumn(name="customer_id", nullable = false)
        val customer:Customer?,

        @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,cascade = [CascadeType.ALL])//buradaki account Transaction içerisindeki account nesnesidir çünkü ora ile ilişkilendirme yapacaktır!//cascade ise ilişkili tablolar arasında bir değişiklik olunca diğer tabloya da ilişkili tablodaki değişikliği yansıtmak için kullanılır.yani account nesnesi oluştururken içerisinde transaction olduğu için ilişkili tablodaki nesneyi de oluşturup öyle oradaki nesneyi de getirecektir!
        val transaction:Set<Transaction> = HashSet()

){

    constructor(customer:Customer,balance: BigDecimal,creationDate: LocalDateTime):this(
            "",
            customer = customer,
            balance=balance,
            creationDate=creationDate
    )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (id != other.id) return false
        if (balance != other.balance) return false
        if (creationDate != other.creationDate) return false
        if (customer != other.customer) return false
        if (transaction != other.transaction) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + (customer?.hashCode() ?: 0)
        return result
    }
}
