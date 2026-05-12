package br.com.condosys.model;

import java.util.UUID;

public class Unidade {
    
    private String id;
    private String bloco;
    private String numero;

    // Construtor 1: Para criar uma unidade nova
    public Unidade(String bloco, String numero) {
        this.id = UUID.randomUUID().toString();
        this.bloco = bloco;
        this.numero = numero;
    }

    // Construtor 2: Para a tela de encomendas (recebe o ID direto)
    public Unidade(String id) {
        this.id = id;
    }

    // --- GETTERS E SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBloco() { return bloco; }
    public void setBloco(String bloco) { this.bloco = bloco; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}