package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Unidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeDAO {

    public void salvar(Unidade unidade) {
        String sql = "INSERT INTO tabela_unidades (id, bloco, numero, proprietario) VALUES (?, ?, ?, ?)";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, unidade.getId()); 
            ps.setString(2, unidade.getBloco());
            ps.setString(3, unidade.getNumero());
            ps.setString(4, unidade.getProprietario()); // <--- SALVA NO BANCO
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> listarDadosTabela() {
        List<Object[]> lista = new ArrayList<>();
        // Agora busca o proprietario também
        String sql = "SELECT id, bloco, numero, proprietario FROM tabela_unidades ORDER BY bloco, numero";
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] linha = new Object[]{
                    rs.getString("id"),
                    rs.getString("bloco"),
                    rs.getString("numero"),
                    rs.getString("proprietario") // <--- PARA A TABELA VISUAL
                };
                lista.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Unidade> listarTodasUnidades() {
        List<Unidade> lista = new ArrayList<>();
        String sql = "SELECT id, bloco, numero, proprietario FROM tabela_unidades ORDER BY bloco, numero";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Unidade u = new Unidade(
                    rs.getString("id"),
                    rs.getString("bloco"),
                    rs.getString("numero"),
                    rs.getString("proprietario") // <--- PARA O COMBOBOX
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}