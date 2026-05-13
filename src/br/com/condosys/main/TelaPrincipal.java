package br.com.condosys.main;

import br.com.condosys.dao.DashboardDAO;
import br.com.condosys.util.EstiloUtil;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class TelaPrincipal extends JFrame {

    private DashboardDAO dashDAO;

    // Rótulos que vão receber os números do banco
    private JLabel lblNumUnidades, lblNumVisitantes, lblNumReservas, lblNumEncomendas;

    public TelaPrincipal() {
        dashDAO = new DashboardDAO();

        setTitle("CondoSys - Dashboard Interativo");
        setSize(1000, 750); // Aumentei um pouco a altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); 
        setLocationRelativeTo(null);
        setLayout(null);

        // --- CABEÇALHO ---
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(EstiloUtil.COR_PRIMARIA);
        painelTopo.setBounds(0, 0, 1000, 80);
        painelTopo.setLayout(null);
        add(painelTopo);

        JLabel lblTitulo = new JLabel("CondoSys - Painel de Controle");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setBounds(30, 20, 500, 40);
        painelTopo.add(lblTitulo);

        // Botão discreto para atualizar os números do painel
        JButton btnAtualizarDash = new JButton("🔄 Atualizar Painel");
        btnAtualizarDash.setBounds(800, 25, 150, 30);
        btnAtualizarDash.setFocusPainted(false);
        btnAtualizarDash.setCursor(new Cursor(Cursor.HAND_CURSOR));
        painelTopo.add(btnAtualizarDash);

        // ==========================================
        // CARDS DE ESTATÍSTICAS (NOVIDADE!)
        // ==========================================
        
        // Card 1: Unidades
        JPanel card1 = criarCard(40, 110, "🏢 Unidades", new Color(52, 152, 219)); // Azul
        lblNumUnidades = criarLabelNumero(card1);
        add(card1);

        // Card 2: Visitantes
        JPanel card2 = criarCard(270, 110, "🛡️ Visitantes Ativos", new Color(231, 76, 60)); // Vermelho
        lblNumVisitantes = criarLabelNumero(card2);
        add(card2);

        // Card 3: Reservas Hoje
        JPanel card3 = criarCard(500, 110, "📅 Reservas Hoje", new Color(46, 204, 113)); // Verde
        lblNumReservas = criarLabelNumero(card3);
        add(card3);

        // Card 4: Encomendas
        JPanel card4 = criarCard(730, 110, "📦 Encomendas P.", new Color(241, 196, 15)); // Amarelo
        lblNumEncomendas = criarLabelNumero(card4);
        add(card4);

        // Preenche os números iniciais
        atualizarEstatisticas();

        // ==========================================
        // BOTÕES DO DASHBOARD (GRADE 2x2)
        // ==========================================
        
        int btnWidth = 300;
        int btnHeight = 100;
        
        JButton btnEncomendas = criarBotaoMenu("📦 ENCOMENDAS", 180, 250, btnWidth, btnHeight);
        add(btnEncomendas);

        JButton btnVisitantes = criarBotaoMenu("🛡️ VISITANTES", 500, 250, btnWidth, btnHeight);
        add(btnVisitantes);

        JButton btnUnidades = criarBotaoMenu("🏢 UNIDADES", 180, 370, btnWidth, btnHeight);
        add(btnUnidades);

        JButton btnReservas = criarBotaoMenu("📅 RESERVAS", 500, 370, btnWidth, btnHeight);
        add(btnReservas);

        // --- Botão SAIR ---
        JButton btnSair = new JButton("❌ SAIR DO SISTEMA");
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSair.setForeground(Color.WHITE); 
        btnSair.setBackground(new Color(231, 76, 60)); // Fundo vermelho
        btnSair.setBorderPainted(false);
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setBounds(340, 520, 300, 50);
        add(btnSair);

        // ==========================================
        // LÓGICA DE NAVEGAÇÃO E EVENTOS
        // ==========================================
        btnAtualizarDash.addActionListener(e -> atualizarEstatisticas());
        btnEncomendas.addActionListener(e -> new TelaGerenciarEncomendas().setVisible(true));
        btnVisitantes.addActionListener(e -> new TelaGerenciarVisitantes().setVisible(true));
        btnUnidades.addActionListener(e -> new TelaGerenciarUnidades().setVisible(true));
        btnReservas.addActionListener(e -> new TelaGerenciarReservas().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE DESIGN
    // ==========================================
    
    // Método que desenha os cartões coloridos no topo
    private JPanel criarCard(int x, int y, String titulo, Color corBorda) {
        JPanel painel = new JPanel();
        painel.setBounds(x, y, 210, 100);
        painel.setBackground(Color.WHITE);
        painel.setBorder(new LineBorder(corBorda, 3, true));
        painel.setLayout(null);

        JLabel lblT = new JLabel(titulo, SwingConstants.CENTER);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblT.setForeground(new Color(100, 100, 100));
        lblT.setBounds(0, 10, 210, 20);
        painel.add(lblT);

        return painel;
    }

    // Método que cria o número gigante dentro do cartão
    private JLabel criarLabelNumero(JPanel painelPai) {
        JLabel lblNum = new JLabel("0", SwingConstants.CENTER);
        lblNum.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblNum.setForeground(EstiloUtil.COR_PRIMARIA);
        lblNum.setBounds(0, 40, 210, 40);
        painelPai.add(lblNum);
        return lblNum;
    }

    // Método que padroniza a criação dos botões do menu central
    private JButton criarBotaoMenu(String texto, int x, int y, int w, int h) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(EstiloUtil.COR_PRIMARIA); 
        btn.setBackground(Color.WHITE); 
        btn.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2)); 
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        btn.setBounds(x, y, w, h);
        return btn;
    }

    // Busca os dados no DAO e atualiza os textos na tela
    private void atualizarEstatisticas() {
        lblNumUnidades.setText(String.valueOf(dashDAO.getTotalUnidades()));
        lblNumVisitantes.setText(String.valueOf(dashDAO.getVisitantesAtivos()));
        lblNumReservas.setText(String.valueOf(dashDAO.getReservasHoje()));
        lblNumEncomendas.setText(String.valueOf(dashDAO.getEncomendasPendentes()));
    }

    public static void main(String[] args) {
        EstiloUtil.aplicarLookAndFeel();
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}