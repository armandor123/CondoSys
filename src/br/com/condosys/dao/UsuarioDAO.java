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
}