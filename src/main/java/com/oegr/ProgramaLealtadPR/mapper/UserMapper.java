package com.oegr.ProgramaLealtadPR.mapper;

import com.oegr.ProgramaLealtadPR.dto.UserDTO;
import com.oegr.ProgramaLealtadPR.entity.User;


public interface UserMapper {
    UserDTO toDTO(User data);
    User toEntity(UserDTO data);

}
