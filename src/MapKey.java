import java.util.Objects;

public class MapKey {
    String plate;
    String well;

    public MapKey(String plateNumber, String wellID) {
        plate = plateNumber;
        well = wellID;
    }

    public String getPlate() {
        return plate;
    }

    public String getWell() {
        return well;
    }

    public String toString() {
        return plate + " " + well;
    }
    @Override
    public int hashCode() {
        return Objects.hash(plate, well);
    }

    @Override
    public boolean equals(Object o) {
        MapKey k = (MapKey) o;
        if (plate.equals(k.getPlate()) && well.equals(k.getWell())) {
            return true;
        } else {
            return false;
        }
    }


}
