import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JShell js = JShell.builder().build();
        Scanner input = new Scanner(System.in);
        List<String> history = new ArrayList<>();

        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("Please enter your arithmetic expression (or 'history' to see past calculations): ");
            String exp = input.nextLine().trim();

            if (exp.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }

            if (exp.equalsIgnoreCase("history")) {
                printHistory(history, input);
                continue;
            }

            if (exp.equalsIgnoreCase("recall")) {
                recallHistory(history, input);
                continue;
            }

            try {
                exp = preprocessExpression(exp);
                if (!areBracketsBalanced(exp)) {
                    throw new IllegalArgumentException("ERROR! Close all of the brackets.");
                }
                if (exp.contains("/0") || exp.contains("%0")) {
                    throw new ArithmeticException("ERROR! Division by zero is not allowed.");
                }

                List<SnippetEvent> events = js.eval(exp);
                if (events.isEmpty() || events.get(0).value() == null) {
                    throw new Exception("Invalid expression or unsupported syntax.");
                }

                String res = events.get(0).value();
                if (res.matches("-?\\d+\\.0")) {
                    res = res.substring(0, res.length() - 2);
                }

                String record = exp + " = " + res;
                history.add(record);
                System.out.println("Result: " + res);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error in evaluating the expression: " + e.getMessage());
            }

            System.out.print("Do you want to continue? (y/n): ");
            String choice = input.nextLine().trim();
            if (choice.equalsIgnoreCase("n")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            }
        }
        input.close();
    }

    private static void printHistory(List<String> history, Scanner input) {
        if (history.isEmpty()) {
            System.out.println("History is empty.");
        } else {
            System.out.println("Calculation History:");
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }

    private static void recallHistory(List<String> history, Scanner input) {
        if (history.isEmpty()) {
            System.out.println("History is empty.");
            return;
        }

        System.out.print("Enter the number of the calculation you want to recall: ");
        int index = input.nextInt();
        input.nextLine(); // consume the newline character left by nextInt()

        if (index < 1 || index > history.size()) {
            System.out.println("Invalid selection.");
        } else {
            String recalledExpression = history.get(index - 1).split(" = ")[0]; // Extract the expression
            System.out.println("Recalled Expression: " + recalledExpression);
            // You can now use this recalled expression for further calculations
            System.out.print("Do you want to use this expression? (y/n): ");
            String useChoice = input.nextLine().trim();
            if (useChoice.equalsIgnoreCase("y")) {
                System.out.println("Using recalled expression...");
                // Call the eval method again on this recalled expression
                evaluateExpression(recalledExpression);
            }
        }
    }

    private static void evaluateExpression(String exp) {
        // This method evaluates a given expression, similar to the code already implemented
        JShell js = JShell.builder().build();
        try {
            exp = preprocessExpression(exp);
            if (!areBracketsBalanced(exp)) {
                throw new IllegalArgumentException("ERROR! Close all of the brackets.");
            }
            if (exp.contains("/0") || exp.contains("%0")) {
                throw new ArithmeticException("ERROR! Division by zero is not allowed.");
            }

            List<SnippetEvent> events = js.eval(exp);
            if (events.isEmpty() || events.get(0).value() == null) {
                throw new Exception("Invalid expression or unsupported syntax.");
            }

            String res = events.get(0).value();
            if (res.matches("-?\\d+\\.0")) {
                res = res.substring(0, res.length() - 2);
            }

            System.out.println("Result: " + res);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in evaluating the expression: " + e.getMessage());
        }
    }

    private static boolean areBracketsBalanced(String exp) {
        Stack<Character> stack = new Stack<>();
        for (char ch : exp.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    private static String preprocessExpression(String exp) {
        exp = exp.replaceAll("\\s", "");
        exp = exp.replaceAll("power\\(([^,]+),([^\\)]+)\\)", "Math.pow($1,$2)");
        exp = exp.replaceAll("abs\\(([^\\)]+)\\)", "Math.abs($1)");
        exp = exp.replaceAll("sqrt\\(([^\\)]+)\\)", "Math.sqrt($1)");
        exp = exp.replaceAll("round\\(([^\\)]+)\\)", "(double)Math.round($1)");
        return exp;
    }
}
