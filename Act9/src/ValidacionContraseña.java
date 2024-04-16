import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ValidacionContraseña {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        while (true) {
            System.out.print("Ingrese una contraseña,longitud mínima de ocho (8) caracteres, presencia de caracteres especiales, \nuso de al menos dos (2) letras mayúsculas, uso de al menos tres (3) letras minúsculas, \nuso de al menos un (1) número. (o 'exit' para salir): ");
            String password = scanner.nextLine();

            if (password.equalsIgnoreCase("exit")) {
                break;
            }

            PasswordValidationTask task = new PasswordValidationTask(password);
            executor.execute(task);
        }

        executor.shutdown();
    }

    private static class PasswordValidationTask implements Runnable {
        private final String password;

        public PasswordValidationTask(String password) {
            this.password = password;
        }

        @Override
        public void run() {
            boolean isValid = pattern.matcher(password).matches();
            System.out.println("La contraseña '" + password + "' " + (isValid ? "es válida" : "no es válida"));
        }
    }
}