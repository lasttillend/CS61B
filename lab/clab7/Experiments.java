import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Double> b = new BST<>();
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        for (int x = 1; x <= 5000; x++) {
            b.add(r.nextDouble());
            double thisY = b.averageDepth();
            xValues.add(x);
            yValues.add(thisY);

            double thisY2 = ExperimentHelper.optimalAverageDepth(x);
            y2Values.add(thisY2);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Number of items").yAxisTitle("Depth").build();
        chart.addSeries("Average depth", xValues, yValues);
        chart.addSeries("Optimal average depth", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        BST<Double> b = new BST<>();
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        // Initializes BST by randomly inserting N items
        int N = 1000;
        for (int i = 0; i < N; i++) {
            b.add(r.nextDouble());
        }
        double startingDepth = b.averageDepth();

        // Randomly delete an item using asymmetric Hibbard deletion
        int M = 10000;
        for (int x = 1; x <= M; x++) {
            b.deleteTakingSuccessor(b.getRandomKey());
            b.add(r.nextDouble());  // Randomly insert a new item
            double thisY = b.averageDepth();  // Records the average depth of the tree
            xValues.add(x);
            yValues.add(thisY / startingDepth);
        }

        // Plot the data
        XYChart chart = new XYChartBuilder().width(1000).height(600).xAxisTitle("Number of Operations").yAxisTitle("Ratio to the starting depth").build();
        chart.addSeries("Asymmetric deletion", xValues, yValues);

        new SwingWrapper(chart).displayChart();

    }

    public static void experiment3() {
        BST<Double> b = new BST<>();
        Random r = new Random();
        List<Integer> xValues = new ArrayList<>();
        List<Double> yValues = new ArrayList<>();

        // Initializes BST by randomly inserting N items
        int N = 1000;
        for (int i = 0; i < N; i++) {
            b.add(r.nextDouble());
        }
        double startingDepth = b.averageDepth();

        // Randomly delete an item using symmetric deletion
        int M = 10000;
        for (int x = 1; x <= M; x++) {
            b.deleteTakingRandom(b.getRandomKey());
            b.add(r.nextDouble());  // Randomly insert a new item
            double thisY = b.averageDepth();  // Records the average depth of the tree
            xValues.add(x);
            yValues.add(thisY / startingDepth);
        }

        // Plot the data
        XYChart chart = new XYChartBuilder().width(1000).height(600).xAxisTitle("Number of Operations").yAxisTitle("Ratio to the starting depth").build();
        chart.addSeries("Symmetric deletion", xValues, yValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment1();
        experiment2();
        experiment3();
    }
}
