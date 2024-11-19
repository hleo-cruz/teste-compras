package com.br.teste.compras.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {

    private static String MESSAGE = "Ocorreu um erro não mapeado";

    private final ErrorDTO errorDTO;
    private final HttpStatus httpStatus;

    public CustomException(ErrorDTO errorDTO, HttpStatus httpStatus) {
        this.errorDTO = errorDTO;
        this.httpStatus = httpStatus;
    }

    public CustomException(Throwable cause, ErrorDTO errorDTO, HttpStatus httpStatus) {
        super(cause);
        this.errorDTO = errorDTO;
        this.httpStatus = httpStatus;
    }

    public CustomException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.errorDTO = ErrorDTO.builder().message(MESSAGE).build();
        this.httpStatus = httpStatus;
    }

}
