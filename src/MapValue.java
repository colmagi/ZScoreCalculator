public class MapValue {
    String IDNumber;
    String smiles;

    public MapValue(String id, String s) {
        IDNumber = id;
        smiles = s;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public String getSmiles() {
        return smiles;
    }

    public String toString() {
        return IDNumber + " " + smiles;
    }
}
