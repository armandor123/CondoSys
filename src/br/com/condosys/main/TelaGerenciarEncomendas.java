package br.com.condosys.main;

import br.com.condosys.dao.EncomendaDAO;
import br.com.condosys.dao.UnidadeDAO;
import br.com.condosys.util.EstiloUtil;
import br.com.condosys.model.Unidade;
import br.com.condosys.model.Encomenda;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

public class TelaGerenciarEncomendas extends JFrame {

    private JTextField campoDescricao;
    private JTextField campoBuscaUnidade; // <--- O NOVO CAMPO DE BUSCA RÁPIDA
    private JComboBox<Unidade> comboUnidades;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    // Guarda todas as unidades na memória para o filtro ser ultrarrápido
    private List<Unidade> listaTodasUnidades; 

    public TelaGerenciarEncomendas() {
        setTitle("CondoSys - Gestão de Encomendas");
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

        JLabel lblTitulo = new JLabel("📦 Gerenciamento de Encomendas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(EstiloUtil.FONTE_TITULO);
        lblTitulo.setBounds(25, 15, 500, 35);
        painelTopo.add(lblTitulo);

        // --- FORMULÁRIO ---
        JLabel lblDesc = new JLabel("REMETENTE / DESCRIÇÃO");
        lblDesc.setFont(EstiloUtil.FONTE_LABEL);
        lblDesc.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblDesc.setBounds(30, 90, 200, 25);
        add(lblDesc);
        
        campoDescricao = new JTextField();
        campoDescricao.setFont(EstiloUtil.FONTE_INPUT);
        campoDescricao.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoDescricao.setBounds(30, 120, 250, 40);
        add(campoDescricao);

        // ==================================================
        // A MÁGICA DA BUSCA INTELIGENTE COMEÇA AQUI
        // ==================================================
        JLabel lblBusca = new JLabel("🔍 BUSCAR APTO");
        lblBusca.setFont(EstiloUtil.FONTE_LABEL);
        lblBusca.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblBusca.setBounds(300, 90, 120, 25);
        add(lblBusca);

        // 1. Campo pequeno para digitar só o número (ex: "104")
        campoBuscaUnidade = new JTextField();
        campoBuscaUnidade.setFont(EstiloUtil.FONTE_INPUT);
        campoBuscaUnidade.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoBuscaUnidade.setBounds(300, 120, 120, 40);
        add(campoBuscaUnidade);

        JLabel lblId = new JLabel("SELECIONE NA LISTA");
        lblId.setFont(EstiloUtil.FONTE_LABEL);
        lblId.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblId.setBounds(440, 90, 250, 25);
        add(lblId);
        
        // 2. A lista de seleção
        comboUnidades = new JComboBox<>();
        comboUnidades.setFont(EstiloUtil.FONTE_INPUT);
        comboUnidades.setBackground(Color.WHITE);
        comboUnidades.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        comboUnidades.setBounds(440, 120, 270, 40);
        add(comboUnidades);

        // Carrega todas as unidades do banco e preenche a lista
        listaTodasUnidades = new UnidadeDAO().listarTodasUnidades();
        filtrarComboUnidades(); 

        // 3. O "Ouvinte": Sempre que digitar algo no campo Busca, ele filtra o Combo
        campoBuscaUnidade.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrarComboUnidades(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrarComboUnidades(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrarComboUnidades(); }
        });
        // ==================================================

        // BOTÃO SALVAR
        JButton btnSalvar = new JButton("SALVAR");
        btnSalvar.setFont(EstiloUtil.FONTE_BOTAO);
        btnSalvar.setForeground(EstiloUtil.COR_PRIMARIA);
        btnSalvar.setBackground(Color.WHITE);
        btnSalvar.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setBounds(730, 115, 230, 45);
        add(btnSalvar);

        // --- TABELA ---
        modeloTabela = new DefaultTableModel(new Object[]{
            "ID", "Descrição", "STATUS", "ID Destino", "Chegada", "Retirada", "Quem Retirou"
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

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(230, 230, 235)));
        scroll.setBounds(30, 200, 930, 380);
        add(scroll);

        // BOTÕES INFERIORES
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAtualizar.setForeground(new Color(100, 100, 100));
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        btnAtualizar.setBounds(30, 600, 180, 40);
        add(btnAtualizar);

        JButton btnEntregar = new JButton("✅ MARCAR COMO ENTREGUE");
        btnEntregar.setFont(EstiloUtil.FONTE_BOTAO);
        btnEntregar.setForeground(new Color(46, 204, 113)); 
        btnEntregar.setBackground(Color.WHITE);
        btnEntregar.setBorder(new LineBorder(new Color(46, 204, 113), 2));
        btnEntregar.setFocusPainted(false);
        btnEntregar.setBounds(700, 600, 260, 40);
        add(btnEntregar);
        
     // ==== BOTÃO EXPORTAR (ENCOMENDAS) ====
        JButton btnExportar = new JButton("📥 Exportar p/ Excel");
        btnExportar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnExportar.setForeground(new Color(39, 174, 96)); 
        btnExportar.setBackground(Color.WHITE);
        btnExportar.setBorder(new LineBorder(new Color(39, 174, 96), 1));
        btnExportar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportar.setBounds(220, 600, 180, 40); // Fica ao lado do Atualizar
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
            try {
                Unidade unidadeSelecionada = (Unidade) comboUnidades.getSelectedItem();
                
                if(unidadeSelecionada == null) {
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma unidade válida na lista!");
                    return;
                }
                if(campoDescricao.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite o remetente/descrição!");
                    return;
                }

                Encomenda enc = new Encomenda(unidadeSelecionada, null, campoDescricao.getText());
                new EncomendaDAO().salvar(enc);
                
                JOptionPane.showMessageDialog(null, "Encomenda Registrada!");
                campoDescricao.setText(""); 
                campoBuscaUnidade.setText(""); // Limpa a busca para a próxima
                carregarDados();
                
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        // Eventos padrões
        btnAtualizar.addActionListener(e -> { 
            carregarDados(); 
            listaTodasUnidades = new UnidadeDAO().listarTodasUnidades();
            filtrarComboUnidades(); 
        });
        
        btnEntregar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if(linha == -1) { JOptionPane.showMessageDialog(null, "Selecione uma encomenda."); return; }
            String statusAtual = tabela.getValueAt(linha, 2).toString();
            if(statusAtual.equals("ENTREGUE")) { JOptionPane.showMessageDialog(null, "Já entregue."); return; }
            
            String id = tabela.getValueAt(linha, 0).toString();
            String nomeRetirada = JOptionPane.showInputDialog(this, "Nome de quem está retirando:");
            if(nomeRetirada != null && !nomeRetirada.trim().isEmpty()) {
                new EncomendaDAO().atualizarStatus(id, "ENTREGUE", nomeRetirada.toUpperCase());
                carregarDados();
            }
        });

        carregarDados();
    }

    // ==================================================
    // MÉTODOS AUXILIARES
    // ==================================================
    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Object[]> lista = new EncomendaDAO().listarDadosTabela();
        for (Object[] linha : lista) {
            modeloTabela.addRow(linha);
        }
    }

    // O "Motor" que filtra a caixa de seleção em tempo real
    private void filtrarComboUnidades() {
        String termoBusca = campoBuscaUnidade.getText().toLowerCase();
        
        // Remove tudo da lista
        comboUnidades.removeAllItems();
        
        // Adiciona apenas quem tem a palavra ou número digitado no meio do nome
        for (Unidade u : listaTodasUnidades) {
            if (u.toString().toLowerCase().contains(termoBusca)) {
                comboUnidades.addItem(u);
            }
        }
    }
}