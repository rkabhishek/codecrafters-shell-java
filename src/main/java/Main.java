import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String TYPE = "type";
    private static final String EXIT = "exit";
    private static final String ECHO = "echo";
    private static final List<String> BUILT_IN_COMMANDS = List.of(EXIT, ECHO, TYPE);

    public static void main(String[] args) throws Exception {

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

    private static void handleType(String cmd) {
        cmd = cmd.trim();
        if (cmd.isEmpty()) {
            return;
        }

        if (BUILT_IN_COMMANDS.contains(cmd)) {
            System.out.println(cmd + " is a shell builtin");
        } else {
            Optional<String> path = getPath(cmd);
            if (path.isPresent()) {
                System.out.println(cmd + " is " + path.get());
            } else {
                System.out.println(cmd + ": not found");
            }
        }
    }

    private static Optional<String> getPath(String cmd) {
        String pathStr = System.getenv("PATH");
        if (pathStr == null || pathStr.isEmpty()) {
            return Optional.empty();
        }

        String[] paths = pathStr.split(File.pathSeparator);

        for (String path : paths) {
            Path fullPath = Path.of(path, cmd);
            if (Files.isRegularFile(fullPath)) {
                return Optional.of(fullPath.toString());
            }
        }

        return Optional.empty();
    }
}
