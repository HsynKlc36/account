package com.hsyntech.account.service;

import com.hsyntech.account.dto.AccountDto;
import com.hsyntech.account.dto.converter.AccountDtoConverter;
import com.hsyntech.account.dto.request.CreateAccountRequest;
import com.hsyntech.account.model.Account;
import com.hsyntech.account.model.Customer;
import com.hsyntech.account.model.Transaction;
import com.hsyntech.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private  final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;


    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;

    }


    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
        Customer customer=customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account=new Account(customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());
        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO)>0){
            //Transaction transaction=transactionService.initiateMoney(account,createAccountRequest.getInitialCredit());//önce bir para transferi oluşturduk ardından bunu accounta ekledik çünkü hesabın transactionlarını account içerisinde set<> ile tutuyoruz!!
            Transaction transaction= new Transaction(createAccountRequest.getInitialCredit(),account);
            account.getTransaction().add(transaction);
        }
        Account newAccount=accountRepository.save(account);
         return accountDtoConverter.convert(newAccount);
    }

}
