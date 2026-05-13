package br.com.condosys.main;

import br.com.condosys.dao.ReservaDAO;
import br.com.condosys.dao.UnidadeDAO;
import br.com.condosys.model.Reserva;
import br.com.condosys.model.Unidade;
import br.com.condosys.util.EstiloUtil;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

public class TelaGerenciarReservas extends JFrame {

    private JComboBox<String> comboAreaComum;
    private JFormattedTextField campoData;
    private JTextField campoBuscaApto, campoBuscaTabela;
    private JComboBox<Unidade> comboUnidades;
    
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> classificadorTabela;
    
    private List<Unidade> listaTodasUnidades;

    public TelaGerenciarReservas() {
        setTitle("CondoSys - Reservas de Áreas Comuns");
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

        JLabel lblTitulo = new JLabel("📅 Gerenciamento de Reservas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(EstiloUtil.FONTE_TITULO);
        lblTitulo.setBounds(25, 15, 600, 35);
        painelTopo.add(lblTitulo);

        // --- FORMULÁRIO ---
        JLabel lblArea = new JLabel("ÁREA COMUM");
        lblArea.setFont(EstiloUtil.FONTE_LABEL);
        lblArea.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblArea.setBounds(30, 80, 200, 25);
        add(lblArea);

        // Combo com as áreas do condomínio
        String[] areas = {"Salão de Festas", "Churrasqueira 1", "Churrasqueira 2", "Quadra Poliesportiva", "Espaço Gourmet"};
        comboAreaComum = new JComboBox<>(areas);
        comboAreaComum.setFont(EstiloUtil.FONTE_INPUT);
        comboAreaComum.setBackground(Color.WHITE);
        comboAreaComum.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        comboAreaComum.setBounds(30, 110, 220, 40);
        add(comboAreaComum);

        // MÁSCARA PARA A DATA (DD/MM/YYYY)
        JLabel lblData = new JLabel("DATA (DD/MM/AAAA)");
        lblData.setFont(EstiloUtil.FONTE_LABEL);
        lblData.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblData.setBounds(270, 80, 150, 25);
        add(lblData);

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            campoData = new JFormattedTextField(mascaraData);
        } catch (Exception ex) {
            campoData = new JFormattedTextField();
        }
        campoData.setFont(EstiloUtil.FONTE_INPUT);
        campoData.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoData.setBounds(270, 110, 150, 40);
        add(campoData);

        JLabel lblBuscaApto = new JLabel("🔍 BUSCAR APTO");
        lblBuscaApto.setFont(EstiloUtil.FONTE_LABEL);
        lblBuscaApto.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblBuscaApto.setBounds(440, 80, 120, 25);
        add(lblBuscaApto);

        campoBuscaApto = new JTextField();
        campoBuscaApto.setFont(EstiloUtil.FONTE_INPUT);
        campoBuscaApto.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoBuscaApto.setBounds(440, 110, 100, 40);
        add(campoBuscaApto);

        JLabel lblId = new JLabel("UNIDADE DESTINO");
        lblId.setFont(EstiloUtil.FONTE_LABEL);
        lblId.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblId.setBounds(560, 80, 200, 25);
        add(lblId);

        comboUnidades = new JComboBox<>();
        comboUnidades.setFont(EstiloUtil.FONTE_INPUT);
        comboUnidades.setBackground(Color.WHITE);
        comboUnidades.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        comboUnidades.setBounds(560, 110, 200, 40);
        add(comboUnidades);

        JButton btnSalvar = new JButton("SALVAR RESERVA");
        btnSalvar.setFont(EstiloUtil.FONTE_BOTAO);
        btnSalvar.setForeground(EstiloUtil.COR_PRIMARIA);
        btnSalvar.setBackground(Color.WHITE);
        btnSalvar.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setBounds(780, 105, 180, 45);
        add(btnSalvar);

        // --- PESQUISA NA TABELA ---
        JLabel lblBuscaTab = new JLabel("🔍 Filtrar Reservas:");
        lblBuscaTab.setFont(EstiloUtil.FONTE_LABEL);
        lblBuscaTab.setForeground(EstiloUtil.COR_PRIMARIA);
        lblBuscaTab.setBounds(30, 170, 150, 25);
        add(lblBuscaTab);

        campoBuscaTabela = new JTextField();
        campoBuscaTabela.setFont(EstiloUtil.FONTE_INPUT);
        campoBuscaTabela.setBorder(new LineBorder(new Color(200, 200, 200), 2));
        campoBuscaTabela.setBounds(180, 165, 780, 35);
        add(campoBuscaTabela);

        // --- TABELA ---
        modeloTabela = new DefaultTableModel(new Object[]{
            "ID Reserva (Oculto)", "Morador Responsável", "Área Reservada", "Data da Reserva"
        }, 0);
        
        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(35);
        tabela.setGridColor(new Color(240, 240, 245));
        tabela.setSelectionBackground(EstiloUtil.COR_SECUNDARIA);
        tabela.setSelectionForeground(Color.WHITE);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setForeground(EstiloUtil.COR_PRIMARIA);
        header.setFont(EstiloUtil.FONTE_LABEL);

        classificadorTabela = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(classificadorTabela);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(230, 230, 235)));
        scroll.setBounds(30, 215, 930, 365);
        add(scroll);

        // --- BOTÃO INFERIOR ---
     // --- BOTÕES INFERIORES ---
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setForeground(new Color(100, 100, 100));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setBounds(30, 600, 180, 40);
        add(btnAtualizar);

        // ==== NOVO BOTÃO DE EXPORTAR ====
        JButton btnExportar = new JButton("📥 Exportar p/ Excel");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setForeground(new Color(39, 174, 96)); // Verde Excel
        btnExportar.setBackground(Color.WHITE);
        btnExportar.setBorder(new LineBorder(new Color(39, 174, 96), 1));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setBounds(220, 600, 180, 40);
        add(btnExportar);

        // Lógica do botão de exportação
        btnExportar.addActionListener(e -> {
            if (tabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "A tabela está vazia. Não há dados para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            br.com.condosys.util.ExportarUtil.exportarParaExcel(tabela, this);
        });

        // --- LÓGICA E EVENTOS ---
        listaTodasUnidades = new UnidadeDAO().listarTodasUnidades();
        filtrarComboUnidades(); 
        
        campoBuscaApto.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrarComboUnidades(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrarComboUnidades(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrarComboUnidades(); }
        });

        campoBuscaTabela.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrarTabela(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrarTabela(); }
            private void filtrarTabela() {
                String texto = campoBuscaTabela.getText();
                if (texto.trim().length() == 0) { classificadorTabela.setRowFilter(null); } 
                else { classificadorTabela.setRowFilter(RowFilter.regexFilter("(?i)" + texto)); }
            }
        });

        btnSalvar.addActionListener(e -> {
            String area = comboAreaComum.getSelectedItem().toString();
            String data = campoData.getText().replace("_", "").replace("/", "").trim();
            Unidade destino = (Unidade) comboUnidades.getSelectedItem();

            if (data.length() < 8 || destino == null) {
                JOptionPane.showMessageDialog(this, "Preencha a Data corretamente e selecione o Apartamento!");
                return;
            }

            try {
                // Pega o texto da data de volta com as barras
                Reserva novaReserva = new Reserva(destino, area, campoData.getText());
                new ReservaDAO().salvar(novaReserva); // Aqui ele pode lançar o erro de "Já reservado"
                
                JOptionPane.showMessageDialog(this, "Reserva Concluída com Sucesso!");
                campoData.setValue(null); 
                campoBuscaApto.setText("");
                carregarDados();
            } catch (Exception ex) {
                // Mostra a mensagem exata do erro na tela (ex: "Área já reservada nesta data!")
                JOptionPane.showMessageDialog(this, "❌ Não foi possível agendar:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            carregarDados();
            listaTodasUnidades = new UnidadeDAO().listarTodasUnidades();
            filtrarComboUnidades();
        });

        carregarDados();
    }
    
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Object[]> dados = new ReservaDAO().listarDadosTabela();
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
    }

    private void filtrarComboUnidades() {
        String termo = campoBuscaApto.getText().toLowerCase();
        comboUnidades.removeAllItems();
        for (Unidade u : listaTodasUnidades) {
            if (u.toString().toLowerCase().contains(termo)) {
                comboUnidades.addItem(u);
            }
        }
    }
}