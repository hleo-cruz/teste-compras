package com.br.teste.compras.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private String clienteNome;
    private String clienteCpf;

    public ClienteResponseDTO(CompraClienteResponseDTO compraClienteResponseDTO) {
        this.clienteNome = compraClienteResponseDTO.getClienteNome();
        this.clienteCpf = compraClienteResponseDTO.getClienteCpf();
    }
}
