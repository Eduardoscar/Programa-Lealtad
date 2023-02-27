package com.oegr.ProgramaLealtadPR.service;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;

import java.util.List;

public interface BusinessService {
    List<BusinessDTO> findAll();
    BusinessDTO findById(long id);
    BusinessDTO save(BusinessDTO data);
    BusinessDTO update(long id, BusinessDTO data);
    void delete(long id);

    Business findFirstByUserId(long id);

}
