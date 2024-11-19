package com.br.teste.compras.api.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CompraResponseDTO {

    private Integer quantidade;
    private ProdutoResponseDTO produto;
    private BigDecimal valorTotal;

}
