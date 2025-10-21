package controller;

import model.User;
import java.io.*;
/**
 *
 * @author reduz
 */
public class UserControl {
    private static final String ARQUIVO = "usuarios.txt";

    public static boolean emailValido(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static void registrarUsuario(User usuario) throws IOException {
        if (!emailValido(usuario.getEmail())) {
            throw new IllegalArgumentException("Email inválido. Use o formato xx@xx.com");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            bw.write(usuario.getEmail() + ";" + usuario.getSenha());
            bw.newLine();
        }
    }

    public static boolean autenticarUsuario(String email, String senha) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes[0].equals(email) && partes[1].equals(senha)) {
                    return true;
                }
            }
        }
        return false;
    }
}