package com.hsyntech.account;

import com.hsyntech.account.model.Customer;
import com.hsyntech.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {
	private final CustomerRepository customerRepository;

	public AccountApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
      Customer customer=customerRepository.save(new Customer("","hüseyin","kilic",new HashSet<>()));
		System.out.println(customer);
	}



}
