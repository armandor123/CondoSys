package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void salvarAdministrador(Administrador admin) {
        // Precisamos de dois SQLs porque temos duas tabelas
        String sqlPessoa = "INSERT INTO tabela_pessoas (id, nome, documento, telefone, tipo_pessoa) VALUES (?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO tabela_usuarios (pessoa_id, email, senha) VALUES (?, ?, ?)";

        try (Connection conexao = FabricaConexao.conectar()) {
            // 1. Salva na tabela_pessoas
            try (PreparedStatement psPessoa = conexao.prepareStatement(sqlPessoa)) {
                psPessoa.setString(1, admin.getId().toString());
                psPessoa.setString(2, admin.getNome());
                psPessoa.setString(3, admin.getDocumento());
                psPessoa.setString(4, admin.getTelefone());
                psPessoa.setString(5, "ADMINISTRADOR");
                psPessoa.executeUpdate();
            }

            // 2. Salva na tabela_usuarios
            try (PreparedStatement psUsuario = conexao.prepareStatement(sqlUsuario)) {
                psUsuario.setString(1, admin.getId().toString());
                psUsuario.setString(2, admin.getEmail());
                psUsuario.setString(3, admin.getSenha());
                psUsuario.executeUpdate();
            }

            System.out.println("✅ Administrador salvo com sucesso no banco de dados!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar no banco de dados!");
            e.printStackTrace();
        }
    }
 // Novo método: Vai no banco e verifica se o e-mail e senha existem
    public boolean autenticar(String email, String senha) {
        // Comando SQL de busca (SELECT)
        String sql = "SELECT * FROM tabela_usuarios WHERE email = ? AND senha = ?";
        
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            // Trocamos as interrogações pelo que o usuário digitou na tela
            ps.setString(1, email);
            ps.setString(2, senha);
            
            // O ResultSet é a "caixa" que guarda a resposta do banco de dados
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                // Se o rs.next() for verdadeiro, significa que o banco achou 1 usuário com esses dados!
                return rs.next(); 
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar usuário no banco de dados!");
            e.printStackTrace();
            return false;
        }
    }
}