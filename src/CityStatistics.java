import java.util.*;
import java.util.stream.Collectors;

public class CityStatistics {
    private List<CityRecord> records;

    public CityStatistics(List<CityRecord> records) {
        this.records = records;
    }

    // 1. Найти дублирующиеся записи
    public Map<CityRecord, Long> findDuplicates() {
        return records.stream()
                .collect(Collectors.groupingBy(r -> r, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // 2. Статистика по этажам для каждого города
    public Map<String, Map<Integer, Long>> getFloorStatisticsByCity() {
        Map<String, Map<Integer, Long>> result = new TreeMap<>();

        for (CityRecord record : records) {
            result.putIfAbsent(record.getCity(), new HashMap<>());
            Map<Integer, Long> floorMap = result.get(record.getCity());
            floorMap.put(record.getFloor(), floorMap.getOrDefault(record.getFloor(), 0L) + 1);
        }

        return result;
    }

    // Общее количество записей
    public int getTotalRecords() {
        return records.size();
    }
}