package com.oegr.ProgramaLealtadPR.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    
    private long id;
    private String name;

    private String paternalSurname;

    private String maternalSurname;

    private String email;

    private Date birthday;

    private String role;

    private String password;
    @JsonIgnore
    private Date creationDate;
    @JsonIgnore
    private Date updateDate;

}

