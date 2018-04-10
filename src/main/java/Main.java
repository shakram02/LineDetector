import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        x.add(980.5);
        y.add(596.0);
        x.add(726.5);
        y.add(549.0);
        x.add(877.5);
        y.add(299.0);
        x.add(851.0);
        y.add(572.5);
        x.add(1022.5);
        y.add(318.5);
        x.add(754.5);
        y.add(281.0);
        x.add(593.5);
        y.add(525.0);
        x.add(633.0);
        y.add(261.5);
//
//        List<Double> y = new ArrayList<>();
//        y.add(5.0);
//        y.add(8.0);
//        y.add(11.0);
//        y.add(22.0);
//        y.add(17.0);
//        y.add(20.0);
//        y.add(23.0);
//        y.add(500.0);
//
//        List<Double> x = new ArrayList<>();
//        for (int i = 0; i < y.size(); i++) {
//            x.add((double) i);
//        }

//        List<Double> result = RANSAC.perform(x, y, 3, 100, 400, 0.4);
////        List<Double> result = RANSAC.perform(dataX, data, 2, 100, 1, 0.2);
//        for (Double d : result) {
//            System.out.println(d);
//        }

        VectorFinder finder = new VectorFinder(x, y);

        finder.clusterLines();
    }
}

