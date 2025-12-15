import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLCityParser implements CityFileParser {
    @Override
    public List<CityRecord> parse(File file) throws Exception {
        List<CityRecord> records = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList itemList = document.getElementsByTagName("item");

        for (int i = 0; i < itemList.getLength(); i++) {
            Element item = (Element) itemList.item(i);

            String city = item.getAttribute("city");
            String street = item.getAttribute("street");
            String house = item.getAttribute("house");
            int floor = Integer.parseInt(item.getAttribute("floor"));

            records.add(new CityRecord(city, street, house, floor));
        }

        return records;
    }
}