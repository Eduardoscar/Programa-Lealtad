package com.oegr.ProgramaLealtadPR;

import com.oegr.ProgramaLealtadPR.controller.UserController;
import com.oegr.ProgramaLealtadPR.dto.UserDTO;
import com.oegr.ProgramaLealtadPR.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Tests {
    @Mock
    UserServiceImpl userServiceMock;
    @InjectMocks
    UserController userController;

    @Test
    @DisplayName("Obtener todos los usuarios")
    void findAll() {
        List<UserDTO> users = new ArrayList<>();
        given(userServiceMock.findAll()).willReturn(users);
        assertEquals(users,userController.findAll());
    }

    @Test
    @DisplayName("Obtener un usuario por su id")
    void findId() throws Exception {
        UserDTO user = new UserDTO();
        given(userServiceMock.findById(anyLong())).willReturn(user);
        assertEquals(user, userController.findById(anyLong()));
    }

    @Test
    @DisplayName("Respuesta de crear un usuario")
    void save () {
        UserDTO user = new UserDTO();
        given(userServiceMock.save(any(UserDTO.class))).willReturn(user);
        assertEquals(user, userController.save(user));
    }



    @Test
    @DisplayName("Exception Creando Usuario")
    void saveException() {
        doThrow(new RuntimeException()).when(userServiceMock).save(any(UserDTO.class));
        assertThrows(RuntimeException.class, () ->{userController.save(any(UserDTO.class));});
    }

    @Test
    @DisplayName("Actualizando Usuario")
    void patch() throws Exception {
        UserDTO userDTO = new UserDTO();
        userController.update(1L,userDTO);
        verify(userServiceMock, times(1)).update(1L,userDTO);
    }

    @Test
    @DisplayName("Exception Actualizando Usuario")
    void updateException() {
        doThrow(new RuntimeException()).when(userServiceMock).update(anyLong(),any(UserDTO.class));
        assertThrows(RuntimeException.class, () ->{userController.update(anyLong(),any(UserDTO.class));});
    }


    @Test
    @DisplayName("Eliminando a un usuario")
    void delete () throws Exception {
        userController.delete(1L);
        verify(userServiceMock,times(1)).delete(1);
    }

    @Test
    @DisplayName("Exception Eliminando Usuario")
    void deleteException() {
        doThrow(new RuntimeException()).when(userServiceMock).delete(anyLong());
        assertThrows(RuntimeException.class, () ->{userController.delete(anyLong());});
    }
}
