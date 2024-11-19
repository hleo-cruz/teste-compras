package com.br.teste.compras.client.clientes;

import lombok.Data;

import java.util.List;

@Data
public class ClienteModel {

    private String nome;
    private String cpf;
    private List<ClienteCompraModel> compras;
}
