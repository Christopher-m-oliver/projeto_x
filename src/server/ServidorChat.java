package server;

/**
 *
 * @author reduz
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorChat {
    private static final int PORTA = 12345;
    private static final List<PrintWriter> clientes = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado na porta " + PORTA);

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Novo cliente conectado: " + cliente.getInetAddress());

                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                clientes.add(out);

                Thread t = new Thread(() -> tratarCliente(cliente, out));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static void tratarCliente(Socket cliente, PrintWriter out) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()))) {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Recebido: " + msg);
                enviarParaTodos("Cliente diz: " + msg);
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        } finally {
            clientes.remove(out);
            try {
                cliente.close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    private static void enviarParaTodos(String mensagem) {
        synchronized (clientes) {
            for (PrintWriter escritor : clientes) {
                escritor.println(mensagem);
            }
        }
    }
}