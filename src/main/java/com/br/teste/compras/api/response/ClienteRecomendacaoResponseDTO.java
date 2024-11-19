package com.br.teste.compras.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRecomendacaoResponseDTO {

    private String clienteNome;
    private String clienteCpf;
    private ProdutoResponseDTO produto;

    public ClienteRecomendacaoResponseDTO(CompraClienteResponseDTO compraCliente) {
        this.clienteNome = compraCliente.getClienteNome();
        this.clienteCpf = compraCliente.getClienteCpf();
        this.produto = compraCliente.getCompras().getFirst().getProduto();
    }
}
