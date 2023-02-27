package com.oegr.ProgramaLealtadPR.mapper.Impl;

import com.oegr.ProgramaLealtadPR.dto.UserDTO;
import com.oegr.ProgramaLealtadPR.dto.UserDTOBusiness;
import com.oegr.ProgramaLealtadPR.dto.UserDTOUser;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.mapper.UserMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.TransactionRepository;
import com.oegr.ProgramaLealtadPR.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {
    private final TransactionRepository transactionRepository;
    private final BusinessService businessService;
    @Autowired
    public UserMapperImpl(TransactionRepository transactionRepository, BusinessService businessService) {
        this.businessService = businessService;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public UserDTO toDTO(User data) {
        if ( data == null ) {
            return null;
        }
        UserDTO userDTO;
        if (Objects.equals(data.getRole(), "BUSINESS")){
            userDTO = new UserDTOBusiness();
        }else if (Objects.equals(data.getRole(), "USER")){
            userDTO = new UserDTOUser();
        }else {
            userDTO = new UserDTO();
        }

        userDTO.setId( data.getId() );
        userDTO.setName( data.getName() );
        userDTO.setPaternalSurname( data.getPaternalSurname() );
        userDTO.setMaternalSurname( data.getMaternalSurname() );
        userDTO.setEmail( data.getEmail() );
        userDTO.setBirthday( data.getBirthday() );
        userDTO.setRole( data.getRole() );
        userDTO.setPassword( "*********");
        userDTO.setCreationDate( data.getCreationDate() );
        userDTO.setUpdateDate( data.getUpdateDate() );

        if (userDTO instanceof UserDTOBusiness userDTOBusiness) {
            Business business = businessService.findFirstByUserId(data.getId());
            userDTOBusiness.setName_business(business.getName());
            userDTOBusiness.setId_business(business.getId());
            return userDTOBusiness;
        }
        if (userDTO instanceof UserDTOUser userDTOUser) {
            userDTOUser.setPoints(transactionRepository.sumPointsByUser(data.getId()));
            return userDTOUser;
        }
        return userDTO;
    }

    @Override
    public User toEntity(UserDTO data) {
        if ( data == null ) {
            return null;
        }

        User user = new User();

        user.setId( data.getId() );
        user.setName( data.getName() );
        user.setPaternalSurname( data.getPaternalSurname() );
        user.setMaternalSurname( data.getMaternalSurname() );
        user.setBirthday( data.getBirthday() );
        user.setEmail( data.getEmail() );
        user.setPassword( data.getPassword() );
        user.setRole( data.getRole() );
        user.setCreationDate( data.getCreationDate() );
        user.setUpdateDate( data.getUpdateDate() );

        return user;
    }
}

