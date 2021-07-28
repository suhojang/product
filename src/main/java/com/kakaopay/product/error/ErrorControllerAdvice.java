package com.kakaopay.product.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse eResponse = ErrorResponse.of(ErrorCode.TEMPORARY_SERVER_ERROR);
        eResponse.setErrMsg(e.getMessage());
        return new ResponseEntity<>(eResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SoldOutException.class)
    @ResponseStatus(value = HttpStatus.OK)
    protected ResponseEntity<ErrorResponse> handleSoldOutException(SoldOutException e) {
        ErrorResponse eResponse = ErrorResponse.of(ErrorCode.SOLD_OUT_ERROR);
        return new ResponseEntity<>(eResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = AlreadyRegisterException.class)
    @ResponseStatus(value = HttpStatus.OK)
    protected ResponseEntity<ErrorResponse> handleAlReadyRegisterException(AlreadyRegisterException e) {
        ErrorResponse eResponse = ErrorResponse.of(ErrorCode.ALREADY_REGISTER_ERROR);
        return new ResponseEntity<>(eResponse, HttpStatus.OK);
    }
}
