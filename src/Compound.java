public class Compound implements Comparable<Compound>{

    double rawValue;
    double plateNumber;
    String wellNumber;
    double relativeSignal;
    //absolute deviation from the average
    double adfa;
    //absolute deviation from the median
    double adfm;
    //median z score
    double medZScore;
    //mean z score
    double meanZScore;
    //Compound ID
    MapKey k;

    public Compound(double value, double plate, String well, MapKey key) {
        rawValue = value;
        plateNumber = plate;
        wellNumber = well;
        k = key;
    }

    public void calculateRV(double negControl) {
        relativeSignal = rawValue / negControl;
    }

    public void calculateADFA(double plateAvg) {
        adfa = Math.abs(rawValue - plateAvg);
    }

    public void calculateADFM(double plateMedian) {
        adfm = Math.abs(rawValue - plateMedian);
    }

    @Override
    public int compareTo(Compound o) {
        if ((medZScore - o.medZScore) > 0) {
            return 1;
        }
        else if((medZScore - o.medZScore) < 0) {
            return -1;
        }
        else {
            return 0;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Plate number " + plateNumber + " ");
        s.append("median z score: " + medZScore + " ");
        s.append("relative signal: " + relativeSignal + " ");
        s.append("mean z score: " + meanZScore + " ");
        s.append("Raw score: " + rawValue + " ");
        s.append("Well number: " + wellNumber + " ");
        return s.toString();
    }
}
