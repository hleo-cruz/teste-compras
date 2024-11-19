package com.br.teste.compras.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionManagerResponse {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manageMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        final String message = "Existem parâmetro(s) obritório(s) não informado(s).";
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorDTO error = ErrorDTO.builder().message(message).build();
        final String stackTrace = ExceptionUtils.getStackTrace(exception);

        log.error(message);
        log.error(stackTrace);

        return ResponseEntity.status(httpStatus).body(error);

    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDTO> manageNotFoundException(CustomException exception) {

        final ErrorDTO error = exception.getErrorDTO();
        final HttpStatus httpStatus = exception.getHttpStatus();
        final String message = ExceptionUtils.getMessage(exception);
        final String stackTrace = ExceptionUtils.getStackTrace(exception);

        log.error(message);
        log.error(stackTrace);

        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manageException(Exception exception) {

        final String message = ExceptionUtils.getMessage(exception);
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDTO error = ErrorDTO.builder().message(message).build();
        final String stackTrace = ExceptionUtils.getStackTrace(exception);

        log.error(message);
        log.error(stackTrace);

        return ResponseEntity.status(httpStatus).body(error);
    }
}
