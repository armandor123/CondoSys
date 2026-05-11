package br.com.condosys.main;

import br.com.condosys.model.Administrador;
import br.com.condosys.dao.UsuarioDAO; // IMPORTANTE: Importamos o seu novo DAO
import java.time.LocalDate;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public TelaLogin() {
        // 1. Configurações básicas da Janela
        setTitle("CondoSys - Controle de Acesso");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 2. E-MAIL
        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(30, 30, 80, 25);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(100, 30, 200, 25);
        add(campoEmail);

        // 3. SENHA
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(30, 70, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(100, 70, 200, 25);
        add(campoSenha);

        // 4. BOTÃO
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(120, 110, 100, 30);
        add(botaoEntrar);

        // 5. EVENTO DO BOTÃO
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailDigitado = campoEmail.getText();
                String senhaDigitada = new String(campoSenha.getPassword());

                // Criamos o objeto Administrador (como você já tinha feito)
                Administrador adminTeste = new Administrador(
                        "Armando Rodrigues",      
                        "467.417.068.01",           
                        "11959169338",              
                        "admin@condosys.com",       
                        "N15k35mjyam0",                 
                        LocalDate.now()             
                );

                // ============================================================
                // AQUI ESTÁ A MUDANÇA: Chamando o DAO para salvar no banco!
                // ============================================================
                UsuarioDAO dao = new UsuarioDAO();
                dao.salvarAdministrador(adminTeste);
                // ============================================================

                // Autenticação (como você já tinha feito)
                if (adminTeste.autenticar(emailDigitado, senhaDigitada)) {
                    JOptionPane.showMessageDialog(null, "Acesso Permitido! Dados salvos no Banco.");
                    
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso Negado. E-mail ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        TelaLogin minhaTela = new TelaLogin();
        minhaTela.setVisible(true);
    }
}