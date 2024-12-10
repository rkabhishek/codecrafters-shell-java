import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String TYPE = "type";
    private static final String EXIT = "exit";
    private static final String ECHO = "echo";

    private static final List<String> BUILT_IN_COMMANDS = List.of(EXIT, ECHO, TYPE);

    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equals(EXIT + " 0")) {
                System.exit(0);
            }

            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            if (command.equals(ECHO)) {
                System.out.println(argument);
            } else if (command.equals(TYPE)) {
                handleType(argument);
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }

    private static void handleType(String argument) {
        if (argument.trim().isEmpty()) {
            return;
        }

        if (BUILT_IN_COMMANDS.contains(argument)) {
            System.out.println(argument + " is a shell builtin");
        } else {
            System.out.println(argument + ": not found");
        }
    }
}
