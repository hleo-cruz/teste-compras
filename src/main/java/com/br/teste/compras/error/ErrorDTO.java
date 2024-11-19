package com.br.teste.compras.error;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {
    private String message;
}
