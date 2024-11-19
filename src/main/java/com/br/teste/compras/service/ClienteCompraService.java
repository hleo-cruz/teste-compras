package com.br.teste.compras.service;

import com.br.teste.compras.api.response.ClienteRecomendacaoResponseDTO;
import com.br.teste.compras.api.response.ClienteResponseDTO;
import com.br.teste.compras.api.response.CompraClienteResponseDTO;
import com.br.teste.compras.api.response.CompraResponseDTO;
import com.br.teste.compras.client.clientes.ClienteModel;
import com.br.teste.compras.client.clientes.ClienteRestTemplateClient;
import com.br.teste.compras.client.produto.ProdutoModel;
import com.br.teste.compras.client.produto.ProdutoRestTemplateClient;
import com.br.teste.compras.mapper.ClienteCompraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ClienteCompraService {

    @Autowired
    private ProdutoRestTemplateClient produtoClient;

    @Autowired
    private ClienteRestTemplateClient clienteClient;

    @Autowired
    private ClienteCompraMapper mapper;

    public List<CompraClienteResponseDTO> listarCompras() {

        List<CompraClienteResponseDTO> historicoClientes = construirLista();

        historicoClientes.forEach(cliente -> {
            cliente.getCompras().stream().sorted(Comparator.comparing(CompraResponseDTO::getValorTotal))
                    .toList();
        });

        return historicoClientes;
    }

    public Optional<CompraClienteResponseDTO> buscarMaiorCompraAno(final Integer anoCompra) {

        final List<ProdutoModel> produtos = produtoClient.listarProdutos().stream()
                .filter(produto -> produto.getAnoCompra().equals(anoCompra))
                .toList();

        final List<Long> produtoIdList = produtos.stream()
                .map(ProdutoModel::getCodigo)
                .toList();

        final List<CompraClienteResponseDTO> clientes = clienteClient.listarClientes()
                .stream()
                .filter(cliente -> !cliente.getCompras()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(compra -> produtoIdList.contains(compra.getCodigoAsLong())).toList().isEmpty())
                .map(cliente -> mapper.toDTO(produtos, cliente))
                .toList();

        return clientes.stream()
                .sorted((compra1, compra2) -> compra2.getValorTotal().compareTo(compra1.getValorTotal()))
                .toList().stream().findFirst();
    }

    public List<ClienteResponseDTO> top3Clientes() {

        List<CompraClienteResponseDTO> clientes = construirLista().stream().sorted((cliente1, cliente2) ->
                        Integer.compare(cliente2.getCompras().size(), cliente1.getCompras().size()))
                .toList();

        return clientes.stream().map(ClienteResponseDTO::new).limit(3).toList();
    }

    public List<ClienteRecomendacaoResponseDTO> recomendacoes() {
        return construirLista().stream().peek(cliente -> cliente.getCompras().stream()
                .sorted((produto1, produto2) -> produto2.getQuantidade().compareTo(produto1.getQuantidade()))
                .limit(1).toList()).map(ClienteRecomendacaoResponseDTO::new)
                .toList();
    }

    private List<CompraClienteResponseDTO> construirLista() {
        final List<ProdutoModel> produtos = produtoClient.listarProdutos();
        final List<ClienteModel> clientes = clienteClient.listarClientes();

        return clientes.stream()
                .map(cliente -> mapper.toDTO(produtos, cliente))
                .collect(Collectors.toList());
    }
}
