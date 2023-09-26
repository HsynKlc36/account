package com.hsyntech.account.dto.request

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class CreateAccountRequest(
        //kotlinde @field ekleriz çünkü kotlin ile bu class'ları oluşturduk
        @field:NotBlank(message = "customerId is required")// stringin boş olmaması için kullanılır default mesajı olduğu gibi bizde mesaj yazabiliriz
      val customerId:String,
        @field:Min(0)//boş olabilir ve minimum 0 olabir -(eksi) değer alamaz!
        val initialCredit:BigDecimal
)
//buraya eklediğimiz anotation'lar sayesinde gönderilen requestlerin valid olup olmadığını kontrol edebilmiş olup sonrasında da hata varsa globalExceptionHandler sayesinde bu durum kontrol edilip hatalı durumda controller'da doğrudan kesip hata için handler sayesinde bir response dönecektir!!Ayrıca bu validasyonların kontrol edilebilmesi için controller da @valid 'i metotlar içerisinde belirtmem gerekirki kontrol sağlansın!
