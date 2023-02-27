package com.oegr.ProgramaLealtadPR.mapper;

import com.oegr.ProgramaLealtadPR.dto.ProductDTO;
import com.oegr.ProgramaLealtadPR.entity.Product;

public interface ProductMapper {
    ProductDTO toDTO(Product data);
    Product toEntity(ProductDTO data);
}
