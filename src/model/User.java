package model;

/**
 *
 * @author reduz
 */
public class User {
    private String email;
    private String senha;

    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
