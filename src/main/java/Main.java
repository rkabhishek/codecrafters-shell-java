import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final String[] BUILT_IN_COMMANDS = {"echo", "type", "exit"};

    public static void main(String[] args) throws Exception {
        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.isEmpty()) {
            if (input.equals("exit 0")) {
                System.exit(0);

            } else if (input.startsWith("echo")) {
                echo(input);

            } else if (input.startsWith("type")) {
                type(input);

            } else {
                System.out.println(input + ": command not found");
            }

            System.out.print("$ ");
            input = scanner.nextLine().trim();
        }
    }

    private static void echo(String input) {
        String[] arguments = input.split("\\s+");
        for (int i = 1; i < arguments.length; i++) {
            System.out.print(arguments[i]);
            System.out.print(" ");
        }

        System.out.println();
    }

    private static void type(String input) {
        String[] arguments = input.split("\\s+");
        for (int i = 1; i < arguments.length; i++) {
            if (Arrays.asList(BUILT_IN_COMMANDS).contains(arguments[i])) {
                System.out.println(arguments[i] + " is a shell builtin");
            } else {
                System.out.println(arguments[i] + ": not found");
            }
        }
    }
}
