package com.oegr.ProgramaLealtadPR.mapper.Impl;

import com.oegr.ProgramaLealtadPR.dto.TransactionDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.Transaction;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.TransactionMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Autowired
    public TransactionMapperImpl(UserRepository userRepository, BusinessRepository businessRepository) {

        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
    }
    @Override
    public TransactionDTO toDTO(Transaction data) {
        if ( data == null ) {
            return null;
        }
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(data.getId());
        transactionDTO.setPoints(data.getPoints());
        transactionDTO.setCreationDate(data.getCreationDate());
        transactionDTO.setEmail_user(data.getUser().getEmail());
        transactionDTO.setId_business(data.getBusiness().getId());
        transactionDTO.setName_business(data.getBusiness().getName());
        return transactionDTO;
    }

    @Override
    public Transaction toEntity(TransactionDTO data) {
        if ( data == null ) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(data.getId());
        transaction.setPoints(data.getPoints());
        transaction.setCreationDate(data.getCreationDate());
        Optional<User> userOptional = userRepository.findOneByEmail(data.getEmail_user());
        User user = userOptional.orElseThrow(()-> new BadRequestExceptionMapper("Email no registrado"));
        transaction.setUser(user);
        Optional<Business> businessOptional = businessRepository.findById(data.getId_business());
        Business business = businessOptional.orElseThrow(()-> new BadRequestExceptionMapper("No existe el id del negocio"));
        transaction.setBusiness(business);
        return transaction;
    }
}
