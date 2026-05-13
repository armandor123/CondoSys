package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO {

    public int getTotalUnidades() {
        return executarCount("SELECT COUNT(id) FROM tabela_unidades");
    }

    public int getVisitantesAtivos() {
        return executarCount("SELECT COUNT(id) FROM tabela_visitantes WHERE data_saida IS NULL");
    }

    public int getReservasHoje() {
        return executarCount("SELECT COUNT(id) FROM tabela_reservas WHERE data_reserva = CURRENT_DATE()");
    }

    public int getEncomendasPendentes() {
        // Conta as encomendas que ainda não foram marcadas como entregues
        return executarCount("SELECT COUNT(id) FROM tabela_encomendas WHERE status != 'ENTREGUE' OR status IS NULL");
    }

    // Método auxiliar para não repetir código de conexão
    private int executarCount(String sql) {
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1); // Pega o número contado pelo banco
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar estatística: " + e.getMessage());
        }
        return 0; // Retorna 0 em caso de erro para não quebrar a tela
    }
}