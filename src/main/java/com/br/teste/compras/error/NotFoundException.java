package com.br.teste.compras.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {

    private static final String MESSAGE = "Não foi encontrado registro(s) com o(s) parâmetro(s) informado.";

    public NotFoundException() {
        super(ErrorDTO.builder()
                .message(MESSAGE)
                .build(), HttpStatus.NOT_FOUND);
    }

    public NotFoundException(Throwable cause) {
        super(cause, ErrorDTO.builder()
                .message(MESSAGE)
                .build(), HttpStatus.NOT_FOUND);
    }
}
