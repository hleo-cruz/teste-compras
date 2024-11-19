package com.br.teste.compras.api.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Builder
public class CompraClienteResponseDTO {

    private String clienteNome;
    private String clienteCpf;
    private List<CompraResponseDTO> compras;

    public BigDecimal getValorTotal() {
        return compras.stream().filter(Objects::nonNull).map(CompraResponseDTO::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
