package com.oegr.ProgramaLealtadPR.controller;

import com.oegr.ProgramaLealtadPR.dto.ProductDTO;
import com.oegr.ProgramaLealtadPR.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @PostMapping
    public ProductDTO save(@Valid @RequestBody ProductDTO data) {
        log.info("Endpoint:/business - Method: POST");
        ProductDTO response = service.save(data);
        log.info("Endpoint:/business - Method: POST - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS') OR hasRole('ROLE_USER')")
    @GetMapping("/business/{id}")
    public List<ProductDTO> findAllByBusiness(@PathVariable("id") long id) {
        log.info("Endpoint:/users - Method: GET ");
        List<ProductDTO> response = service.findAllByBusiness(id);
        log.info("Endpoint:/users - Method: GET - Status: {}", HttpStatus.OK.value());
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable("id") long id){
        log.info("Endpoint:/users/{} - Method: GET ", id);
        ProductDTO response = service.findById(id);
        log.info("Endpoint:/users/{} - Method: GET - Status: {}", id, HttpStatus.OK.value());
        return response;
    }
    /**
     @PatchMapping("/{id}")
     public ProductDTO update(@PathVariable("id") long id, @RequestBody ProductDTO data) {
     log.info("Endpoint:/users/{} - Method: PATCH ", id);
     ProductDTO response = service.update(id, data);
     log.info("Endpoint:/users/{} - Method: PATCH - Status: {} ", id, HttpStatus.OK.value());
     return response;
     }**/

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_BUSINESS')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        log.info("Endpoint:/users/{} - Method: DELETE ", id);
        service.delete(id);
        log.info("Endpoint:/users/{} - Method: DELETE - Status: {}", id, HttpStatus.NO_CONTENT.value());
    }

}
