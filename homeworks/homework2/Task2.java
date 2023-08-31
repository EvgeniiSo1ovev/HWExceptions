package homework2;

/*
 * Если необходимо, исправьте данный код:
 * try {
       int d = 0;
       double catchedRes1 = intArray[8] / d;
       System.out.println("catchedRes1 = " + catchedRes1);
    } catch (ArithmeticException e) {
       System.out.println("Catching exception: " + e);
    }
 * */

import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Введите делитель:");
            int d = Integer.parseInt(scanner.nextLine());
            int[] intArray = {1, 2, 3, 4, 5, 6, 7, 8};
            double[] catchedRes = new double[intArray.length];
            for (int i = 0; i < intArray.length; i++) {
                if (d == 0) throw new RuntimeException("Catching exception: Divide by zero error");
                catchedRes[i] = (double) intArray[i] / d;
            }
            System.out.println("catchedRes1 = " + catchedRes[0]);
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
