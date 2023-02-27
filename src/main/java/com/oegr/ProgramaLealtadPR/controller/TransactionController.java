package com.oegr.ProgramaLealtadPR.controller;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.dto.TransactionDTO;
import com.oegr.ProgramaLealtadPR.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @PostMapping
    public TransactionDTO save(@Valid @RequestBody TransactionDTO data) {
        log.info("Endpoint:/transactions - Method: POST");
        TransactionDTO response = service.save(data);
        log.info("Endpoint:/transactions - Method: POST - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @GetMapping
    public List<TransactionDTO> findAll() {
        log.info("Endpoint:/transactions - Method: GET ");
        List<TransactionDTO> response = service.findAll();
        log.info("Endpoint:/transactions - Method: GET - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @GetMapping("/{id}")
    public TransactionDTO findById(@PathVariable("id") long id){
        log.info("Endpoint:/transactions/{} - Method: GET ", id);
        TransactionDTO response = service.findById(id);
        log.info("Endpoint:/transactions/{} - Method: GET - Status: {}", id, HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public List<TransactionDTO> findAllUser() {
        log.info("Endpoint:/transactions/user - Method: GET ");
        List<TransactionDTO> response = service.findAllUser();
        log.info("Endpoint:/transactions/user - Method: GET - Status: {}", HttpStatus.OK.value());
        return response;
    }

}
