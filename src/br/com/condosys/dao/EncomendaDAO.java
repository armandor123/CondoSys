package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Encomenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EncomendaDAO {

    // Método para salvar uma nova encomenda no banco de dados
    public void salvar(Encomenda encomenda) {
        // O comando SQL com as interrogações (medida de segurança contra hackers)
        String sql = "INSERT INTO tabela_encomendas (id, descricao, status, destinatario_id) VALUES (?, ?, ?, ?)";

        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            // 1. Pega o ID gerado no Java (assumindo que seja um UUID transformado em String)
            ps.setString(1, encomenda.getId().toString()); 
            
            // 2. Pega a descrição da encomenda
            ps.setString(2, encomenda.getDescricao());
            
            // 3. Pega o status (Assumindo que seja um Enum, usamos o .name() para pegar o texto exato)
            ps.setString(3, encomenda.getStatus().name()); 
            
            // 4. Pega o ID do morador/pessoa que vai receber a encomenda
            // Atenção aqui: o destinatário é um objeto Pessoa, então pegamos o ID dentro dele
            ps.setString(4, encomenda.getDestinatario().getId().toString());

            // Executa o comando no banco de dados!
            ps.executeUpdate();
            System.out.println("📦 Encomenda salva com sucesso no banco de dados!");

        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar a encomenda no banco de dados!");
            e.printStackTrace();
        }
    }
}