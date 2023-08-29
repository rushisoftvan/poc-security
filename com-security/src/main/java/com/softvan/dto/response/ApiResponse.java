package com.softvan.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;


public class ApiResponse <T>{

    private T data;
    private Integer stausCode;

    public ApiResponse(T data, Integer statusCode){
        this.data=data;
        this.stausCode=statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStausCode() {
        return stausCode;
    }

    public void setStausCode(Integer stausCode) {
        this.stausCode = stausCode;
    }
}
