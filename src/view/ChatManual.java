package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.net.Socket;
import javax.swing.*;

public class ChatManual extends JPanel {
    private final JPanel painelMensagens;
    private final JScrollPane scroll;
    private final JTextField campoMensagem;
    private final JButton btnEnviar;
    private final Socket socket;
    private final java.io.PrintWriter writer;
    private final String nomeUsuario;


    public ChatManual(Socket socket, String nomeUsuario) {
        this.socket = socket;
        this.nomeUsuario = nomeUsuario;
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        painelMensagens = new JPanel();
        painelMensagens.setLayout(new BoxLayout(painelMensagens, BoxLayout.Y_AXIS));
        painelMensagens.setBackground(new Color(30, 30, 30));

        scroll = new JScrollPane(painelMensagens);
        scroll.setBorder(null);
        scroll.setBackground(new Color(30, 30, 30));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        campoMensagem = new JTextField();
        campoMensagem.setFont(new Font("Consolas", Font.PLAIN, 16));
        campoMensagem.setBackground(new Color(50, 50, 50));
        campoMensagem.setForeground(Color.WHITE);
        campoMensagem.setCaretColor(Color.WHITE);
        campoMensagem.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 85)));

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(new Color(0, 255, 85));
        btnEnviar.setForeground(Color.BLACK);
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setBorderPainted(false);
        try {
            writer = new java.io.PrintWriter(socket.getOutputStream(), true);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao criar writer para o socket", e);
        }
        btnEnviar.addActionListener(e -> {
        String mensagem = campoMensagem.getText().trim();
        if (!mensagem.isEmpty()) {
            writer.println(mensagem); // envia pelo socket
            adicionarMensagem("Você", mensagem, new Color(0, 255, 85)); // exibe localmente
            campoMensagem.setText("");
    }
});

        campoMensagem.addActionListener(e -> btnEnviar.doClick());


        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setBackground(new Color(30, 30, 30));
        painelInferior.add(campoMensagem, BorderLayout.CENTER);
        painelInferior.add(btnEnviar, BorderLayout.EAST);

        add(scroll, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

    }

    private void adicionarMensagem(String usuario, String texto, Color corUsuario) {
        JPanel linha = new JPanel();
        linha.setLayout(new BoxLayout(linha, BoxLayout.Y_AXIS));
        linha.setBackground(new Color(30, 30, 30));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        String hora = java.time.LocalTime.now().withNano(0).toString();
        JLabel cabecalho = new JLabel(usuario + " — " + hora);
        cabecalho.setFont(new Font("Consolas", Font.BOLD, 14));
        cabecalho.setForeground(corUsuario);

        JLabel corpo = new JLabel("> " + texto);
        corpo.setFont(new Font("Consolas", Font.PLAIN, 16));
        corpo.setForeground(Color.WHITE);

        linha.add(cabecalho);
        linha.add(corpo);
        linha.add(Box.createVerticalStrut(8));

        painelMensagens.add(linha);
        painelMensagens.revalidate();
        painelMensagens.repaint();

        SwingUtilities.invokeLater(() ->
            scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum())
        );
    }
}
