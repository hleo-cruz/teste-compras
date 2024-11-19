package com.br.teste.compras.api.response;

import com.br.teste.compras.client.produto.ProdutoModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {

    private Long codigo;
    private Double preco;
    private Integer anoCompra;
    private String safra;
    private String tipoVinho;

    public ProdutoResponseDTO(ProdutoModel produtoModel) {
        this.codigo = produtoModel.getCodigo();
        this.preco = produtoModel.getPreco();
        this.anoCompra = produtoModel.getAnoCompra();
        this.safra = produtoModel.getSafra();
        this.tipoVinho = produtoModel.getTipoVinho();
    }
}
