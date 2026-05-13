package br.com.condosys.dao;

import br.com.condosys.config.FabricaConexao;
import br.com.condosys.model.Reserva;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Motor de dados para o módulo de Reservas.
 */
public class ReservaDAO {

    /**
     * Verifica se o espaço já está ocupado na data escolhida.
     * @return TRUE se estiver livre, FALSE se estiver ocupado.
     */
    public boolean isDataDisponivel(String areaComum, String dataReservaBr) {
        String sql = "SELECT id FROM tabela_reservas WHERE area_comum = ? AND data_reserva = ?";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            // Converte a data do formato visual para o formato do banco
            String dataMySQL = converterDataParaMySQL(dataReservaBr);
            
            ps.setString(1, areaComum);
            ps.setString(2, dataMySQL);
            
            try (ResultSet rs = ps.executeQuery()) {
                return !rs.next(); // Se o resultado for vazio, a data está livre!
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }

    /**
     * Salva uma nova reserva no banco de dados.
     */
    public void salvar(Reserva reserva) throws Exception {
        // Validação de segurança: Impede reserva dupla no mesmo dia/local
        if (!isDataDisponivel(reserva.getAreaComum(), reserva.getDataReserva())) {
            throw new Exception("Lamento! O espaço '" + reserva.getAreaComum() + "' já está reservado para este dia.");
        }

        String sql = "INSERT INTO tabela_reservas (id, unidade_id, area_comum, data_reserva) VALUES (?, ?, ?, ?)";
        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, reserva.getId());
            ps.setString(2, reserva.getUnidade().getId());
            ps.setString(3, reserva.getAreaComum());
            ps.setString(4, converterDataParaMySQL(reserva.getDataReserva()));
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erro técnico ao salvar no banco de dados.");
        }
    }

    /**
     * Busca as reservas formatadas para a tabela, incluindo o nome do Morador (JOIN).
     */
    public List<Object[]> listarDadosTabela() {
        List<Object[]> lista = new ArrayList<>();
        
        // SQL com JOIN: Busca dados da Reserva e o Nome/Número da Unidade
        String sql = "SELECT r.id, u.bloco, u.numero, u.proprietario, r.area_comum, r.data_reserva " +
                     "FROM tabela_reservas r " +
                     "JOIN tabela_unidades u ON r.unidade_id = u.id " +
                     "ORDER BY r.data_reserva DESC";
        
        SimpleDateFormat sdfBR = new SimpleDateFormat("dd/MM/yyyy");

        try (Connection conexao = FabricaConexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                // Monta a string: "Bloco A - 101 (Carlos Drummond)"
                String identificadorApto = rs.getString("bloco") + " - " + rs.getString("numero");
                String nomeMorador = rs.getString("proprietario");
                String moradorResponsavel = identificadorApto + " (" + (nomeMorador != null ? nomeMorador : "S/ Nome") + ")";
                
                // Formata a data para o padrão brasileiro
                String dataFormatada = "";
                Date dataBanco = rs.getDate("data_reserva");
                if (dataBanco != null) {
                    dataFormatada = sdfBR.format(dataBanco);
                }

                Object[] linha = new Object[]{
                    rs.getString("id"),
                    moradorResponsavel, // Coluna 1 da tabela
                    rs.getString("area_comum"),
                    dataFormatada
                };
                lista.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Auxiliar: Converte DD/MM/YYYY para YYYY-MM-DD
     */
    private String converterDataParaMySQL(String dataBr) {
        try {
            SimpleDateFormat sdfBR = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataJava = sdfBR.parse(dataBr);
            return sdfSQL.format(dataJava);
        } catch (Exception e) {
            return dataBr;
        }
    }
}