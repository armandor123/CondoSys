

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    // 1. As credenciais do seu Banco de Dados (Ajuste conforme o seu MySQL)
    private static final String URL = "jdbc:mysql://localhost:3306/condosys_db";
    private static final String USUARIO = "Armando";
    private static final String SENHA = "N15k35mjyam0"; // Coloque a sua senha do MySQL aqui!

    // 2. O método que "fabrica" e entrega a conexão pronta
    public static Connection conectar() {
        try {
            // Tenta abrir a porta de comunicação com o banco
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("✅ Conexão com o Banco de Dados realizada com sucesso!");
            return conexao;
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar com o Banco de Dados!");
            e.printStackTrace(); // Mostra o erro no console para o programador ler
            throw new RuntimeException("Falha na conexão com o banco de dados.", e);
        }
    }

    // 3. Um mini "Motor de Partida" só para testarmos se a fábrica funciona
    public static void main(String[] args) {
        FabricaConexao.conectar();
    }
}

