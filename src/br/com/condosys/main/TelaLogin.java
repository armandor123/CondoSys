package br.com.condosys.main;

import br.com.condosys.model.Administrador;
import java.time.LocalDate;

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
                // 1. Pegamos o que o usuário digitou na tela
                String emailDigitado = campoEmail.getText();
                String senhaDigitada = new String(campoSenha.getPassword());

                // 2. CRIANDO NOSSO BANCO DE DADOS FALSO (Mock)
                // Como ainda não temos banco de dados, vamos instanciar um Administrador de teste aqui
                Administrador adminTeste = new Administrador(
                        "Armadndo Rodrigues",                  // Nome
                        "467.417.068.01",           // Documento
                        "11959169338",              // Telefone
                        "admin@condosys.com",       // E-mail de login
                        "N15k35mjyam0",                 // Senha de login
                        LocalDate.now()             // Data de contratação
                );

                // 3. A MÁGICA DA ORIENTAÇÃO A OBJETOS!
                // Em vez de comparar strings soltas, pedimos para o OBJETO se autenticar
                if (adminTeste.autenticar(emailDigitado, senhaDigitada)) {
                    
                    // Se o método retornar TRUE, o login deu certo!
                    JOptionPane.showMessageDialog(null, "Acesso Permitido! Entrando no sistema...");
                    
                    // Abre a tela principal
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    
                    // Fecha a janela de login
                    dispose(); 
                    
                } else {
                    // Se o método retornar FALSE, barramos a entrada
                    JOptionPane.showMessageDialog(null, "Acesso Negado. E-mail ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
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