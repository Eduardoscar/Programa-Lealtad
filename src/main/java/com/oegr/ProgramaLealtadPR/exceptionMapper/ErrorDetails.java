package com.oegr.ProgramaLealtadPR.exceptionMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {
    private int status;
    private String message;
}
