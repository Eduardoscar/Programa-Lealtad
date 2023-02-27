package com.oegr.ProgramaLealtadPR.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private long id;

    private int points;

    private Date creationDate;

    private String email_user;

    private long id_business;

    private String name_business;

}
