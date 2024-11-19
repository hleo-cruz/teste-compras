package com.br.teste.compras.service;

import com.br.teste.compras.api.response.ClienteRecomendacaoResponseDTO;
import com.br.teste.compras.api.response.ClienteResponseDTO;
import com.br.teste.compras.api.response.CompraClienteResponseDTO;
import com.br.teste.compras.api.response.CompraResponseDTO;
import com.br.teste.compras.client.clientes.ClienteCompraModel;
import com.br.teste.compras.client.clientes.ClienteModel;
import com.br.teste.compras.client.clientes.ClienteRestTemplateClient;
import com.br.teste.compras.client.produto.ProdutoModel;
import com.br.teste.compras.client.produto.ProdutoRestTemplateClient;
import com.br.teste.compras.mapper.ClienteCompraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        return historicoClientes.stream()
                .peek(cliente -> {

                            List<CompraResponseDTO> compras = cliente.getCompras().stream()
                                    .sorted((compra1, compra2) -> compra2.getValorTotal().compareTo(compra1.getValorTotal()))
                                    .toList();
                            cliente.setCompras(compras);
                        }
                )
                .toList();
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
                .peek(cliente -> {

                    List<ClienteCompraModel> compras = cliente.getCompras().stream()
                            .filter(compra -> produtoIdList.contains(compra.getCodigoAsLong()))
                            .toList();

                    cliente.setCompras(compras);
                })
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
        return construirLista().stream()
                .peek(cliente -> {

                    List<CompraResponseDTO> compras = cliente.getCompras().stream()
                            .sorted((produto1, produto2) -> produto2.getQuantidade().compareTo(produto1.getQuantidade()))
                            .limit(1).toList();

                    cliente.setCompras(compras);

                }).map(ClienteRecomendacaoResponseDTO::new)
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
