package com.hsyntech.account.model

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
data class Customer(
        @Id
        @GeneratedValue(generator = "UUID")//benzersiz bir id oluşturmamızı sağlar!
        @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id:String?,
        val name:String?,
        val surname:String?,

        @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
        val accounts: Set<Account>?

){

    constructor(name: String,surname: String):this("",name,surname,HashSet())
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Customer) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (accounts != other.accounts) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (surname?.hashCode() ?: 0)
        return result
    }
}
//---------------------------------- (n-n)  ilişki !!----------------------------
//entitylerden birinde bu yazıldı mı
/*
 class Person
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
        name = "person_phone",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "phone_id")
)
private Set<Phone> phones = new HashSet<>();
*/
//diğer tarafta sadece aşağıdaki gibi belirtilmesi yeterlidir!
/*
class Phone
@ManyToMany(mappedBy = "phones")
private Set<Person> owners = new HashSet<>();*/

//------------------------------- (1-1) ilişki!------------------------------
/*@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")//tek tarafta ilişkiye ait id vermek yeterlidir
    private Address address;

    // Getter ve Setter metotları
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;

    @OneToOne(mappedBy = "address")//buradaki isimlendirmeler önemlidir diğer tablodaki bu nesneyi temsil eden isim kullanılır
    private Person person;

    // Getter ve Setter metotları
}*/
//---------------------------------(1-n) ilişki------------------------
/*
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    // Getter ve Setter metotları
}

@Entity
public class Address {//tek olan tarafta id verilir
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    // Getter ve Setter metotları
}

 */
