import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.isEmpty()) {
            if (input.equals("exit 0")) {
                System.exit(0);
            } else if (input.startsWith("echo")) {
                echo(input);
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






}
