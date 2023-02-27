package com.oegr.ProgramaLealtadPR.mapper.Impl;

import com.oegr.ProgramaLealtadPR.dto.ProductDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.Product;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.ProductMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductMapperImpl implements ProductMapper {
    private final BusinessRepository businessRepository;

    @Autowired
    public ProductMapperImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public ProductDTO toDTO(Product data) {
        if ( data == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( data.getId() );
        productDTO.setDescription( data.getDescription() );
        productDTO.setPicture( data.getPicture() );
        productDTO.setCost( data.getCost() );
        productDTO.setBusiness_id(data.getBusiness().getId());

        return productDTO;
    }

    @Override
    public Product toEntity(ProductDTO data) {
        if ( data == null ) {
            return null;
        }

        Product product = new Product();
        product.setId(product.getId());
        product.setDescription( data.getDescription() );
        product.setPicture( data.getPicture() );
        product.setCost( data.getCost() );
        Business business = businessRepository
                .findById(data.getBusiness_id())
                .orElseThrow(()-> new BadRequestExceptionMapper("No existe el id del negocio"));
        product.setBusiness(business);
        return product;
    }
}

