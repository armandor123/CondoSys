package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Visitante;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class VisitanteDAO {

    /**
     * Módulo CREATE: Salva a ENTRADA do visitante no banco de dados.
     */
    public void registrarEntrada(Visitante visitante) {
        // Note que não mandamos a data_entrada, o MySQL preenche sozinho!
        String sql = "INSERT INTO tabela_visitantes (id, nome, documento, unidade_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, visitante.getId()); 
            ps.setString(2, visitante.getNome());
            ps.setString(3, visitante.getDocumento());
            ps.setString(4, visitante.getUnidadeDestino().getId()); // Salva o ID do apartamento
            
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Erro ao registrar entrada do visitante.");
            e.printStackTrace();
        }
    }

    /**
     * Módulo UPDATE: Atualiza a hora em que o visitante foi embora.
     */
    public void registrarSaida(String idVisitante) {
        String sql = "UPDATE tabela_visitantes SET data_saida = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, idVisitante);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao registrar saída.");
            e.printStackTrace();
        }
    }

    /**
     * Módulo READ: Busca todos os visitantes e formata para a Tabela Visual.
     */
    public List<Object[]> listarDadosTabela() {
        List<Object[]> lista = new ArrayList<>();
        
        // O JOIN junta a tabela de visitantes com a tabela de unidades
        String sql = "SELECT v.id, v.nome, v.documento, u.bloco, u.numero, v.data_entrada, v.data_saida " +
                     "FROM tabela_visitantes v " +
                     "JOIN tabela_unidades u ON v.unidade_id = u.id " +
                     "ORDER BY v.data_entrada DESC"; // Mostra os mais recentes primeiro
        
        // Formatador para deixar a data bonita (Ex: 15/05/2026 14:30)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Formata a Entrada
                Timestamp tsEntrada = rs.getTimestamp("data_entrada");
                String strEntrada = (tsEntrada != null) ? sdf.format(tsEntrada) : "";

                // Formata a Saída
                Timestamp tsSaida = rs.getTimestamp("data_saida");
                String strSaida = (tsSaida != null) ? sdf.format(tsSaida) : "AINDA NO CONDOMÍNIO";

                // Junta o bloco e número (Ex: Bloco A - 101)
                String destino = rs.getString("bloco") + " - " + rs.getString("numero");

                Object[] linha = new Object[]{
                    rs.getString("id"),
                    rs.getString("nome"),
                    rs.getString("documento"),
                    destino,
                    strEntrada,
                    strSaida
                };
                lista.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}