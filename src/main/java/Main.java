import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("$ ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.isEmpty()) {
            if (input.equals("exit 0")) {
                System.exit(0);
            }
            
            System.out.println(input + ": command not found");
            System.out.print("$ ");
            input = scanner.nextLine();
        }
    }
}
