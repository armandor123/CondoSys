package br.com.condosys.main;

import br.com.condosys.dao.UsuarioDAO;
import br.com.condosys.model.Usuario;
import br.com.condosys.util.EstiloUtil;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField campoEmail; // <-- Agora é email
    private JPasswordField campoSenha;

    public TelaLogin() {
        setTitle("CondoSys - Acesso Restrito");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- CABEÇALHO ROXO ---
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(EstiloUtil.COR_PRIMARIA);
        painelTopo.setBounds(0, 0, 400, 100);
        painelTopo.setLayout(null);
        add(painelTopo);

        JLabel lblLogo = new JLabel("CondoSys", SwingConstants.CENTER);
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setBounds(0, 30, 400, 40);
        painelTopo.add(lblLogo);

        // --- CAMPOS ---
        JLabel lblLogin = new JLabel("E-MAIL DE ACESSO"); // <-- Mudou o texto
        lblLogin.setFont(EstiloUtil.FONTE_LABEL);
        lblLogin.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblLogin.setBounds(50, 130, 300, 25);
        add(lblLogin);

        campoEmail = new JTextField();
        campoEmail.setFont(EstiloUtil.FONTE_INPUT);
        campoEmail.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoEmail.setBounds(50, 160, 300, 40);
        add(campoEmail);

        JLabel lblSenha = new JLabel("SENHA");
        lblSenha.setFont(EstiloUtil.FONTE_LABEL);
        lblSenha.setForeground(EstiloUtil.COR_TEXTO_SUAVE);
        lblSenha.setBounds(50, 220, 300, 25);
        add(lblSenha);

        campoSenha = new JPasswordField();
        campoSenha.setFont(EstiloUtil.FONTE_INPUT);
        campoSenha.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 1));
        campoSenha.setBounds(50, 250, 300, 40);
        add(campoSenha);

        // --- BOTÃO ENTRAR ---
        JButton btnEntrar = new JButton("ENTRAR NO SISTEMA");
        btnEntrar.setFont(EstiloUtil.FONTE_BOTAO);
        btnEntrar.setForeground(EstiloUtil.COR_PRIMARIA);
        btnEntrar.setBackground(Color.WHITE);
        btnEntrar.setBorder(new LineBorder(EstiloUtil.COR_PRIMARIA, 2));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setBounds(50, 340, 300, 50);
        add(btnEntrar);

        // LÓGICA DE LOGIN
        btnEntrar.addActionListener(e -> {
            String email = campoEmail.getText();
            String senha = new String(campoSenha.getPassword());

            // Envia o E-MAIL no lugar do login antigo
            Usuario user = new UsuarioDAO().autenticar(email, senha);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + user.getNome() + "!");
                new TelaPrincipal().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Acesso Negado! E-mail ou senha incorretos, ou conta desativada.", "Erro de Segurança", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        EstiloUtil.aplicarLookAndFeel();
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}