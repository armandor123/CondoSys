package br.com.condosys.util;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportarUtil {

    public static void exportarParaExcel(JTable tabela, JFrame telaPai) {
        // Abre a janela para o usuário escolher onde salvar o arquivo
        JFileChooser seletorArquivo = new JFileChooser();
        seletorArquivo.setDialogTitle("Salvar Relatório como Excel (CSV)");
        
        int escolha = seletorArquivo.showSaveDialog(telaPai);

        if (escolha == JFileChooser.APPROVE_OPTION) {
            File arquivoEscolhido = seletorArquivo.getSelectedFile();
            String caminhoArquivo = arquivoEscolhido.getAbsolutePath();
            
            // Garante que o arquivo termine com a extensão .csv
            if (!caminhoArquivo.toLowerCase().endsWith(".csv")) {
                caminhoArquivo += ".csv";
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                TableModel modelo = tabela.getModel();
                
                // 1. Exportar os Títulos das Colunas (Cabeçalho)
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    bw.write(modelo.getColumnName(i) + (i == modelo.getColumnCount() - 1 ? "" : ";"));
                }
                bw.newLine();

                // 2. Exportar os Dados Linha por Linha
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        Object valor = modelo.getValueAt(i, j);
                        String texto = (valor == null) ? "" : valor.toString();
                        // Se o texto tiver ponto e vírgula, trocamos por vírgula para não bugar o Excel
                        texto = texto.replace(";", ","); 
                        bw.write(texto + (j == modelo.getColumnCount() - 1 ? "" : ";"));
                    }
                    bw.newLine();
                }
                
                JOptionPane.showMessageDialog(telaPai, "Relatório exportado com sucesso!\nSalvo em: " + caminhoArquivo, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(telaPai, "Erro ao exportar o arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}