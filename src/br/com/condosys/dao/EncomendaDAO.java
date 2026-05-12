package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Encomenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncomendaDAO {

    public void salvar(Encomenda encomenda) {
        // O banco salva a 'data_recebimento' automaticamente se estiver configurado como TIMESTAMP
        String sql = "INSERT INTO tabela_encomendas (id, descricao, status, destinatario_id) VALUES (?, ?, ?, ?)";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, encomenda.getId().toString()); 
            ps.setString(2, "Remetente: " + encomenda.getRemetente()); 
            ps.setString(3, "PENDENTE"); 
            ps.setString(4, encomenda.getUnidade().getId()); 
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    // --- ATUALIZADO: Agora busca datas e quem retirou ---
    public List<Object[]> listarDadosTabela() {
        List<Object[]> lista = new ArrayList<>(); 
        String sql = "SELECT id, descricao, status, destinatario_id, data_recebimento, data_retirada, quem_retirou FROM tabela_encomendas"; 
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) { 

            while (rs.next()) {
                Object[] linha = new Object[]{
                    rs.getString("id"),               
                    rs.getString("descricao"),        
                    rs.getString("status"),           
                    rs.getString("destinatario_id"),
                    rs.getTimestamp("data_recebimento"), // Data de Chegada
                    rs.getTimestamp("data_retirada"),    // Data da Baixa
                    rs.getString("quem_retirou")         // Nome de quem pegou
                };
                lista.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista; 
    }

    // --- ATUALIZADO: Agora grava a hora exata da baixa e o nome de quem pegou ---
    public void atualizarStatus(String idEncomenda, String novoStatus, String nomeRetirada) {
        // O comando NOW() pede para o banco de dados registrar o segundo exato de agora
        String sql = "UPDATE tabela_encomendas SET status = ?, data_retirada = NOW(), quem_retirou = ? WHERE id = ?";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, novoStatus); 
            ps.setString(2, nomeRetirada); // Salva o nome de quem pegou
            ps.setString(3, idEncomenda); 
            ps.executeUpdate(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}