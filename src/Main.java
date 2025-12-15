import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CityFileProcessor processor = new CityFileProcessor();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ПРИЛОЖЕНИЕ ДЛЯ АНАЛИЗА СПРАВОЧНИКОВ ГОРОДОВ");
        System.out.println("Поддерживаемые форматы: XML, CSV");
        System.out.println("Для выхода введите: exit или quit");

        while (true) {
            System.out.print("\nВведите путь к файлу или 'exit' для выхода: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit") ||
                    input.equalsIgnoreCase("quit") ||
                    input.equalsIgnoreCase("выход")) {
                System.out.println("\nЗавершение работы приложения...");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            processor.processFile(input);
        }

        scanner.close();
        System.out.println("Приложение завершило работу. До свидания!");
    }
}