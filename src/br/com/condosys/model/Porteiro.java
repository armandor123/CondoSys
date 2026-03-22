package br.com.condosys.model;

import java.time.LocalDate;

public class Porteiro extends Usuario {
	
	//atributos especificos do porteiro
	
	private String turno;
	private LocalDate dataAdmissao;
	
	public Porteiro(String nome, String documento, String telefone, String email, String senhaHash, String turno, LocalDate dataAdmissao)		{
		//super vai enviar os 5 daddos para classe principal = usuario
		super(nome, documento, telefone, email, senhaHash);		
		//porteiro cuirada só dos seus proprios atibutos
		
		this.turno = turno;
		this.dataAdmissao = dataAdmissao;
		
	}
	
	// GETTERS E SETTERS ESPECIFICOS DO PORTEIRO
	
	public String getTurno() {
		return turno;
	}
	
	public void setTruno(String turno) {
		this.turno = turno;
	}
	
	public localDate getDataAdminissão() {
		return dataAdmissao;
	}
	/ 4. Métodos de Negócio (Esqueletos)
    /* * Nota do Professor: No seu diagrama, o Porteiro tem métodos como "registrarEntrada".
     * Como esses métodos dependem de classes que ainda não criamos (como RegistroAcesso e Unidade),
     * nós vamos deixar apenas um aviso em texto por enquanto para o código não dar erro!
     */
	
	public void registrarEntrada() {
		System.out.println("Logica de registrar eentrada sera implementada no futuro.");
		
	}
	
	public void registrarSaida() {
		System.out.println("Logica de resgistrar saida sera implementada aqui futuramente.");
	}
	
	
	
	
	
}
