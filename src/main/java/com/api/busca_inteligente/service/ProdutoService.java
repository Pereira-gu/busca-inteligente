package com.api.busca_inteligente.service;

import com.api.busca_inteligente.model.Produto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Utilitário para converter a lista de Objetos em JSON String e vice-versa
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Produto> bancoDeDadosSimulado = List.of(
            new Produto(1L, "Notebook Gamer Dell G15", "Eletrônicos", 5500.0),
            new Produto(2L, "Notebook Apple MacBook Air M2", "Eletrônicos", 9000.0),
            new Produto(3L, "Smartphone Samsung Galaxy S24", "Celulares", 4500.0),
            new Produto(4L, "Smartphone Apple iPhone 15 Pro", "Celulares", 7500.0),
            new Produto(5L, "Monitor Gamer LG 29 Ultrawide", "Monitores", 1200.0),
            new Produto(6L, "Teclado Mecânico Logitech G Pro", "Acessórios", 600.0),
            new Produto(7L, "Mouse Sem Fio Logitech MX Master 3S", "Acessórios", 550.0),
            new Produto(8L, "Cadeira Gamer Reclinável DT3", "Móveis", 1500.0)
    );

    public List<Produto> buscarProdutos(String termo) {
        String termoBusca = termo.toLowerCase().trim();

        if (termoBusca.isEmpty()) {
            return new ArrayList<>();
        }

        String chaveRedis = "busca:cache:" + termoBusca;

        try {
            // 1. Tenta buscar o resultado direto no Cache do Redis
            String jsonCache = redisTemplate.opsForValue().get(chaveRedis);

            if (jsonCache != null) {
                // HIT: Dado encontrado no Redis! Transformamos o JSON em Lista de Produtos de novo.
                System.out.println("\u001B[32m[REDIS CACHE] ⚡ HIT! Termo '" + termoBusca + "' encontrado na memória.\u001B[00m");
                return objectMapper.readValue(jsonCache, new TypeReference<List<Produto>>() {});
            }

            // MISS: Dado não estava no Redis. Faz a busca lenta no "Banco de Dados"
            System.out.println("\u001B[34m[REDIS CACHE] ❌ MISS! Termo '" + termoBusca + "' não está no cache. Buscando no Banco...\u001B[00m");
            List<Produto> resultadosBanco = buscarNoBancoDeDadosSimulado(termoBusca);

            // 2. Salva o resultado no Redis em formato JSON String com TTL de 10 minutos
            String jsonResultado = objectMapper.writeValueAsString(resultadosBanco);
            redisTemplate.opsForValue().set(chaveRedis, jsonResultado, Duration.ofMinutes(10));

            return resultadosBanco;

        } catch (Exception e) {
            // Caso ocorra falha de serialização, faz fallback seguro para o banco de dados
            System.err.println("[ERRO] Falha ao gerenciar cache: " + e.getMessage());
            return buscarNoBancoDeDadosSimulado(termoBusca);
        }
    }

    private List<Produto> buscarNoBancoDeDadosSimulado(String termo) {
        try {
            System.out.println("\u001B[33m[BANCO DE DADOS] ⚠️ Fazendo busca pesada no Postgres para o termo: '" + termo + "'...\u001B[00m");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return bancoDeDadosSimulado.stream()
                .filter(p -> p.getNome().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }
}