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
    private static final Set<PrintWriter> clientes = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Servidor de chat iniciado na porta " + PORTA);

        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Novo cliente conectado: " + cliente.getInetAddress());
                new Thread(new ClienteHandler(cliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }

    private static class ClienteHandler implements Runnable {
        private final Socket socket;
        private PrintWriter writer;

        public ClienteHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                Scanner reader = new Scanner(socket.getInputStream());
            ) {
                writer = new PrintWriter(socket.getOutputStream(), true);
                clientes.add(writer);

                while (reader.hasNextLine()) {
                    String mensagem = reader.nextLine();
                    System.out.println("Recebido: " + mensagem);
                    broadcast(mensagem);
                }
            } catch (IOException e) {
                System.err.println("Erro com cliente: " + e.getMessage());
            } finally {
                if (writer != null) {
                    clientes.remove(writer);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar socket: " + e.getMessage());
                }
            }
        }

        private void broadcast(String mensagem) {
            synchronized (clientes) {
                for (PrintWriter cliente : clientes) {
                    cliente.println(mensagem);
                }
            }
        }
    }
}
