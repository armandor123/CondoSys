package br.com.condosys.model;

public abstract class Usuario extends Pessoa {
	
	//Seguindo nosso UML esse são os atributos especificos apenas do usuario
	private String email;
	private String senhaHash;
	private boolean ativo;
	
	// cosntruto
	public Usuario(String nome, String documento, String telefone, String email, String senhaHash) {
	// super usado pra chamar o construtor da classe Pessoa a nossa principal
	//preenchendo o nome, doumento, telefone e gera o id unicdo
	
	super(nome, documento, telefone);
	
	this.email = email;
	this.senhaHash = senhaHash;
	this.ativo = true; //todos usuarios novos ja vao ser ativos por padrão
	

}
	
	// Metodos de negocio (oque o usuario faz)
	public boolean autenticar(String emailLogin, String senhaLogin) {
		// valida se a conta esta ativa e se o email e senha batem
		if (this.ativo && this.email.equals(emailLogin) && this.senhaHash.equals(senhaLogin)) {
			System.out.println("Login aprovado! Bem vindo, " + getNome() );
			return true;
		}
		System.out.println("Acesso negado.");
		return false;
	}
	
	public void alterarSenha(String novaSenha) {
		this.senhaHash = novaSenha;
		System.out.println("senha alterada com sucesso.");
	}
	
	public void desativar() {
		this.ativo =false;
		System.out.println("Usuario desativado do sistema.");
		
	}
	
	// getters e setters especificos do usuario
	public String getEmail(){
		return email;
		
	}// Para booleanos, o Java usa "is" em vez de "get"
	 public boolean isAtivo() {
		 return ativo;
	 }
	
	
	
	
	
	
	
	
	
}