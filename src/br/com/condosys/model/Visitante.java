package br.com.condosys.model;

import java.time.LocalDateTime;

public class Visitante extends Pessoa {
    private LocalDateTime dataUltimaVisita;

    public Visitante(String nome, String documento, String telefone) {
        super(nome, documento, telefone);
        this.dataUltimaVisita = LocalDateTime.now();
    }

    public LocalDateTime getDataUltimaVisita() { return dataUltimaVisita; }
    public void atualizarUltimaVisita() { this.dataUltimaVisita = LocalDateTime.now(); }
}