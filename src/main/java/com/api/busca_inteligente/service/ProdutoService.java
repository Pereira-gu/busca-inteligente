package com.api.busca_inteligente.service;

import com.api.busca_inteligente.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    // Lista em memória simulando os registos do nosso Banco de Dados
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

        // Por enquanto, vai sempre direto à simulação do banco lento
        return buscarNoBancoDeDadosSimulado(termoBusca);
    }

    private List<Produto> buscarNoBancoDeDadosSimulado(String termo) {
        try {
            // Código ANSI para deixar o texto do terminal amarelo
            System.out.println("\u001B[33m[BANCO DE DADOS] ⚠️ Fazendo busca pesada no Postgres para o termo: '" + termo + "'...\u001B[00m");

            // Simula a lentidão de uma consulta pesada travando a thread por 1 segundo
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return bancoDeDadosSimulado.stream()
                .filter(p -> p.getNome().toLowerCase().contains(termo))
                .collect(Collectors.toList());
    }
}