package br.com.condosys.model;

import java.util.UUID;

public class Unidade {
    private String id;
    private String bloco;
    private String numero;
    private String proprietario; // <--- NOVA COLUNA

    public Unidade(String id) { this.id = id; }

    public Unidade(String bloco, String numero, String proprietario) {
        this.id = UUID.randomUUID().toString();
        this.bloco = bloco;
        this.numero = numero;
        this.proprietario = proprietario;
    }

    public Unidade(String id, String bloco, String numero, String proprietario) {
        this.id = id;
        this.bloco = bloco;
        this.numero = numero;
        this.proprietario = proprietario;
    }

    public String getId() { return id; }
    public String getBloco() { return bloco; }
    public String getNumero() { return numero; }
    public String getProprietario() { return proprietario; }

    // Agora o Dropdown vai mostrar: Bloco A - Apto 101 (Carlos Drummond)
    @Override
    public String toString() {
        if (proprietario != null && !proprietario.isEmpty()) {
            return "Bloco " + bloco + " - Apto " + numero + " (" + proprietario + ")";
        }
        return "Bloco " + bloco + " - Apto " + numero;
    }
}