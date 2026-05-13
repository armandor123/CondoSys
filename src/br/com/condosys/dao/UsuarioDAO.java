package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    /**
     * Autentica pelo E-MAIL e SENHA, e só permite acesso se a conta estiver ATIVA.
     */
    public Usuario autenticar(String email, String senha) {
        // Verifica email, senha e se a coluna ativo é verdadeira
        String sql = "SELECT * FROM tabela_usuarios WHERE email = ? AND senha = ? AND ativo = TRUE";
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, senha);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Usa EXATAMENTE o construtor da sua classe Usuario: 
                    // (nome, documento, telefone, email, senha)
                    return new Usuario(
                        rs.getString("nome"),
                        rs.getString("documento"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna nulo se falhar no login
    }
}