package br.com.condosys.main;

import br.com.condosys.dao.UsuarioDAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    // Componentes que vão aparecer na tela
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public TelaLogin() {
        // 1. Configurações básicas da Janela
        setTitle("CondoSys - Controle de Acesso"); 
        setSize(350, 200); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); // Abre no centro da tela
        setLayout(null); 

        // 2. Criando o texto e a caixinha do E-MAIL
        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(30, 30, 80, 25);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(100, 30, 200, 25);
        add(campoEmail);

        // 3. Criando o texto e a caixinha de SENHA
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(30, 70, 80, 25);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(100, 70, 200, 25);
        add(campoSenha);

        // 4. Criando o BOTÃO de Entrar
        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(120, 110, 100, 30);
        add(botaoEntrar);

        // 5. EVENTO DO BOTÃO (Comunicação 100% real com o Banco de Dados)
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pega os dados digitados
                String emailDigitado = campoEmail.getText();
                String senhaDigitada = new String(campoSenha.getPassword());

                // Instancia o motor do banco de dados
                UsuarioDAO dao = new UsuarioDAO();

                // Pede para o DAO ir no banco conferir as credenciais
                boolean acessoPermitido = dao.autenticar(emailDigitado, senhaDigitada);

                // Toma a decisão baseada na resposta do banco
                if (acessoPermitido) {
                    JOptionPane.showMessageDialog(null, "Acesso Permitido! Seja bem-vindo ao CondoSys.");
                    
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    
                    dispose(); // Fecha a janela de login
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso Negado. E-mail ou senha não encontrados no banco de dados.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // O "Motor de Partida" do Java
    public static void main(String[] args) {
        TelaLogin minhaTela = new TelaLogin();
        minhaTela.setVisible(true);
    }
}