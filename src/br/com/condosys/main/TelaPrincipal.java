package br.com.condosys.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        // 1. Configurações da Janela Principal
        setTitle("CondoSys - Sistema de Gestão Condominial");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 2. Título de Boas-vindas
        JLabel labelTitulo = new JLabel("Painel Administrativo CondoSys");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setBounds(100, 30, 300, 30);
        add(labelTitulo);

        // 3. Botão: Gerenciar Encomendas (O que você vai testar agora)
        JButton btnEncomendas = new JButton("📦 Gerenciar Encomendas");
        btnEncomendas.setBounds(125, 100, 250, 50);
        add(btnEncomendas);

        // 4. Botão: Outros Módulos (Exemplo para o futuro)
        JButton btnUsuarios = new JButton("👤 Gerenciar Usuários");
        btnUsuarios.setBounds(125, 170, 250, 50);
        add(btnUsuarios);

        // =========================================================
        // LÓGICA PARA ABRIR A TELA DE ENCOMENDAS
        // =========================================================
        btnEncomendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Instancia a tela real de encomendas que criamos
                TelaGerenciarEncomendas tela = new TelaGerenciarEncomendas();
                // Torna a tela visível
                tela.setVisible(true);
            }
        });

        // Lógica provisória para o botão de usuários
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Módulo de Usuários será implementado em breve!");
            }
        });
    }

    // Método principal para iniciar o sistema
    public static void main(String[] args) {
        // Garante que a interface seja iniciada corretamente na Thread do Swing
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal principal = new TelaPrincipal();
            principal.setVisible(true);
        });
    }
}