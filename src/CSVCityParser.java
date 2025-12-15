import java.io.*;
import java.util.*;

public class CSVCityParser implements CityFileParser {
    @Override
    public List<CityRecord> parse(File file) throws Exception {
        List<CityRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = parseCSVLine(line);
                if (values.length >= 4) {
                    String city = values[0].trim();
                    String street = values[1].trim();
                    String house = values[2].trim();
                    int floor = Integer.parseInt(values[3].trim());

                    records.add(new CityRecord(city, street, house, floor));
                }
            }
        }

        return records;
    }

    private String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ';' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());

        return result.toArray(new String[0]);
    }
}