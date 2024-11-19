package com.br.teste.compras.client.clientes;

import lombok.Data;

@Data
public class ClienteCompraModel {

    private String codigo;
    private Integer quantidade;

    public Long getCodigoAsLong() {
        return Long.parseLong(codigo);
    }
}
