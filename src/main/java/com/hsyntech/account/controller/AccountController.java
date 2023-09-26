package com.hsyntech.account.controller;

import com.hsyntech.account.dto.AccountDto;
import com.hsyntech.account.dto.request.CreateAccountRequest;
import com.hsyntech.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")//default 8080 portundan ayağa kalkar
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest){
        return  ResponseEntity.ok(accountService.createAccount(createAccountRequest));
    }
}
