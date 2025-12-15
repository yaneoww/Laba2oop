import java.util.List;
import java.io.File;

public interface CityFileParser {
    List<CityRecord> parse(File file) throws Exception;
}