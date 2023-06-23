import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;

public class Main {
    public static final int ROWS = 16;
    public static final int COLS = 24;

    public static void main(String[] args) throws IOException {
        HashMap<MapKey, MapValue> lookup = new HashMap<>();
        initializeMap(lookup);
//        for (HashMap.Entry<MapKey, MapValue> entry : lookup.entrySet()) {
//            MapKey key = entry.getKey();
//            System.out.print (key + " ");
//            MapValue value = entry.getValue();
//            System.out.print (value + " ");
//        }
        ArrayList<Compound> totalList = new ArrayList<>();
        File file = new File("resources/input.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNextDouble()) {
            double negControl = 0;
            double number = sc.nextDouble();
            //Everything lol
            //Calculates compound data first (individual stuff)
            Compound[][] input = new Compound[ROWS][COLS - 2];
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    if (j == COLS - 1) {
                        // We are at a negative control
                        negControl += sc.nextDouble();
                    }
                    else if (j == COLS - 2) {
                        sc.nextDouble();
                        //literally do nothing lmao
                    }
                    else {
                        char letter = (char) (65 + i);
                        String name = String.format("%02d",(j + 1));
                        name = letter + name;
                        String template = "PLATE_" + String.format("%03d", (int) number);
                        MapKey key = new MapKey(template, name);
                        Compound temp = new Compound(sc.nextDouble(), number, name, key);
                        input[i][j] = temp;
                        totalList.add(temp);
                    }
                }
            }
            negControl = negControl / ROWS;
            //Stupid second for loop because we can't calculate average of negcontrol in the middle of
            //the first for loop due to the way the txt is formatted
            for (int i = 0; i < input.length; i++) {
                for (int j = 0; j < input[0].length; j++) {
                    //Time to calculate relative signal of compounds
                    input[i][j].calculateRV(negControl);
                }
            }
            //Create plate object and shove everything into constructor lmao
            Plate plate = new Plate(input, number, ROWS, COLS-2);
        }
        Collections.sort(totalList);
        System.out.println(totalList.size());
        File out = new File("resources/output.txt");
        FileWriter output = null;
        try {
            output = new FileWriter(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Compound c : totalList) {
            StringBuilder sb = new StringBuilder();
            MapValue temp = lookup.get(c.k);
            if (temp != null) {
                sb.append((int) c.plateNumber + " ");
                sb.append(c.wellNumber + " ");
                sb.append(temp.getIDNumber() + " ");
                sb.append(c.medZScore + " ");
                sb.append(c.meanZScore + " ");
                sb.append(c.relativeSignal + " ");
                sb.append(c.rawValue + " ");
                sb.append(temp.getSmiles() + " ");
                output.write(sb.toString());
                output.write("\n");
            }
        }
        output.close();
//            System.out.println(c);
//            System.out.println(lookup.get(c.k));
//            System.out.println();
    }

    private static void initializeMap(HashMap<MapKey, MapValue> lookup) {
        File file = new File("resources/lookup.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(sc.hasNext()) {
            //looooong while loop oops
            String ID = sc.next();
            String plate = sc.next();
            String well = sc.next();
            String smile = sc.next();
            MapKey k = new MapKey(plate, well);
            MapValue v = new MapValue(ID, smile);
            lookup.put(k, v);
        }

    }
}
