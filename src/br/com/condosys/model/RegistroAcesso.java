package br.com.condosys.model;

import java.time.LocalDateTime;
import java.util.UUID;
import br.com.condosys.enums.StatusAcesso;

public class RegistroAcesso {

    private UUID id;
    private LocalDateTime dataHora;
    private Pessoa pessoa; // Visitante ou Prestador [cite: 55, 56]
    private Porteiro porteiro; // Quem realizou o registro [cite: 56]
    private Unidade unidade; // Destino [cite: 58]
    private Autorizacao autorizacao; // Se houve uma [cite: 336]
    private StatusAcesso status; // AUTORIZADO, NEGADO, etc [cite: 57]
    private String observacoes;

    public RegistroAcesso(Pessoa pessoa, Porteiro porteiro, Unidade unidade, 
                          Autorizacao autorizacao, StatusAcesso status) {
        this.setId(UUID.randomUUID()); // [cite: 336]
        this.dataHora = LocalDateTime.now(); // [cite: 56]
        this.pessoa = pessoa;
        this.setPorteiro(porteiro);
        this.setUnidade(unidade);
        this.setAutorizacao(autorizacao);
        this.status = status;
    }

    // Getters para os relatórios de auditoria [cite: 81]
    public LocalDateTime getDataHora() { return dataHora; }
    public StatusAcesso getStatus() { return status; }
    public Pessoa getPessoa() { return pessoa; }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Porteiro getPorteiro() {
		return porteiro;
	}

	public void setPorteiro(Porteiro porteiro) {
		this.porteiro = porteiro;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Autorizacao getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(Autorizacao autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
}