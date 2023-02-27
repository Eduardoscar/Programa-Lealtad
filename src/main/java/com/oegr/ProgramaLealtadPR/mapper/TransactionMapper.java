package com.oegr.ProgramaLealtadPR.mapper;

import com.oegr.ProgramaLealtadPR.dto.TransactionDTO;
import com.oegr.ProgramaLealtadPR.entity.Transaction;

public interface TransactionMapper {
    TransactionDTO toDTO(Transaction data);
    Transaction toEntity(TransactionDTO data);
}
