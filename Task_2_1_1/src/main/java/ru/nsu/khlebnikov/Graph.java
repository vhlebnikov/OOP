package ru.nsu.khlebnikov;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Class for making graph with PrimeChecker methods test.
 */
public class Graph extends JFrame {
    public Graph() {
        initUI();
    }

    /**
     * Method that warms up the system before real measurements begin.
     */
    private void warmUp() {
        List<Integer> thousand = TestingTools.readFromFile("ThousandPrimeNumbers.txt");
        TestingTools.measure(() -> PrimeChecker.sequentialCheck(thousand), 1000);
        TestingTools.measure(() -> PrimeChecker.parallelCheck(thousand), 1000);
        for (int i = 2; i <= 40; i *= 2){
            int finalI = i;
            TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(thousand, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 100);
        }
    }

    /**
     * Method that draws GUI.
     */
    private void initUI() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method that makes data for graph.
     *
     * @return data set with graphics
     */
    private XYDataset createDataset() {
        // x - data y - time f(x,y) - method

        List<Integer> thousand = TestingTools.readFromFile("ThousandPrimeNumbers.txt");
        List<Integer> tenThousand = TestingTools.readFromFile("TenThousandPrimeNumbers.txt");
        List<Integer> oneHundredThousand =
                TestingTools.readFromFile("OneHundredThousandPrimeNumbers.txt");
        List<Integer> million = TestingTools.readFromFile("MillionPrimeNumbers.txt");

        warmUp();

        XYSeries sequential = new XYSeries("Sequential");
        sequential.add(1000, TestingTools
                .measure(() ->PrimeChecker.sequentialCheck(thousand), 1));
        sequential.add(10000, TestingTools
                .measure(() -> PrimeChecker.sequentialCheck(tenThousand), 1));
        sequential.add(100000, TestingTools
                .measure(() -> PrimeChecker.sequentialCheck(oneHundredThousand), 1));
        sequential.add(1000000, TestingTools
                .measure(() -> PrimeChecker.sequentialCheck(million), 1));

        XYSeries parallel = new XYSeries("ParallelStream");
        parallel.add(1000, TestingTools
                .measure(() -> PrimeChecker.parallelCheck(thousand), 1));
        parallel.add(10000, TestingTools
                .measure(() -> PrimeChecker.parallelCheck(tenThousand), 1));
        parallel.add(100000, TestingTools
                .measure(() -> PrimeChecker.parallelCheck(oneHundredThousand), 1));
        parallel.add(1000000, TestingTools
                .measure(() -> PrimeChecker.parallelCheck(million), 1));

        List<XYSeries> threads = new ArrayList<>();

        for (int i = 2; i <= 40; i *= 2) {
            XYSeries thread = new XYSeries(i + " Threads");
            int finalI = i;
            thread.add(1000, TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(thousand, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 1));
            thread.add(10000, TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(tenThousand, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 1));
            thread.add(100000, TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(oneHundredThousand, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 1));
            thread.add(1000000, TestingTools.measure(() -> {
                try {
                    PrimeChecker.threadCheck(million, finalI);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, 1));
            threads.add(thread);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sequential);
        dataset.addSeries(parallel);
        for (XYSeries x : threads) {
            dataset.addSeries(x);
        }
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average execution time for each method",
                "Input data",
                "Milliseconds",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle("Average execution time for each method",
                        new Font("Serif", Font.BOLD, 18)
                )
        );
        return chart;
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Graph ex = new Graph();
            ex.setVisible(true);
        });
    }
}