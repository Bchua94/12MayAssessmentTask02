package sg.edu.nus.iss;



import java.util.Scanner;

public class CalculatorLast
{
    public static void main( String[] args )
    {
        System.out.println("Welcome.");

        Scanner scanner = new Scanner(System.in);
        String option = "";

        while (!option.equals("exit")) {
            System.out.println("> ");
            option = scanner.nextLine();

            if (option.equals("exit")) {
                System.out.println("Bye bye");
                
            }
            else {
                String[] parts = option.split(" ");

                double number1 = Double.parseDouble(parts[0]);

                String operator = parts[1];

                double number2 = Double.parseDouble(parts[2]);

                double result = 0;

                if (operator.equals("+")) {
                    result = number1 + number2;
                    System.out.println(result);
                }

                else if (operator.equals("-")) {
                    result = number1 - number2;
                    System.out.println(result);

                }
                else if (operator.equals("*")) {
                    result = number1 * number2;
                    System.out.println(result);
                }

                else if (operator.equals("/")) {
                    result = number1 / number2;
                    System.out.println(result);
                }


                double last = 0;
                if (option.contains("$last")) {
                    result += last;
                }
                last = result;
                System.out.println(result);
        }
        }
        scanner.close();
    }
}
