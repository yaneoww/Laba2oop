import java.util.Objects;

public class CityRecord {
    private String city;
    private String street;
    private String house;
    private int floor;

    public CityRecord(String city, String street, String house, int floor) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.floor = floor;
    }

    public String getCity() { return city; }

    public String getHouse() { return house; }
    public int getFloor() { return floor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityRecord that = (CityRecord) o;
        return floor == that.floor &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, house, floor);
    }

    @Override
    public String toString() {
        return String.format("Город: %-20s Улица: %-30s Дом: %-5s Этажей: %d",
                city, street, house, floor);
    }
}