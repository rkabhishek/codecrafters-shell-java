import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final String TYPE = "type";
    private static final String EXIT = "exit";
    private static final String ECHO = "echo";
    private static final String PWD = "pwd";
    private static final String CD = "cd";
    private static final String USER_DIR = "user.dir";
    private static final String USER_HOME = "user.home";
    private static final String HOME = "HOME";
    private static final String HOME_SYMBOL = "~";
    private static final List<String> BUILT_IN_COMMANDS = List.of(EXIT, ECHO, TYPE, PWD, CD);
    private static Path currentDirectory = Paths.get(System.getProperty(USER_DIR));

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equals(EXIT + " 0")) {
                System.exit(0);
            }

            String[] parts = input.split(" ");
            String command = parts[0];
            List<String> arguments = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));

            if (command.equals(ECHO)) {
                handleEcho(arguments);
            } else if (command.equals(TYPE)) {
                handleType(arguments);
            } else if (command.equals(PWD)) {
                handlePwd();
            } else if (command.equals(CD)) {
                handleCd(arguments);
            } else {
                executeCommand(command, arguments);
            }
        }
    }


    private static void handleEcho(List<String> arguments) {
        StringBuilder sb = new StringBuilder();
        for (String arg : arguments) {
            sb.append(arg).append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    private static void handleType(List<String> arguments) {
        if (arguments.isEmpty()) {
            return;
        }

        for (String arg : arguments) {
            if (BUILT_IN_COMMANDS.contains(arg)) {
                System.out.println(arg + " is a shell builtin");
            } else {
                Optional<String> path = getPath(arg);
                if (path.isPresent()) {
                    System.out.println(arg + " is " + path.get());
                } else {
                    System.out.println(arg + ": not found");
                }
            }
        }
    }

    private static void handlePwd() {
        System.out.println(currentDirectory.toAbsolutePath());
    }

    private static void handleCd(List<String> arguments) {
        if (arguments.size() > 1) {
            System.out.println("cd: too many arguments. Just specify one path");
            return;
        }

        String pathStr;
        if (arguments.isEmpty()) {
            pathStr = resolveHomeDirectory();
        } else if (arguments.get(0).startsWith(HOME_SYMBOL)) {
            pathStr = arguments.get(0).replace(HOME_SYMBOL, resolveHomeDirectory());
        } else {
            pathStr = arguments.get(0);
        }

        Path newPath = currentDirectory.resolve(pathStr).normalize();

        if (Files.isDirectory(newPath)) {
            currentDirectory = newPath;
        } else {
            System.out.println("cd: " + pathStr + ": No such file or directory");
        }
    }

    private static String resolveHomeDirectory() {
        String home = System.getenv(HOME);
        if (home == null || home.isEmpty()) {
            home = System.getProperty(USER_HOME);
        }

        return home;
    }

    private static Optional<String> getPath(String cmd) {
        String pathStr = System.getenv("PATH");
        if (pathStr == null || pathStr.isEmpty()) {
            return Optional.empty();
        }

        String[] paths = pathStr.split(File.pathSeparator);

        for (String path : paths) {
            Path fullPath = Path.of(path, cmd);
            if (Files.isRegularFile(fullPath) && Files.isExecutable(fullPath)) {
                return Optional.of(fullPath.toString());
            }
        }

        return Optional.empty();
    }

    private static void executeCommand(String command, List<String> arguments) {
        Optional<String> pathOp = getPath(command);

        if (pathOp.isPresent()) {
            String path = pathOp.get();

            try {
                List<String> commandList = new ArrayList<>();
                commandList.add(path);
                commandList.addAll(arguments);
                ProcessBuilder processBuilder = new ProcessBuilder(commandList);
                processBuilder.inheritIO();
                Process process = processBuilder.start();
                process.waitFor();
            } catch (IOException e) {
                System.err.println("I/O error: " +  e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Process interrupted: " + e.getMessage());
            }
        } else {
            System.out.println(command + ": command not found");
        }
    }
}
