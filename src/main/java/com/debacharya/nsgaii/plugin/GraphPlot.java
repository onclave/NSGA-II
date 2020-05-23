package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
/*
 * MIT License
 *
 * Copyright (c) 2019 Debabrata Acharya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GraphPlot extends ApplicationFrame {

	public static String APPLICATION_TITLE = "NSGA-II";

	private final XYSeriesCollection dataset = new XYSeriesCollection();
	private final String key;
	private final List<AbstractObjectiveFunction> objectives;

	private int dimensionX = 800;
	private int dimensionY = 600;

	public GraphPlot(String key, List<AbstractObjectiveFunction> objectives) {
		super(GraphPlot.APPLICATION_TITLE);
		this.key = key;
		this.objectives = objectives;
		GraphPlot.isCompatible(objectives);
	}

	public void addData(Population population) {
		this.addData(population, this.key);
	}

	public void addData(Population population, String uniqueSeriesKey) {

		if(!GraphPlot.isCompatible(this.objectives)) return;

		XYSeries front = new XYSeries(uniqueSeriesKey);

		population.getPopulace().forEach(chromosome -> front.add(
			chromosome.getObjectiveValues().get(0),
			chromosome.getObjectiveValues().get(1)
		));

		dataset.addSeries(front);
	}

	public void configure(String xAxisTitle, String yAxisTitle) {

		if(!GraphPlot.isCompatible(this.objectives)) return;

		JFreeChart xyLineChart = ChartFactory.createXYLineChart(
			this.key,
			xAxisTitle,
			yAxisTitle,
			this.dataset,
			PlotOrientation.VERTICAL,
			true,
			true,
			false
		);
		ChartPanel chartPanel = new ChartPanel(xyLineChart);

		chartPanel.setPreferredSize(
			new java.awt.Dimension(
				this.dimensionX,
				this.dimensionY
			)
		);

		XYPlot plot = xyLineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		renderer.setSeriesPaint(0, this.getRandomPaint());
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		setContentPane(chartPanel);
	}

	public void plot(String xAxisTitle, String yAxisTitle) {

		if(!GraphPlot.isCompatible(this.objectives)) return;
		if(this.dataset.getSeriesCount() < 1) {
			System.out.println("\nNothing to plot!\n");
			return;
		}

		this.configure(xAxisTitle, yAxisTitle);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	public void plot() {
		this.plot(
			this.objectives.get(0).getObjectiveTitle(),
			this.objectives.get(1).getObjectiveTitle()
		);
	}

	public void setDimensions(int dimensionX, int dimensionY) {
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
	}

	public static boolean isCompatible(List<AbstractObjectiveFunction> objectives) {

		if(objectives.size() > 2) {

			System.out.println(
				"\n\n!! There are more than two objective functions present which cannot be plotted on a 2D graph.\n" +
				"!! You may need to implement your own plotting algorithm since this package does not support plotting \n" +
				"!! more than two objective functions at once!.\n"
			);

			return false;
		}

		return true;
	}

	private Paint getRandomPaint() {
		return new Color(
			ThreadLocalRandom.current().nextFloat(),
			ThreadLocalRandom.current().nextFloat(),
			ThreadLocalRandom.current().nextFloat()
		);
	}
}
