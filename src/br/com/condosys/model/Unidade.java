package br.com.condosys.model;

import java.util.UUID;

public class Unidade {

    // 1. Atributos da Unidade
    private UUID id;
    private String numero;
    private String bloco;
    private boolean ativa;
    
    // Associação: A Unidade "CONHECE" o seu Proprietário
    private Proprietario proprietario;

    // 2. Construtor
    // Note que não pedimos o Proprietário no momento da criação, 
    // pois o apartamento existe fisicamente antes de ser vendido para alguém!
    public Unidade(String numero, String bloco) {
        this.id = UUID.randomUUID();
        this.numero = numero;
        this.bloco = bloco;
        this.ativa = true; // A unidade já nasce ativa
    }

    // 3. Getters e Setters
    public UUID getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public boolean isAtiva() {
        return ativa;
    }

    // 4. Métodos de Negócio Específicos (conforme o seu UML)
    public void ativar() {
        this.ativa = true;
        System.out.println("A unidade " + bloco + "-" + numero + " foi ativada.");
    }

    public void desativar() {
        this.ativa = false;
        System.out.println("A unidade " + bloco + "-" + numero + " foi desativada.");
    }
}