package homework2;

import java.util.Scanner;

/*
 * Разработайте программу, которая выбросит Exception, когда пользователь вводит пустую строку.
 * Пользователю должно показаться сообщение, что пустые строки вводить нельзя.
 * */
public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processInput(scanner);
        scanner.close();
    }

    public static void processInput(Scanner scanner) {
        try {
            System.out.println("Введите что-нибудь:");
            inputString(scanner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inputString(Scanner scanner) throws Exception{
        String text = scanner.nextLine();
        if (text.isEmpty()) {
            throw new Exception("Пустые строки вводить нельзя.");
        }
    }
}
