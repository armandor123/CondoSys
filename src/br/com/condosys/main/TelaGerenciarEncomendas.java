package br.com.condosys.main;

import br.com.condosys.dao.EncomendaDAO;
import br.com.condosys.util.EstiloUtil;
import br.com.condosys.model.Encomenda;
import br.com.condosys.model.Unidade;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Color;
import java.util.List;

public class TelaGerenciarEncomendas extends JFrame {

    private JTextField campoDescricao, campoIdMorador;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaGerenciarEncomendas() {
        setTitle("CondoSys - Gestão de Encomendas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Fundo Branco
        getContentPane().setBackground(EstiloUtil.COR_FUNDO);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- CABEÇALHO (ROXO GRADIENTE/SUAVE) ---
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
        campoDescricao.setBorder(new LineBorder(new Color(230, 230, 235), 2));
        campoDescricao.setBounds(30, 120, 320, 40);
        add(campoDescricao);

        JLabel lblId = new JLabel("ID DA UNIDADE");
        lblId.setFont(EstiloUtil.FONTE_LABEL);
        lblId.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblId.setBounds(380, 90, 200, 25);
        add(lblId);
        
        campoIdMorador = new JTextField();
        campoIdMorador.setFont(EstiloUtil.FONTE_INPUT);
        campoIdMorador.setBorder(new LineBorder(new Color(230, 230, 235), 2));
        campoIdMorador.setBounds(380, 120, 320, 40);
        add(campoIdMorador);

        JButton btnSalvar = new JButton("SALVAR ENCOMENDA");
        btnSalvar.setBackground(EstiloUtil.COR_PRIMARIA);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(EstiloUtil.FONTE_BOTAO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setBounds(730, 115, 230, 45);
        add(btnSalvar);

        // --- TABELA ---
        modeloTabela = new DefaultTableModel(new Object[]{
            "ID Encomenda", "Descrição", "STATUS", "ID Unidade", "Chegada", "Retirada", "Quem Retirou"
        }, 0);
        
        tabela = new JTable(modeloTabela); 
        tabela.setRowHeight(35);
        tabela.setFont(EstiloUtil.FONTE_INPUT);
        tabela.setGridColor(new Color(240, 240, 245));
        tabela.setSelectionBackground(EstiloUtil.COR_SECUNDARIA);
        tabela.setSelectionForeground(Color.WHITE);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(new Color(245, 245, 250)); // Fundo claro para o header
        header.setForeground(EstiloUtil.COR_TEXTO);
        header.setFont(EstiloUtil.FONTE_LABEL);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(new LineBorder(new Color(235, 235, 240)));
        scroll.setBounds(30, 200, 930, 380);
        add(scroll);

        // --- BOTÕES INFERIORES ---
        JButton btnAtualizar = new JButton("🔄 Atualizar Lista");
        btnAtualizar.setFont(EstiloUtil.FONTE_BOTAO);
        btnAtualizar.setBackground(Color.WHITE);
        btnAtualizar.setBounds(30, 600, 180, 40);
        add(btnAtualizar);

        JButton btnEntregar = new JButton("✅ MARCAR COMO ENTREGUE");
        btnEntregar.setBackground(new Color(76, 175, 80)); // Verde moderno
        btnEntregar.setForeground(Color.WHITE);
        btnEntregar.setFont(EstiloUtil.FONTE_BOTAO);
        btnEntregar.setFocusPainted(false);
        btnEntregar.setBorderPainted(false);
        btnEntregar.setBounds(730, 600, 230, 40);
        add(btnEntregar);

        // --- LÓGICA ---
        btnSalvar.addActionListener(e -> {
            try {
                Unidade u = new Unidade(campoIdMorador.getText());
                Encomenda enc = new Encomenda(u, null, campoDescricao.getText());
                new EncomendaDAO().salvar(enc);
                JOptionPane.showMessageDialog(null, "Encomenda Registrada!");
                campoDescricao.setText(""); campoIdMorador.setText("");
                carregarDados();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        btnAtualizar.addActionListener(e -> carregarDados());

        btnEntregar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if(linha == -1) {
                JOptionPane.showMessageDialog(null, "Selecione uma encomenda na tabela.");
                return; 
            }
            String statusAtual = tabela.getValueAt(linha, 2).toString();
            if(statusAtual.equals("ENTREGUE")) {
                JOptionPane.showMessageDialog(null, "Esta encomenda já foi entregue anteriormente.");
                return;
            }
            String id = tabela.getValueAt(linha, 0).toString();
            String nomeRetirada = JOptionPane.showInputDialog(this, "Nome de quem está retirando:");
            if(nomeRetirada != null && !nomeRetirada.trim().isEmpty()) {
                new EncomendaDAO().atualizarStatus(id, "ENTREGUE", nomeRetirada.toUpperCase());
                carregarDados();
            }
        });

        carregarDados();
    }

    private void carregarDados() {
        modeloTabela.setRowCount(0);
        List<Object[]> lista = new EncomendaDAO().listarDadosTabela();
        for (Object[] linha : lista) {
            modeloTabela.addRow(linha);
        }
    }
}