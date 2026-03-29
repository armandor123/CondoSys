package br.com.condosys.model;

import java.util.UUID;

// CLASSE DE MOLDE 
public abstract class Pessoa {
	
	// atributos privados encapsulamento
	private UUID id;
	private String nome;
	private String documento;
	private String telefone;
	
	// constutor inicio do sistema
	public Pessoa(String nome, String documento, String telefone){
		this.id = UUID.randomUUID(); // O HAVA GERA UM CODIGO UNICO E ALEATORIO ALTOMATICAMENTE
		this.nome = nome;
		this.documento = documento;
		this.telefone = telefone;
	}
	// GATTERS (PARA OUTORAS CLASSES ACESSAREM E LER ESSES DADOS PRIVADOS
	public UUID getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDcoumento() {
		return documento;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
}
