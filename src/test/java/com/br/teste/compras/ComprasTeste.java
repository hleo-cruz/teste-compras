package com.br.teste.compras;

import com.br.teste.compras.api.response.ClienteRecomendacaoResponseDTO;
import com.br.teste.compras.api.response.ClienteResponseDTO;
import com.br.teste.compras.api.response.CompraClienteResponseDTO;
import com.br.teste.compras.config.WireMockConfig;
import com.br.teste.compras.service.ClienteCompraService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ComprasTeste extends WireMockConfig {

    @Autowired
    private ClienteCompraService service;

    @Test
    public void deveListarHistoricoDeClientes() {

        // when
        final List<CompraClienteResponseDTO> historicoCompras = service.listarCompras();

        // then
        assertNotNull(historicoCompras);
    }

    @Test
    public void deveBuscarMaiorCompraDoAno() {

        // when
        final CompraClienteResponseDTO maiorCompraDoAno = service.buscarMaiorCompraAno(2017).get();

        // then
        assertNotNull(maiorCompraDoAno);
        assertEquals(maiorCompraDoAno.getClienteCpf(), "03763001590");
        assertEquals(maiorCompraDoAno.getValorTotal(), BigDecimal.valueOf(1965.0));
    }

    @Test
    public void deveListarTop3Clientes() {

        // when
        final List<ClienteResponseDTO> top3Clientes = service.top3Clientes();

        // then
        assertNotNull(top3Clientes);
        assertEquals(3, top3Clientes.size());
        assertEquals(top3Clientes.get(0).getClienteCpf(), "27737287426");
        assertEquals(top3Clientes.get(1).getClienteCpf(), "05870189179");
        assertEquals(top3Clientes.get(2).getClienteCpf(), "96718391344");
    }

    @Test
    public void deveListarRecomendacoes() {

        // when
        final List<ClienteRecomendacaoResponseDTO> recomendacoes = service.recomendacoes();

        // then
        assertNotNull(recomendacoes);
        assertEquals(recomendacoes.getFirst().getClienteCpf(), "05870189179");
        assertEquals(1L, (long) recomendacoes.getFirst().getProduto().getCodigo());
    }
}
