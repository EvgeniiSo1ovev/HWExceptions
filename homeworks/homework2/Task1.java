package homework2;

/*
 * Реализуйте метод, который запрашивает у пользователя ввод дробного числа (типа float),
 * и возвращает введенное значение. Ввод текста вместо числа не должно приводить к падению приложения,
 * вместо этого, необходимо повторно запросить у пользователя ввод данных.
 * */

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(inputNumber(scanner));
    }

    public static float inputNumber(Scanner scanner){
        try {
            System.out.println("Введите дробное число (типа float)");
            return Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Введенное значение не соответствует дробному числу.");
            return inputNumber(scanner);
        }
    }
}
