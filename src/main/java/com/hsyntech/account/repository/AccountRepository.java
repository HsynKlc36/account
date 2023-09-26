package com.hsyntech.account.repository;

import com.hsyntech.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account,String> {
}
