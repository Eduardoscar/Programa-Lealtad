package com.oegr.ProgramaLealtadPR.service.Impl;

import com.oegr.ProgramaLealtadPR.dto.ProductDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.Product;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.ProductMapper;
import com.oegr.ProgramaLealtadPR.mapper.TransactionMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.ProductRepository;
import com.oegr.ProgramaLealtadPR.repository.TransactionRepository;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import com.oegr.ProgramaLealtadPR.service.BusinessService;
import com.oegr.ProgramaLealtadPR.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper;
    private final ProductRepository repository;
    private final BusinessServiceImpl businessService;

    @Autowired
    public ProductServiceImpl(ProductMapper mapper, ProductRepository repository,
                              BusinessServiceImpl businessService) {
        this.mapper = mapper;
        this.repository = repository;
        this.businessService = businessService;
    }

    @Override
    public List<ProductDTO> findAllByBusiness(long id) {
        businessService.isExistId(id);
        List<Product> products = repository.findAllByBusiness(id);
        return products.stream().map(mapper::toDTO).toList();
    }

    @Override
    public ProductDTO findById(long id) {
        Product product = isExistId(id);
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO save(ProductDTO data) {
        businessService.isExistId(data.getBusiness_id());
        Product product = mapper.toEntity(data);
        return mapper.toDTO(repository.save(product));
    }

    @Override
    public ProductDTO update(long id, ProductDTO data) {
        return null;
    }

    @Override
    public void delete(long id) {
        isExistId(id);
        repository.deleteById(id);
    }

    public Product isExistId(long id){
        Optional<Product> result = repository.findById(id);
        return result.orElseThrow(()->new NotFoundExceptionMapper("No existe el Id: " +id));
    }
}
