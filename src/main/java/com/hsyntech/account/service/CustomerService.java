package com.hsyntech.account.service;

import com.hsyntech.account.dto.CustomerDto;
import com.hsyntech.account.dto.converter.CustomerDtoConverter;
import com.hsyntech.account.exception.CustomerNotFoundException;
import com.hsyntech.account.model.Customer;
import com.hsyntech.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    protected Customer findCustomerById(String id){
        return customerRepository.findById(id)
                .orElseThrow(
                ()->new CustomerNotFoundException("Customer not found by id:"+id));
    }
    public CustomerDto getCustomerById(String id){

        return  customerDtoConverter.convertToCustomerDto(findCustomerById(id));
    }
}
