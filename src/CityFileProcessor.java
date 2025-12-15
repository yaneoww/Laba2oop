import java.io.File;
import java.util.*;

public class CityFileProcessor {
    private Map<String, CityFileParser> parsers;

    public CityFileProcessor() {
        parsers = new HashMap<>();
        parsers.put("xml", new XMLCityParser());
        parsers.put("csv", new CSVCityParser());
    }

    public void processFile(String filePath) {
        long startTime = System.currentTimeMillis();

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("Файл не найден: " + filePath);
                return;
            }

            String extension = getFileExtension(filePath);
            CityFileParser parser = parsers.get(extension.toLowerCase());

            if (parser == null) {
                System.out.println("Неподдерживаемый формат файла: " + extension);
                return;
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("Обработка файла: " + filePath);
            System.out.println("=".repeat(60));

            List<CityRecord> records = parser.parse(file);
            CityStatistics statistics = new CityStatistics(records);

            // 1. Вывод дублирующихся записей
            System.out.println("\n1. ДУБЛИРУЮЩИЕСЯ ЗАПИСИ:");
            System.out.println("-".repeat(60));

            Map<CityRecord, Long> duplicates = statistics.findDuplicates();
            if (duplicates.isEmpty()) {
                System.out.println("Дублирующихся записей не найдено.");
            } else {
                duplicates.forEach((record, count) -> {
                    System.out.printf("%s (повторений: %d)%n", record, count);
                });
            }

            // 2. Вывод статистики по этажам
            System.out.println("\n2. СТАТИСТИКА ПО ЭТАЖАМ В КАЖДОМ ГОРОДЕ:");
            System.out.println("-".repeat(60));

            Map<String, Map<Integer, Long>> floorStats = statistics.getFloorStatisticsByCity();
            floorStats.forEach((city, floors) -> {
                System.out.printf("%nГород: %s%n", city);
                System.out.println("Этажность | Количество зданий");
                System.out.println("----------|------------------");

                // Сортируем по номеру этажа
                floors.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            System.out.printf("%9d | %16d%n", entry.getKey(), entry.getValue());
                        });
            });

            // 3. Общая информация
            System.out.println("\n3. ОБЩАЯ ИНФОРМАЦИЯ:");
            System.out.println("-".repeat(60));
            System.out.printf("Всего записей: %d%n", statistics.getTotalRecords());
            System.out.printf("Всего городов: %d%n", floorStats.size());

            long endTime = System.currentTimeMillis();
            System.out.printf("Время обработки: %d мс%n", (endTime - startTime));

        } catch (Exception e) {
            System.out.println("Ошибка при обработке файла: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(60) + "\n");
    }

    private String getFileExtension(String filePath) {
        int lastDot = filePath.lastIndexOf('.');
        if (lastDot == -1) {
            return "";
        }
        return filePath.substring(lastDot + 1);
    }
}