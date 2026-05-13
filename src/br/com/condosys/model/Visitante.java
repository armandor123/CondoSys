package br.com.condosys.model;

import java.util.UUID;

public class Visitante {
    private String id;
    private String nome;
    private String documento;
    private Unidade unidadeDestino;

    // Construtor para registrar um NOVO visitante (gera o ID na hora)
    public Visitante(String nome, String documento, Unidade unidadeDestino) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.documento = documento;
        this.unidadeDestino = unidadeDestino;
    }

    // Getters para o DAO conseguir ler os dados
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public Unidade getUnidadeDestino() { return unidadeDestino; }
}