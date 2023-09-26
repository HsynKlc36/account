package com.hsyntech.account.dto.converter;

import com.hsyntech.account.dto.AccountCustomerDto;
import com.hsyntech.account.dto.CustomerDto;
import com.hsyntech.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {//bu classlar mapper yapmak için kullanılır yani (entity ve dto) arasında

    private final CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter converter) {
        this.converter = converter;
    }

    public AccountCustomerDto convertToAccountCustomer(Customer from){
        if (from ==null){
           return new AccountCustomerDto("","","");
        }
        return  new AccountCustomerDto(from.getId(),from.getName(),from.getSurname());
    }
    public CustomerDto convertToCustomerDto(Customer from){
        return  new CustomerDto(
                from.getId(),
                from.getName(),
                from.getSurname(),
                from.getAccounts().stream().map(converter::convert).collect(Collectors.toSet())
        );
    }

}
