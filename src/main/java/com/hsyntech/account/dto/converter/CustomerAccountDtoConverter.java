package com.hsyntech.account.dto.converter;

import com.hsyntech.account.dto.CustomerAccountDto;
import com.hsyntech.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {
    private final TransactionDtoConverter converter;

    public CustomerAccountDtoConverter(TransactionDtoConverter converter) {
        this.converter = converter;
    }

    public CustomerAccountDto convert(Account from){
        return  new CustomerAccountDto(
                Objects.requireNonNull(from.getId()),// metodu, belirtilen değeri kontrol eder ve eğer değer null ise bir NullPointerException fırlatır.
                from.getBalance(),
                from.getTransaction().stream().map(converter::convert).collect(Collectors.toSet()),
                from.getCreationDate()
                );
    }

}
