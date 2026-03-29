package br.com.condosys.main;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        // 1. Configurações da Janela Principal
        setTitle("CondoSys - Dashboard Principal");
        setSize(500, 350); // Janela um pouco maior
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar tudo ao clicar no X
        setLocationRelativeTo(null); // Centralizar na tela
        setLayout(null); // Posições manuais

        // 2. Mensagem de Boas-Vindas
        JLabel labelBemVindo = new JLabel("Bem-vindo ao sistema do Morada Nova!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 16)); // Deixando a letra maior
        labelBemVindo.setBounds(90, 20, 350, 30);
        add(labelBemVindo);

        // 3. Botão: Registrar Acesso (Visitantes)
        JButton btnAcesso = new JButton("Registrar Acesso");
        btnAcesso.setBounds(150, 80, 200, 40);
        add(btnAcesso);

        // 4. Botão: Gerenciar Encomendas
        JButton btnEncomenda = new JButton("Gerenciar Encomendas");
        btnEncomenda.setBounds(150, 140, 200, 40);
        add(btnEncomenda);

        // 5. Botão: Sair do Sistema
        JButton btnSair = new JButton("Sair (Logout)");
        btnSair.setBounds(150, 200, 200, 40);
        add(btnSair);

        // --- AÇÕES DOS BOTÕES ---

        // Ação do Botão Acesso (Como a tela não existe ainda, mostramos um aviso)
        btnAcesso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Módulo de Acessos em desenvolvimento!");
            }
        });

        // Ação do Botão Encomenda
        btnEncomenda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Módulo de Encomendas em desenvolvimento!");
            }
        });

        // Ação do Botão Sair (Fecha a tela principal e abre o login de novo)
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha esta janela
                TelaLogin telaLogin = new TelaLogin(); // Cria a janela de login
                telaLogin.setVisible(true); // Mostra o login
            }
        });
    }
}
