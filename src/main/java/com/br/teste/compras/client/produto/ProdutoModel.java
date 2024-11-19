package com.br.teste.compras.client.produto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProdutoModel {

    private Long codigo;
    private Double preco;
    @JsonProperty(value = "ano_compra")
    private Integer anoCompra;

    private String safra;
    @JsonProperty(value = "tipo_vinho")
    private String tipoVinho;
}
