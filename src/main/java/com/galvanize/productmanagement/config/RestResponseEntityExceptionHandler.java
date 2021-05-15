package com.galvanize.productmanagement.config;

import com.galvanize.productmanagement.dto.RestError;
import com.galvanize.productmanagement.service.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BusinessException.NotFoundException.class})
    protected ResponseEntity<Object> translate(BusinessException bizEx, WebRequest request) {
        return handleExceptionInternal(bizEx, new RestError(bizEx.getClass().getName(), HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


}
