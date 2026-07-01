package com.api.busca_inteligente.controller;

import com.api.busca_inteligente.model.Produto;
import com.api.busca_inteligente.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produtos")
@CrossOrigin(origins = "*") // Permite que o nosso futuro frontend conecte sem problemas de CORS
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/busca")
    public ResponseEntity<List<Produto>> buscar(@RequestParam String q) {
        // Registra o milissegundo exato em que a requisição bateu na API
        long tempoInicial = System.currentTimeMillis();

        List<Produto> resultados = produtoService.buscarProdutos(q);

        // Calcula a diferença de tempo gasta no processo
        long tempoTotal = System.currentTimeMillis() - tempoInicial;
        System.out.println("[API] ⏱️ Busca pelo termo '" + q + "' finalizada em: " + tempoTotal + "ms\n");

        return ResponseEntity.ok(resultados);
    }
}