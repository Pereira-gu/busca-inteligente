package com.api.busca_inteligente.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String categoria;
    private double preco;

    // Construtor vazio padrão (Necessário para a serialização/Jackson)
    public Produto() {
    }

    // Construtor completo
    public Produto(Long id, String nome, String categoria, double preco) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}