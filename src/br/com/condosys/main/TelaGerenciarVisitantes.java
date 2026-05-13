package br.com.condosys.main;

import br.com.condosys.dao.UnidadeDAO;
import br.com.condosys.dao.VisitanteDAO;
import br.com.condosys.model.Unidade;
import br.com.condosys.model.Visitante;
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

public class TelaGerenciarVisitantes extends JFrame {

    private JTextField campoNome, campoBuscaApto, campoBuscaTabela;
    private JFormattedTextField campoDocumento; 
    private JComboBox<Unidade> comboUnidades;
    
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> classificadorTabela;
    
    private List<Unidade> listaTodasUnidades;

    public TelaGerenciarVisitantes() {
        setTitle("CondoSys - Controle de Visitantes");
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

        JLabel lblTitulo = new JLabel("🛡️ Controle de Acesso - Visitantes e Prestadores");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(EstiloUtil.FONTE_TITULO);
        lblTitulo.setBounds(25, 15, 600, 35);
        painelTopo.add(lblTitulo); // <--- CORRIGIDO AQUI! ✅

        // --- FORMULÁRIO ---
        JLabel lblNome = new JLabel("NOME DO VISITANTE");
        lblNome.setFont(EstiloUtil.FONTE_LABEL);
        lblNome.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblNome.setBounds(30, 80, 200, 25);
        add(lblNome);

        campoNome = new JTextField();
        campoNome.setFont(EstiloUtil.FONTE_INPUT);
        campoNome.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoNome.setBounds(30, 110, 220, 40);
        add(campoNome);

        JLabel lblDoc = new JLabel("CPF DO VISITANTE");
        lblDoc.setFont(EstiloUtil.FONTE_LABEL);
        lblDoc.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblDoc.setBounds(270, 80, 150, 25);
        add(lblDoc);

        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_'); 
            campoDocumento = new JFormattedTextField(mascaraCpf);
        } catch (Exception ex) {
            campoDocumento = new JFormattedTextField();
        }
        
        campoDocumento.setFont(EstiloUtil.FONTE_INPUT);
        campoDocumento.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoDocumento.setBounds(270, 110, 150, 40);
        add(campoDocumento);

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

        JButton btnEntrada = new JButton("REGISTRAR ENTRADA");
        btnEntrada.setFont(EstiloUtil.FONTE_BOTAO);
        btnEntrada.setForeground(EstiloUtil.COR_PRIMARIA);
        btnEntrada.setBackground(Color.WHITE);
        btnEntrada.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2));
        btnEntrada.setFocusPainted(false);
        btnEntrada.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrada.setBounds(780, 105, 180, 45);
        add(btnEntrada);

        // --- PESQUISA NA TABELA ---
        JLabel lblBuscaTab = new JLabel("🔍 Filtrar Visitantes:");
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
            "ID", "Visitante", "CPF", "Destino", "Entrada", "Saída"
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

        // --- BOTÕES INFERIORES ---
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setForeground(new Color(100, 100, 100));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        btnAtualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAtualizar.setBounds(30, 600, 180, 40);
        add(btnAtualizar);

        JButton btnSaida = new JButton("🚪 REGISTRAR SAÍDA");
        btnSaida.setFont(EstiloUtil.FONTE_BOTAO);
        btnSaida.setForeground(new Color(230, 126, 34)); 
        btnSaida.setBackground(Color.WHITE);
        btnSaida.setBorder(new LineBorder(new Color(230, 126, 34), 2));
        btnSaida.setFocusPainted(false);
        btnSaida.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSaida.setBounds(730, 600, 230, 40);
        add(btnSaida);

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

        btnEntrada.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            String documento = campoDocumento.getText().replace("_", "").trim();
            Unidade destino = (Unidade) comboUnidades.getSelectedItem();

            if (nome.isEmpty() || documento.length() < 14 || destino == null) {
                JOptionPane.showMessageDialog(this, "Preencha o Nome, o CPF completo e selecione o Apartamento!");
                return;
            }

            try {
                Visitante novoVis = new Visitante(nome.toUpperCase(), documento, destino);
                new VisitanteDAO().registrarEntrada(novoVis);
                
                JOptionPane.showMessageDialog(this, "Entrada Registrada com Sucesso!");
                campoNome.setText("");
                campoDocumento.setValue(null); 
                campoBuscaApto.setText("");
                carregarDados();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao registrar entrada.");
            }
        });

        btnSaida.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um visitante na tabela primeiro!");
                return;
            }
            
            String idVisitante = tabela.getValueAt(linha, 0).toString();
            String statusSaida = tabela.getValueAt(linha, 5).toString();

            if (!statusSaida.equals("AINDA NO CONDOMÍNIO")) {
                JOptionPane.showMessageDialog(this, "Este visitante já registrou saída anteriormente!");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Confirmar saída do visitante?", "Registrar Saída", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                new VisitanteDAO().registrarSaida(idVisitante);
                JOptionPane.showMessageDialog(this, "Saída Registrada!");
                carregarDados();
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
        List<Object[]> dados = new VisitanteDAO().listarDadosTabela();
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