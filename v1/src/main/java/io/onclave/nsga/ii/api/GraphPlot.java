/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Population;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.Random;

/**
 * this class is the under-the-hood service layer for generating the graphs using jFreeCharts library.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   1.0
 */
public class GraphPlot extends ApplicationFrame {
    
    private final static XYSeriesCollection DATASET = new XYSeriesCollection();
    private final static XYSeriesCollection MULTIPLE_DATASET = new XYSeriesCollection();
    private final static XYLineAndShapeRenderer MULTIPLE_RENDERER = new XYLineAndShapeRenderer();
    private final static String APPLICATION_TITLE = "NSGA-II";
    private static String GRAPH_TITLE = "PARETO FRONT";
    private static String KEY = "Pareto Front";
    private static int DIMENSION_X = 800;
    private static int DIMENSION_Y = 600;
    private static Paint COLOR = Color.RED;
    private static float STROKE_THICKNESS = 2.0f;
    private static final Random RANDOM = new Random();
    
    public GraphPlot() {
        super(APPLICATION_TITLE);
    }
    
    public GraphPlot(Population population) {
        
        super(APPLICATION_TITLE);
        createDataset(population);
    }
    
    public void prepareMultipleDataset(final Population population, final int datasetIndex, final String key) {
        
        createDataset(population, key, MULTIPLE_DATASET);
        
        MULTIPLE_RENDERER.setSeriesPaint(datasetIndex, new Color(RANDOM.nextFloat(), RANDOM.nextFloat(), RANDOM.nextFloat()));
        MULTIPLE_RENDERER.setSeriesStroke(datasetIndex, new BasicStroke(STROKE_THICKNESS));
    }
    
    public void configureMultiplePlotter(final String x_axis, final String y_axis, final String graphTitle) {
        
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(graphTitle, x_axis, y_axis, MULTIPLE_DATASET, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        
        chartPanel.setPreferredSize(new java.awt.Dimension(DIMENSION_X, DIMENSION_Y));
        
        final XYPlot plot = xyLineChart.getXYPlot();
        
        plot.setRenderer(MULTIPLE_RENDERER);
        setContentPane(chartPanel);
    }
    
    private void createDataset(final Population population) {
        createDataset(population, KEY);
    }
    
    private void createDataset(final Population population, String key) {
        createDataset(population, key, DATASET);
    }
    
    private void createDataset(final Population population, String key, XYSeriesCollection dataset) {
        
        final XYSeries paretoFront = new XYSeries(key);
        
        population.getPopulace().stream().forEach((c) -> { paretoFront.add(Configuration.getObjectives().get(0).objectiveFunction(c), Configuration.getObjectives().get(1).objectiveFunction(c)); });
        
        dataset.addSeries(paretoFront);
    }
    
    public void configurePlotter(final String x_axis, final String y_axis) {
        
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(GRAPH_TITLE, x_axis, y_axis, DATASET, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(xyLineChart);
        
        chartPanel.setPreferredSize(new java.awt.Dimension(DIMENSION_X, DIMENSION_Y));
        
        final XYPlot plot = xyLineChart.getXYPlot();
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        
        renderer.setSeriesPaint(0, COLOR);
        renderer.setSeriesStroke(0, new BasicStroke(STROKE_THICKNESS));
        
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    public static void setGRAPH_TITLE(String GRAPH_TITLE) {
        GraphPlot.GRAPH_TITLE = GRAPH_TITLE;
    }

    public static void setKEY(String KEY) {
        GraphPlot.KEY = KEY;
    }

    public static void setDIMENSION_X(int DIMENSION_X) {
        GraphPlot.DIMENSION_X = DIMENSION_X;
    }

    public static void setDIMENSION_Y(int DIMENSION_Y) {
        GraphPlot.DIMENSION_Y = DIMENSION_Y;
    }

    public static void setCOLOR(Paint COLOR) {
        GraphPlot.COLOR = COLOR;
    }

    public static void setSTROKE_THICKNESS(float STROKE_THICKNESS) {
        GraphPlot.STROKE_THICKNESS = STROKE_THICKNESS;
    }
}
