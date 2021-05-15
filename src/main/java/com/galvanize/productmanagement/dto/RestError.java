package com.galvanize.productmanagement.dto;
public class RestError {
    private String message;
    private Integer code;
    private String status;

    public RestError(String message, Integer code, String status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public RestError() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}