package br.com.condosys.main;

import br.com.condosys.dao.UnidadeDAO;
import br.com.condosys.model.Unidade;
import br.com.condosys.util.EstiloUtil;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

public class TelaGerenciarUnidades extends JFrame {

    private JTextField campoBloco, campoNumero, campoProprietario, campoBusca;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> classificadorTabela; // O "Motor" de busca

    public TelaGerenciarUnidades() {
        setTitle("CondoSys - Cadastro de Unidades");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- CABEÇALHO ---
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(EstiloUtil.COR_PRIMARIA);
        painelTopo.setBounds(0, 0, 1000, 65);
        painelTopo.setLayout(null);
        add(painelTopo);

        JLabel lblTitulo = new JLabel("🏢 Gerenciamento de Unidades e Proprietários");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(EstiloUtil.FONTE_TITULO);
        lblTitulo.setBounds(25, 15, 600, 35);
        painelTopo.add(lblTitulo);

        // --- FORMULÁRIO DE CADASTRO ---
        JLabel lblBloco = new JLabel("BLOCO / TORRE");
        lblBloco.setFont(EstiloUtil.FONTE_LABEL);
        lblBloco.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblBloco.setBounds(30, 80, 150, 25);
        add(lblBloco);

        campoBloco = new JTextField();
        campoBloco.setFont(EstiloUtil.FONTE_INPUT);
        campoBloco.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoBloco.setBounds(30, 110, 150, 40);
        add(campoBloco);

        JLabel lblNumero = new JLabel("Nº APARTAMENTO");
        lblNumero.setFont(EstiloUtil.FONTE_LABEL);
        lblNumero.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblNumero.setBounds(200, 80, 150, 25);
        add(lblNumero);

        campoNumero = new JTextField();
        campoNumero.setFont(EstiloUtil.FONTE_INPUT);
        campoNumero.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoNumero.setBounds(200, 110, 150, 40);
        add(campoNumero);

        JLabel lblProprietario = new JLabel("NOME DO PROPRIETÁRIO (Opcional)");
        lblProprietario.setFont(EstiloUtil.FONTE_LABEL);
        lblProprietario.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblProprietario.setBounds(370, 80, 340, 25);
        add(lblProprietario);

        campoProprietario = new JTextField();
        campoProprietario.setFont(EstiloUtil.FONTE_INPUT);
        campoProprietario.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoProprietario.setBounds(370, 110, 340, 40);
        add(campoProprietario);

        JButton btnSalvar = new JButton("CADASTRAR UNIDADE");
        btnSalvar.setFont(EstiloUtil.FONTE_BOTAO);
        btnSalvar.setForeground(EstiloUtil.COR_PRIMARIA);
        btnSalvar.setBackground(Color.WHITE);
        btnSalvar.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setBounds(730, 105, 230, 45);
        add(btnSalvar);

        // --- BARRA DE PESQUISA (NOVIDADE) ---
        JLabel lblBusca = new JLabel("🔍 Buscar por Bloco, Número ou Nome:");
        lblBusca.setFont(EstiloUtil.FONTE_LABEL);
        lblBusca.setForeground(EstiloUtil.COR_PRIMARIA);
        lblBusca.setBounds(30, 170, 300, 25);
        add(lblBusca);

        campoBusca = new JTextField();
        campoBusca.setFont(EstiloUtil.FONTE_INPUT);
        campoBusca.setBorder(new LineBorder(new Color(200, 200, 200), 2));
        campoBusca.setBounds(300, 165, 660, 35);
        add(campoBusca);

        // --- TABELA ---
        modeloTabela = new DefaultTableModel(new Object[]{"ID Sistema", "Bloco", "Número", "Proprietário"}, 0);
        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(35);
        tabela.setGridColor(new Color(240, 240, 245));
        tabela.setSelectionBackground(EstiloUtil.COR_SECUNDARIA);
        tabela.setSelectionForeground(Color.WHITE);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(EstiloUtil.COR_PRIMARIA);
        header.setFont(EstiloUtil.FONTE_LABEL);

        // LIGA O MOTOR DE BUSCA NA TABELA
        classificadorTabela = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(classificadorTabela);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(230, 230, 235)));
        scroll.setBounds(30, 210, 930, 370); // Desci a tabela um pouco para caber a busca
        add(scroll);

        // --- BOTÃO INFERIOR ---
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setForeground(new Color(100, 100, 100));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setBounds(30, 600, 180, 40);
        add(btnAtualizar);

     // ==== BOTÃO EXPORTAR (UNIDADES) ====
        JButton btnExportar = new JButton("📥 Exportar p/ Excel");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setForeground(new Color(39, 174, 96)); 
        btnExportar.setBackground(Color.WHITE);
        btnExportar.setBorder(new LineBorder(new Color(39, 174, 96), 1));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setBounds(30, 600, 180, 40); // Canto inferior esquerdo
        add(btnExportar);

        btnExportar.addActionListener(e -> {
            if (tabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "A tabela está vazia. Não há dados para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            br.com.condosys.util.ExportarUtil.exportarParaExcel(tabela, this);
        });
        
        // --- LÓGICA DE SALVAR ---
        btnSalvar.addActionListener(e -> {
            String bloco = campoBloco.getText();
            String numero = campoNumero.getText();
            String proprietario = campoProprietario.getText();

            if (bloco.trim().isEmpty() || numero.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha o Bloco e o Número!");
                return;
            }

            try {
                Unidade novaUnidade = new Unidade(bloco, numero, proprietario.toUpperCase());
                new UnidadeDAO().salvar(novaUnidade);
                JOptionPane.showMessageDialog(null, "Unidade cadastrada com sucesso!");
                campoBloco.setText(""); campoNumero.setText(""); campoProprietario.setText("");
                carregarDados();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnAtualizar.addActionListener(e -> carregarDados());

        // --- LÓGICA DA BUSCA EM TEMPO REAL ---
        // O DocumentListener "ouve" cada letra digitada ou apagada no campo de busca
        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrarTabela(); }

            private void filtrarTabela() {
                String textoPesquisa = campoBusca.getText();
                if (textoPesquisa.trim().length() == 0) {
                    classificadorTabela.setRowFilter(null); // Mostra tudo se estiver vazio
                } else {
                    // (?i) faz a busca ignorar letras maiúsculas ou minúsculas
                    classificadorTabela.setRowFilter(RowFilter.regexFilter("(?i)" + textoPesquisa));
                }
            }
        });

        // Inicialização
        carregarDados();
    }

    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Object[]> dados = new UnidadeDAO().listarDadosTabela();
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
    }
}