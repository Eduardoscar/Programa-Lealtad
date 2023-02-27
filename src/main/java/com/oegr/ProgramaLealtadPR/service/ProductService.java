package com.oegr.ProgramaLealtadPR.service;

import com.oegr.ProgramaLealtadPR.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllByBusiness(long id);
    ProductDTO findById(long id);
    ProductDTO save(ProductDTO data);
    ProductDTO update(long id, ProductDTO data);
    void delete(long id);
}
