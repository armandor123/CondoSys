package br.com.condosys.model;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.condosys.enums.StatusAutorizacao;
import br.com.condosys.enums.TipoAutorizacao;

public class Autorizacao {

    // 1. Atributos da Autorização (Conforme seu UML)
    private UUID id;
    private Visitante visitante; // Ficará vermelho até a classe Visitante ser criada
    private Unidade unidade;
    private Proprietario proprietario; // Quem autorizou
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private TipoAutorizacao tipo;
    private StatusAutorizacao status;

    // 2. Construtor
    public Autorizacao(Visitante visitante, Unidade unidade, Proprietario proprietario, 
                       LocalDateTime dataInicio, LocalDateTime dataFim, TipoAutorizacao tipo) {
        this.id = UUID.randomUUID();
        this.visitante = visitante;
        this.unidade = unidade;
        this.proprietario = proprietario;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipo = tipo;
        this.status = StatusAutorizacao.ATIVA; // Já nasce ativa por padrão
    }

    // 3. Getters
    public UUID getId() { return id; }
    public Visitante getVisitante() { return visitante; }
    public Unidade getUnidade() { return unidade; }
    public Proprietario getProprietario() { return proprietario; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public TipoAutorizacao getTipo() { return tipo; }
    public StatusAutorizacao getStatus() { return status; }

    // 4. Métodos de Negócio (A inteligência do seu sistema!)
    
    // Verifica se a autorização expirou baseada na data atual
    public boolean expirou() {
        if (this.tipo == TipoAutorizacao.TEMPO_INDETERMINADO) {
            return false; // Nunca expira sozinho
        }
        // Se a data atual for DEPOIS da dataFim, expirou!
        return LocalDateTime.now().isAfter(this.dataFim);
    }

    // Verifica se a autorização é válida para o visitante entrar AGORA
    public boolean isValida() {
        if (this.status != StatusAutorizacao.ATIVA) {
            return false;
        }
        if (expirou()) {
            this.status = StatusAutorizacao.EXPIRADA; // Atualiza o status automaticamente
            return false;
        }
        return true;
    }

    // O proprietário pode revogar (cancelar) a autorização a qualquer momento
    public void revogar() {
        this.status = StatusAutorizacao.REVOGADA;
        System.out.println("Autorização revogada com sucesso.");
    }

    // Renovar a autorização com uma nova data
    public void renovar(LocalDateTime novaDataFim) {
        this.dataFim = novaDataFim;
        this.status = StatusAutorizacao.ATIVA;
        System.out.println("Autorização renovada até: " + novaDataFim);
    }
}
