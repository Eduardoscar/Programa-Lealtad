package com.oegr.ProgramaLealtadPR.controller;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.service.BusinessService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService service;

    @Autowired
    public BusinessController(BusinessService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BusinessDTO save(@Valid @RequestBody BusinessDTO data) {
        log.info("Endpoint:/business - Method: POST");
        BusinessDTO response = service.save(data);
        log.info("Endpoint:/business - Method: POST - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @GetMapping
    public List<BusinessDTO> findAll() {
        log.info("Endpoint:/business - Method: GET ");
        List<BusinessDTO> response = service.findAll();
        log.info("Endpoint:/business - Method: GET - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @GetMapping("/{id}")
    public BusinessDTO findById(@PathVariable("id") long id){
        log.info("Endpoint:/business/{} - Method: GET ", id);
        BusinessDTO response = service.findById(id);
        log.info("Endpoint:/business/{} - Method: GET - Status: {}", id, HttpStatus.OK.value());
        return response;
    }
    /**
    @PatchMapping("/{id}")
    public BusinessDTO update(@PathVariable("id") long id, @RequestBody BusinessDTO data) {
        log.info("Endpoint:/business/{} - Method: PATCH ", id);
        BusinessDTO response = service.update(id, data);
        log.info("Endpoint:/business/{} - Method: PATCH - Status: {} ", id, HttpStatus.OK.value());
        return response;
    }**/

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        log.info("Endpoint:/business/{} - Method: DELETE ", id);
        service.delete(id);
        log.info("Endpoint:/business/{} - Method: DELETE - Status: {}", id, HttpStatus.NO_CONTENT.value());
    }

}
