package com.oegr.ProgramaLealtadPR.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
