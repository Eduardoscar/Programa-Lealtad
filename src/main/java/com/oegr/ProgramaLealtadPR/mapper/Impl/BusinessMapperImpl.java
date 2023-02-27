package com.oegr.ProgramaLealtadPR.mapper.Impl;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.BusinessMapper;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BusinessMapperImpl implements BusinessMapper {
    private final UserRepository userRepository;
    @Autowired
    public BusinessMapperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public BusinessDTO toDTO(Business data) {
        if ( data == null ) {
            return null;
        }

        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setId(data.getId());
        businessDTO.setName(data.getName());
        businessDTO.setAddress(data.getAddress());
        businessDTO.setTelephone(data.getTelephone());
        businessDTO.setPicture(data.getPicture());
        businessDTO.setFacebook(data.getFacebook());
        businessDTO.setInstagram(data.getInstagram());
        businessDTO.setWhatsapp(data.getWhatsapp());
        businessDTO.setEmail_user(data.getUser().getEmail());
        businessDTO.setName_user(data.getUser().getName());
        return businessDTO;
    }

    @Override
    public Business toEntity(BusinessDTO data) {
        if ( data == null ) {
            return null;
        }
        Business business = new Business();
        business.setId(data.getId());
        business.setName(data.getName());
        business.setAddress(data.getAddress());
        business.setTelephone(data.getTelephone());
        business.setPicture(data.getPicture());
        business.setFacebook(data.getFacebook());
        business.setInstagram(data.getInstagram());
        business.setWhatsapp(data.getWhatsapp());
        Optional<User> userOptional = userRepository.findOneByEmail(data.getEmail_user());
        User user = userOptional.orElseThrow(()-> new BadRequestExceptionMapper("Email no registrado"));
        business.setUser(user);
        return business;
    }
}

