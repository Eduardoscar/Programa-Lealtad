package com.oegr.ProgramaLealtadPR.service;

import com.oegr.ProgramaLealtadPR.dto.TransactionDTO;
import java.util.List;

public interface TransactionService {
    List<TransactionDTO> findAll();
    TransactionDTO findById(long id);
    TransactionDTO save(TransactionDTO data);

    List<TransactionDTO> findAllUser();
}
