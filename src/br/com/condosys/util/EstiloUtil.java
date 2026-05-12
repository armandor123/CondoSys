package br.com.condosys.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.UIManager;

public class EstiloUtil {

    // --- PALETA LIGHT (Inspirada no Logo sobre fundo branco) ---
    public static final Color COR_FUNDO = new Color(255, 255, 255);    // Branco Puro
    public static final Color COR_PAINEL = new Color(248, 249, 252);   // Cinza muito claro para áreas de fundo
    public static final Color COR_PRIMARIA = new Color(138, 110, 189); // Roxo do Logo (Versão suave)
    public static final Color COR_SECUNDARIA = new Color(126, 175, 222); // Azul do Logo
    public static final Color COR_TEXTO = new Color(45, 45, 50);       // Texto quase preto
    public static final Color COR_TEXTO_SUAVE = new Color(120, 120, 130); // Texto secundário
    public static final Color COR_BRANCO = Color.WHITE;

    // --- FONTES ---
    public static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONTE_INPUT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 13);

    public static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}