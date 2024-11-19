package com.br.teste.compras.api;

import com.br.teste.compras.api.response.ClienteRecomendacaoResponseDTO;
import com.br.teste.compras.api.response.ClienteResponseDTO;
import com.br.teste.compras.api.response.CompraClienteResponseDTO;
import com.br.teste.compras.service.ClienteCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("compras")
public class ComprasController {

    @Autowired
    private ClienteCompraService service;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CompraClienteResponseDTO> listar() {
        return service.listarCompras();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("maior-compra/{ano}")
    public Optional<CompraClienteResponseDTO> buscarMaiorCompra(@PathVariable Integer ano) {
        return service.buscarMaiorCompraAno(ano);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("clientes-fieis")
    public List<ClienteResponseDTO> top3Clientes() {
        return service.top3Clientes();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("recomendacao/cliente/tipo")
    public List<ClienteRecomendacaoResponseDTO> recomendacaoClienteTipo() {
        return service.recomendacoes();
    }
}
