package br.com.condosys.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// A palavra "extends JFrame" diz ao Java que esta classe É UMA JANELA
public class TelaLogin extends JFrame {

    // Componentes que vão aparecer na tela
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    // Construtor: Aqui nós "desenhamos" a tela
    public TelaLogin() {
        // 1. Configurações básicas da Janela
        setTitle("CondoSys - Controle de Acesso"); // Título lá em cima
        setSize(350, 200); // Largura e Altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fechar o app no 'X'
        setLocationRelativeTo(null); // Faz a janela abrir bem no meio do monitor!
        setLayout(null); // Permite posicionar os itens manualmente usando coordenadas

        // 2. Criando o texto e a caixinha do E-MAIL
        JLabel labelEmail = new JLabel("E-mail:");
        labelEmail.setBounds(30, 30, 80, 25); // (x, y, largura, altura)
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(100, 30, 200, 25);
        add(campoEmail);

        // 3. Criando o texto e a caixinha de SENHA (que esconde os caracteres)
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

        // 5. Dando vida ao botão! (O que acontece quando clica)
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pegamos o que o usuário digitou
                String emailDigitado = campoEmail.getText();
                String senhaDigitada = new String(campoSenha.getPassword());

                // Simulação simples de Autenticação para testar a tela
                // Numa etapa futura, ligaremos isso à sua classe Usuario.java!
                if (emailDigitado.equals("admin") && senhaDigitada.equals("123")) {
                    // MENSAGEM DE SUCESSO
                    JOptionPane.showMessageDialog(null, "Acesso Permitido! Entrando no sistema...");
                    
                    // ABRE A TELA PRINCIPAL
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    
                    // FECHA A TELA DE LOGIN PARA NÃO FICAR ACUMULANDO JANELAS
                    dispose(); 
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Acesso Negado. E-mail ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // O "Motor de Partida" do Java! Todo sistema começa por aqui.
    public static void main(String[] args) {
        // Criamos o objeto da nossa tela e mandamos o Java mostrá-lo (setVisible)
        TelaLogin minhaTela = new TelaLogin();
        minhaTela.setVisible(true);
    }
}