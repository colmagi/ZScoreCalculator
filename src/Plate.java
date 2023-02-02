import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.*;

public class Plate {

    Compound[][] wells;
    double[] values;
    DescriptiveStatistics stats;
    double plateNumber;
    double average;
    double stdDev;
    double median;
    //average absolute deviation from the average
    double aadfa;
    //median absolute deviation from the median
    double madfm;

    public Plate(Compound[][] input, double number, int rows, int cols) {
        values = new double[rows * cols];
        int index = 0;
        stats = new DescriptiveStatistics();
        wells = input;
        for(int i = 0; i < wells.length; i++) {
            for (int j = 0; j < wells[0].length; j++) {
                stats.addValue(wells[i][j].rawValue);
                values[index] = wells[i][j].rawValue;
                index++;
            }
        }
        plateNumber = number;
        stdDev = stats.getStandardDeviation();
        average = stats.getMean();
        Median med = new Median();
        median = med.evaluate(values);

        //second loop stuff
        stats = new DescriptiveStatistics();
        values = new double[rows * cols];
        index = 0;
        for(int i = 0; i < wells.length; i++) {
            for (int j = 0; j < wells[0].length; j++) {
                wells[i][j].calculateADFA(average);
                stats.addValue(wells[i][j].adfa);
                wells[i][j].calculateADFM(median);
                values[index] = wells[i][j].adfm;
                index++;
            }
        }
        aadfa = stats.getMean();
        madfm = med.evaluate(values);
        calcZScores();

    }
    //private helper method for some constructor stuff (getting a little long)
    private void calcZScores() {
        for (int i = 0; i < wells.length; i++) {
            for (int j = 0; j < wells[0].length; j++) {
                Compound well = wells[i][j];
                well.medZScore = (well.rawValue - median) / (1.4825 * madfm);
                well.meanZScore = (well.rawValue - average) / (1.4825 * aadfa);
            }
        }
    }

    public Compound[][] getTable() {
        return wells;
    }


}
