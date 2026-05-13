package br.com.condosys.model;

import java.util.UUID;

public class Reserva {
    private String id;
    private Unidade unidade;
    private String areaComum;
    private String dataReserva; // Vamos usar DD/MM/YYYY no Java

    // Construtor para NOVA reserva
    public Reserva(Unidade unidade, String areaComum, String dataReserva) {
        this.id = UUID.randomUUID().toString();
        this.unidade = unidade;
        this.areaComum = areaComum;
        this.dataReserva = dataReserva;
    }

    // Construtor para ler do Banco de Dados
    public Reserva(String id, Unidade unidade, String areaComum, String dataReserva) {
        this.id = id;
        this.unidade = unidade;
        this.areaComum = areaComum;
        this.dataReserva = dataReserva;
    }

    public String getId() { return id; }
    public Unidade getUnidade() { return unidade; }
    public String getAreaComum() { return areaComum; }
    public String getDataReserva() { return dataReserva; }
}