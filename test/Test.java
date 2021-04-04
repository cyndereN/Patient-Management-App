import model.DataFrame;
import model.DataLoader;
import model.Model;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        DataFrame d = new DataLoader().dataLoadedToDataFrame("resources/patients100.csv");
        System.out.println(d.getColumnNames());
        System.out.println(d.getValue("LAST", 20));
        Model m = new Model();
        m.readFile("resources/patients100.csv");
        System.out.println(m.getAverageAge());
        System.out.println(m.getCommonYear());
        System.out.println(m.getCommonCity());
        System.out.println(m.getGenderProportion());
        System.out.println(m.getOldest());
        System.out.println(Arrays.deepToString(m.getStringPatientList()));
    }
}
