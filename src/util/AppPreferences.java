package util;

/**
 *
 * @author Christopher
 */
import java.util.prefs.Preferences;

public class AppPreferences {
    private static final Preferences prefs = Preferences.userNodeForPackage(AppPreferences.class);
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_REMEMBER_ME = "remember_me";

    public static void saveUserEmail(String email, boolean rememberMe) {
        prefs.putBoolean(KEY_REMEMBER_ME, rememberMe);
        if (rememberMe) {
            prefs.put(KEY_USER_EMAIL, email);
        } else {
            prefs.remove(KEY_USER_EMAIL); // Limpa o email se desmarcado
        }
    }

    public static String getSavedEmail() {
        // Retorna o email apenas se a opção "lembrar de mim" estiver marcada
        if (prefs.getBoolean(KEY_REMEMBER_ME, false)) {
            return prefs.get(KEY_USER_EMAIL, "");
        }
        return "";
    }
    
    public static boolean getRememberMeStatus() {
        return prefs.getBoolean(KEY_REMEMBER_ME, false);
    }
}

