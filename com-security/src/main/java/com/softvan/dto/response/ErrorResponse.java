package com.softvan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String error;
    public Integer statuscode;

    public ErrorResponse(String error, Integer statuscode){
        this.error=error;
        this.statuscode=statuscode;

    }
}
