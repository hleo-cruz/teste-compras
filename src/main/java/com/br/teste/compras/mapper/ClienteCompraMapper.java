package com.br.teste.compras.mapper;

import com.br.teste.compras.api.response.CompraClienteResponseDTO;
import com.br.teste.compras.api.response.CompraResponseDTO;
import com.br.teste.compras.api.response.ProdutoResponseDTO;
import com.br.teste.compras.client.clientes.ClienteCompraModel;
import com.br.teste.compras.client.clientes.ClienteModel;
import com.br.teste.compras.client.produto.ProdutoModel;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ClienteCompraMapper {

    public CompraClienteResponseDTO toDTO(final List<ProdutoModel> produtos, final ClienteModel cliente) {

        final List<CompraResponseDTO> compras = cliente.getCompras().stream().map(compra -> mapCompraResponseDTO(compra, produtos)).toList();
        return mapCompraClienteResponseDTO(compras, cliente);
    }

    private CompraResponseDTO mapCompraResponseDTO(final ClienteCompraModel compra, final List<ProdutoModel> produtos) {

        final BigDecimal quantidade = BigDecimal.valueOf(compra.getQuantidade());

        final Optional<ProdutoResponseDTO> produto = produtos.stream()
                .filter(produtoModel -> produtoModel.getCodigo().equals(compra.getCodigoAsLong()))
                .map(ProdutoResponseDTO::new)
                .findFirst();

        if(produto.isPresent()) {
            return produto.map(produtoModel -> {

                final BigDecimal valorTotal = BigDecimal.valueOf(produtoModel.getPreco()).multiply(quantidade);

                return CompraResponseDTO.builder()
                        .valorTotal(valorTotal)
                        .quantidade(quantidade.intValue())
                        .produto(produtoModel)
                        .build();
            }).get();

        } else {
            return null;
        }
    }

    private CompraClienteResponseDTO mapCompraClienteResponseDTO(final List<CompraResponseDTO> compras, final ClienteModel cliente) {

        final String nome = cliente.getNome();
        final String cpf = cliente.getCpf();

        return CompraClienteResponseDTO.builder()
                .clienteNome(nome)
                .clienteCpf(cpf)
                .compras(compras)
                .build();
    }
}
