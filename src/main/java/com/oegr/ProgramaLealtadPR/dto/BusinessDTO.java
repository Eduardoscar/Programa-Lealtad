package com.oegr.ProgramaLealtadPR.dto;

import com.oegr.ProgramaLealtadPR.entity.User;
import lombok.Data;

@Data
public class BusinessDTO {

    private long id;

    private String name;

    private String address;

    private String telephone;

    private String picture;

    private String facebook;

    private String instagram;

    private String whatsapp;

    private String email_user;

    private String name_user;
}
