package com.oegr.ProgramaLealtadPR.service;

import com.oegr.ProgramaLealtadPR.dto.UserDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
    UserDTO findById(long id);
    UserDTO save(UserDTO data);
    UserDTO update(long id, UserDTO data);
    void delete(long id);

}
