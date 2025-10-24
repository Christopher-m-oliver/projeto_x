package view;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConexaoManual extends JPanel {

    private final Main main;
    private JLabel lblTitulo;
    private JLabel lblIP;
    private JLabel lblPorta;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JButton btnConectar;

    public ConexaoManual(Main main) {
        this.main = main;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(new Color(30, 30, 30));

        lblTitulo = new JLabel("Conexão ao Servidor");
        lblTitulo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        lblTitulo.setForeground(new Color(0, 255, 85));
        lblTitulo.setBounds(200, 30, 300, 30);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        lblIP = new JLabel("IP do servidor:");
        lblIP.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblIP.setForeground(Color.WHITE);
        lblIP.setBounds(100, 90, 200, 25);
        add(lblIP);

        txtIP = new JTextField();
        txtIP.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        txtIP.setBackground(new Color(78, 80, 82));
        txtIP.setForeground(Color.WHITE);
        txtIP.setBounds(100, 120, 400, 35);
        add(txtIP);

        lblPorta = new JLabel("Porta:");
        lblPorta.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        lblPorta.setForeground(Color.WHITE);
        lblPorta.setBounds(100, 170, 200, 25);
        add(lblPorta);

        txtPorta = new JTextField();
        txtPorta.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        txtPorta.setBackground(new Color(78, 80, 82));
        txtPorta.setForeground(Color.WHITE);
        txtPorta.setBounds(100, 200, 400, 35);
        add(txtPorta);

        btnConectar = new JButton("Entrar");
        btnConectar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
        btnConectar.setBackground(new Color(0, 255, 85));
        btnConectar.setBounds(100, 260, 400, 50);
        btnConectar.setBorderPainted(false);
        add(btnConectar);

        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectar();
            }
        });
    }

    private void conectar() {
        String ip = txtIP.getText().trim();
        String portaStr = txtPorta.getText().trim();
        String nomeUsuario = main.getNomeUsuario();

        if (ip.isEmpty()) ip = "localhost";
        if (portaStr.isEmpty()) portaStr = "12345";

        try {
            int porta = Integer.parseInt(portaStr);
            Socket socket = new Socket(ip, porta);
            JOptionPane.showMessageDialog(this, "Conectado com sucesso!");
            main.abrirChat(socket, nomeUsuario);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Porta inválida.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + e.getMessage());
        }
    }
}