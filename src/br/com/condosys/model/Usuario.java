package br.com.condosys.model;

public class Usuario extends Pessoa {
	
	// Seguindo nosso UML esses são os atributos específicos apenas do usuario
	private String email;
	private String senhaHash;
	private boolean ativo;
	
	// Construtor
	public Usuario(String nome, String documento, String telefone, String email, String senhaHash) {
	    // super usado pra chamar o construtor da classe Pessoa a nossa principal
	    // preenchendo o nome, documento, telefone e gera o id unico
	    super(nome, documento, telefone);
	
	    this.email = email;
	    this.senhaHash = senhaHash;
	    this.ativo = true; // todos usuarios novos ja vao ser ativos por padrão
	}
	
	// Métodos de negocio (o que o usuario faz)
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
		this.ativo = false;
		System.out.println("Usuario desativado do sistema.");
	}
	
	public String getSenha() {
	    return senhaHash;
	}
	
	// Getters e setters especificos do usuario
	public String getEmail() {
		return email;
	}
	
	// Para booleanos, o Java usa "is" em vez de "get"
	public boolean isAtivo() {
		return ativo;
	}
}