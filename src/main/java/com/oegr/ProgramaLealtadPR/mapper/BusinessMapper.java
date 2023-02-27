package com.oegr.ProgramaLealtadPR.mapper;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;


public interface BusinessMapper {
    BusinessDTO toDTO(Business data);
    Business toEntity(BusinessDTO data);
}
