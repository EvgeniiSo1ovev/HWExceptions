package homework3;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Напишите приложение, которое будет запрашивать у пользователя следующие данные
 * в произвольном порядке, разделенные пробелом:
 * Фамилия Имя Отчество датарождения номертелефона пол
 *
 * Форматы данных:
 * фамилия, имя, отчество - строки
 * датарождения - строка формата dd.mm.yyyy
 * номертелефона - целое беззнаковое число без форматирования
 * пол - символ латиницей f или m.

 * Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
 * вернуть код ошибки, обработать его и показать пользователю сообщение,
 * что он ввел меньше и больше данных, чем требуется.

 * Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
 * Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
 * Можно использовать встроенные типы java и создать свои.
 * Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

 * Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
 * в него в одну строку должны записаться полученные данные, вида

 * <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>

 * Однофамильцы должны записаться в один и тот же файл, в отдельные строки.

 * Не забудьте закрыть соединение с файлом.

 * При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
 * пользователь должен увидеть стектрейс ошибки.
 * */

public class Task {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        saveData(questionnaire(scanner));
        scanner.close();
    }

    public static Map<String, Object> questionnaire(Scanner scanner) {
        try {
            System.out.println("Введите данные в произвольном порядке, разделенные пробелом:\n" +
                    "Фамилия Имя Отчество датарождения номертелефона пол");
            String[] dataNames = {"lastName", "firstName", "fatherName", "bornDate", "phoneNumber", "gender"};
            return parseData(dataNames, inputData(scanner, dataNames.length));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<String> inputData(Scanner scanner, int count) throws Exception {
        String userText = scanner.nextLine();
        List<String> userList = new ArrayList<>(Arrays.asList(userText.split(" ")));
        if (userList.size() < count) {
            throw new Exception("Введено меньше данных, чем требуется");
        } else if (userList.size() > count) {
            throw new Exception("Введено больше данных, чем требуется");
        }
        return userList;
    }

    public static Map<String, Object> parseData(String[] dataNames, List<String> userList) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        for (String dataName : dataNames) {
            Object data = getDataByFormat(dataName, userList);
            dataMap.put(dataName, data);
            userList.remove(data);
        }
        return dataMap;
    }

    public static Object getDataByFormat(String dataName, List<String> userList) throws Exception {
        Object result = null;
        switch (dataName) {
            case "lastName" -> {
                result = findName(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Фамилии', " +
                            "соответствующее формату: только буквы алфавита");
                }
            }
            case "firstName" -> {
                result = findName(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Имени', " +
                            "соответствующее формату: только буквы алфавита");
                }
            }
            case "fatherName" -> {
                result = findName(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Отчества', " +
                            "соответствующее формату: только буквы алфавита");
                }
            }
            case "bornDate" -> {
                result = findBornDate(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Даты рождения', " +
                            "соответствующее формату: строка формата dd.mm.yyyy (год от 1900)");
                }
            }
            case "phoneNumber" -> {
                result = findPhoneNumber(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Номера телефона', " +
                            "соответствующее формату: целое беззнаковое число без форматирования");
                }
            }
            case "gender" -> {
                result = findGender(userList);
                if (result == null) {
                    throw new Exception("Среди введенных данных не найдено значение 'Пола', " +
                            "соответствующее формату: f или m");
                }
            }
        }
        return result;
    }

    public static Object findName(List<String> userList) {
        for (String ul : userList) {
            if (isOnlyAlphabet(ul)) return ul;
        }
        return null;
    }

    public static Object findBornDate(List<String> userList) {
        for (String ul : userList) {
            String[] dateArray = ul.split("\\.");
            if (dateArray.length == 3) {
                try {
                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                    df.setLenient(false);
                    df.parse(ul);
                    return ul;
                } catch (ParseException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static Object findPhoneNumber(List<String> userList) {
        for (String ul : userList) {
            if (isOnlyDigits(ul)) return ul;
        }
        return null;
    }

    public static Object findGender(List<String> userList) {
        for (String ul : userList) {
            if (ul.length() == 1) {
                char ch = ul.charAt(0);
                if ((Character.isLetter(ch)) && (ch == 'f' || ch == 'm')) return ch;
            }
        }
        return null;
    }

    public static boolean isOnlyAlphabet(String text) {
        return ((text != null) && (!text.equals("")) && (text.length() != 1) && (text.matches("^[a-zA-Z]*$")));
    }

    public static boolean isOnlyDigits(String text) {
        return text.matches("[0-9]+");
    }

    public static void saveData(Map<String, Object> dataMap) {
        if (dataMap != null) {
            try {
                String fileName = (String) dataMap.get("lastName");
                File file = new File(fileName);
                file.createNewFile();
                StringBuilder text = new StringBuilder();
                Charset charset = Charset.forName("US-ASCII");
                try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath(), charset);
                     BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath(), charset)) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                        text.append(line);
                    }
                    text.append(dataToString(dataMap)).append("\n");
                    bufferedWriter.write(String.valueOf(text));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static StringBuilder dataToString(Map<String, Object> dataMap) {
        StringBuilder text = new StringBuilder();
        text.append(dataMap.get("lastName")).append(" ");
        text.append(dataMap.get("firstName")).append(" ");
        text.append(dataMap.get("fatherName")).append(" ");
        text.append(dataMap.get("bornDate")).append(" ");
        text.append(dataMap.get("phoneNumber")).append(" ");
        text.append(dataMap.get("gender"));
        return text;
    }
}
