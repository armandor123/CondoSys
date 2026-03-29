package br.com.condosys.model;

import java.time.LocalDateTime;
import java.util.UUID;
import br.com.condosys.enums.StatusEncomenda;

public class Encomenda {
    private UUID id;
    private LocalDateTime dataRecebimento;
    private LocalDateTime dataEntrega;
    private Unidade unidade;
    private Porteiro porteiro;
    private String remetente;
    private StatusEncomenda status;

    public Encomenda(Unidade unidade, Porteiro porteiro, String remetente) {
        this.setId(UUID.randomUUID());
        this.setDataRecebimento(LocalDateTime.now());
        this.setUnidade(unidade);
        this.setPorteiro(porteiro);
        this.setRemetente(remetente);
        this.setStatus(StatusEncomenda.RECEBIDA);
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDateTime dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public LocalDateTime getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDateTime dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public Porteiro getPorteiro() {
		return porteiro;
	}

	public void setPorteiro(Porteiro porteiro) {
		this.porteiro = porteiro;
	}

	public StatusEncomenda getStatus() {
		return status;
	}

	public void setStatus(StatusEncomenda status) {
		this.status = status;
	}
    
    // (Getters e Setters ficarão a cargo dele)
}