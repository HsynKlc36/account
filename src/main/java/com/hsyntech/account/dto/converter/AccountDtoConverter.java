package com.hsyntech.account.dto.converter;

import com.hsyntech.account.dto.AccountDto;
import com.hsyntech.account.model.Account;
import com.hsyntech.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component//annotasyonu Spring bileşeni oluşturan sınıflara eklenir ve Spring tarafından otomatik olarak yönetilirler.
public class AccountDtoConverter  {
    private final CustomerDtoConverter customerDtoConverter;

    private final TransactionDtoConverter transactionDtoConverter;

    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter, TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter = transactionDtoConverter;
    }


    public  AccountDto convert(Account from){
        return new AccountDto(from.getId(),
                from.getBalance(),
                from.getCreationDate(),
                customerDtoConverter.convertToAccountCustomer(from.getCustomer()),//burada AccountDto içerisinde AccountCusterDto olduğu için diğer converter daki bu metodu çağırdım ve account içerisindeki customer'ı da layz ile ihtiyacım olduğu şu anda çağırmış oldum!
                from.getTransaction()
                        .stream()
                        .map(transactionDtoConverter::convert)
                        .collect(Collectors.toSet()));
    }
}
