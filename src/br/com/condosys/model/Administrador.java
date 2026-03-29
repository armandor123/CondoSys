package br.com.condosys.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Administrador extends Usuario {
    private LocalDate dataContratacao;
    private Set<String> permissoes;

    public Administrador(String nome, String documento, String telefone, String email, String senhaHash, LocalDate dataContratacao) {
        super(nome, documento, telefone, email, senhaHash);
        this.dataContratacao = dataContratacao;
        this.permissoes = new HashSet<>();
    }

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public Set<String> getPermissoes() { return permissoes; }
    public void adicionarPermissao(String permissao) { this.permissoes.add(permissao); }
    public void gerenciarUsuarios() { System.out.println("Gerenciando usuários..."); }
}