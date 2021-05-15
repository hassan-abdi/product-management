package com.galvanize.productmanagement.service.exception;

public class BusinessException extends RuntimeException {

    public static class NotFoundException extends BusinessException{

    }
}
