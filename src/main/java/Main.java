import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final String TYPE = "type";
    private static final String EXIT = "exit";
    private static final String ECHO = "echo";

    private static final String[] BUILT_IN_COMMANDS = {EXIT, ECHO, TYPE};

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
                boolean found = false;
                for (String str : BUILT_IN_COMMANDS) {
                    if (str.equals(argument)) {
                        found = true;
                        System.out.println(str + " is a shell builtin");
                        break;
                    }
                }

                if (!found) {
                    System.out.println(argument + ": not found");
                }
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
