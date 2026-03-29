package br.com.condosys.model;

import java.time.LocalDate;

// Proprietario ESTENDE Usuario
public class Proprietario extends Usuario {

    // 1. Atributos específicos
    private LocalDate dataAdesao;
    
    // Associação: O Proprietário "TEM UMA" Unidade. 
    // (Vai ficar vermelho até criarmos a classe Unidade no próximo passo)
    private Unidade unidade; 

    // 2. Construtor
    public Proprietario(String nome, String documento, String telefone, String email, String senhaHash, LocalDate dataAdesao) {
        super(nome, documento, telefone, email, senhaHash);
        this.dataAdesao = dataAdesao;
        // Não pedimos a Unidade no construtor pois ela pode ser vinculada depois pelo Administrador
    }

    // 3. Getters e Setters
    public LocalDate getDataAdesao() {
        return dataAdesao;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    // 4. Métodos de Negócio (Esqueletos)
    public void consultarEntradas() {
        System.out.println("Listando o histórico de acessos da unidade...");
    }
}
