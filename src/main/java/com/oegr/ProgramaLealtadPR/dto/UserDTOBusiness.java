package com.oegr.ProgramaLealtadPR.dto;

import lombok.Data;

@Data
public class UserDTOBusiness extends UserDTO {
    private long id_business;
    private String name_business;
}
