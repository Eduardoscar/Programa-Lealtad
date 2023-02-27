package com.oegr.ProgramaLealtadPR.service.Impl;

import com.oegr.ProgramaLealtadPR.dto.TransactionDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.Transaction;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.TransactionMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.TransactionRepository;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import com.oegr.ProgramaLealtadPR.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper mapper;
    private final TransactionRepository repository;
    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Autowired
    public TransactionServiceImpl(TransactionMapper mapper, TransactionRepository repository,
                                  UserRepository userRepository, BusinessRepository businessRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
    }

    @Override
    public List<TransactionDTO> findAll() {
        List<Transaction> transaction;
        if (Objects.equals(userRoleToken(), "ROLE_ADMIN")){
            transaction = repository.findAll();
        } else {
            Business business = businessRepository.findFirstByUserId(userIdToken()).orElseThrow(()-> new NotFoundExceptionMapper("No tienes registrado ningun negocio"));
            transaction = repository.findTransactionsByBusinessId(business.getId());
        }

        return transaction.stream().map(mapper::toDTO).toList();
    }

    @Override
    public TransactionDTO findById(long id) {
        Optional<Transaction> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundExceptionMapper("No existe el Id: " +id);
        }
        return mapper.toDTO(result.get());
    }

    @Override
    public TransactionDTO save(TransactionDTO data) {
        User user = userRepository.findOneByEmail(data.getEmail_user())
                .orElseThrow(()-> new BadRequestExceptionMapper("Email no registrado"));
        if (!Objects.equals(user.getRole(), "USER")){
            throw new BadRequestExceptionMapper("El usuario no es del rol USER");
        }
        if (data.getPoints() < 0){
            int points = repository.sumPointsByUser(user.getId());
            if (points <=  Math.abs(data.getPoints())){
                throw new BadRequestExceptionMapper("El usuario no cuenta con los puntos suficientes");
            }
        }
        Transaction transaction = mapper.toEntity(data);
        return mapper.toDTO(repository.save(transaction));
    }

    @Override
    public List<TransactionDTO> findAllUser() {
        List<Transaction> transaction = repository.findTransactionsByUserId(userIdToken());
        return transaction.stream().map(mapper::toDTO).toList();
    }

    public String userRoleToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        return authorities.get(0).getAuthority();
    }

    public long userIdToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(authentication.getName())
                .orElseThrow(()-> new BadRequestExceptionMapper("Email no registrado"));
        return user.getId();
    }
}
