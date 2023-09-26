package com.hsyntech.account.dto.converter;

import com.hsyntech.account.dto.TransactionDto;
import com.hsyntech.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component// bunu yazdığımız için servislere inject edebiliyoruz!!!
public class TransactionDtoConverter {
    public TransactionDto convert(Transaction from){
        return new TransactionDto(from.getId(),
                from.getTransactionType(),
                from.getAmount(),
                from.getTransactionDate());
    }
}
