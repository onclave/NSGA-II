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

package com.debacharya.nsgaii;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.plugin.GraphPlot;

import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Reporter {

	private static final int fileHash = ThreadLocalRandom.current().nextInt(10000, 100000);
	private static final StringBuilder writeContent = new StringBuilder();

	private static GraphPlot allGenerationGraph;

	public static boolean silent = false;
	public static boolean plotGraph = true;
	public static boolean plotCompiledGraphForEveryGeneration = true;
	public static boolean plotGraphForEveryGeneration = false;
	public static boolean writeToDisk = true;
	public static boolean diskWriteSuccessful = true;
	public static String filename = "NSGA-II-report-" + Reporter.fileHash + ".txt";

	public static void init(Configuration configuration) {

		if(silent && !writeToDisk) return;

		p("\n[ " + java.time.LocalDateTime.now() + " ]");
		p("\n** To stop reporter from printing to console, change Reporter.silent to true or call Configuration.beSilent()");
		p(
		"** Reporter is" 											+
			(Reporter.writeToDisk ? "" : " not") 						+
			" writing to disk" 											+
			(Reporter.writeToDisk ? (" at " + Reporter.filename) : "") 	+
			"."
		);

		if(plotGraph)
			p("** Plotting pareto front.");

		if(plotCompiledGraphForEveryGeneration)
			p("** Plotting compiled graph for all generations");

		if(plotGraphForEveryGeneration)
			p("** Plotting separate graph for every generation. !! Note that this might cause performance issues!");

		p("** To change this behavior, change Reporter.writeToDisk or call Cofiguration.writeToDisk(boolean).");
		p("** To change location and file name of where to save the file, change Reporter.filename.\n");
		p("------------------------------------------------");
		p("   NON-DOMINATED SORTING GENETIC ALGORITHM-II   ");
		p("------------------------------------------------");
		p(configuration.toString());
	}

	public static void reportGeneration(Population parent, Population child, int generation, List<AbstractObjectiveFunction> objectives) {

		if(Reporter.allGenerationGraph == null)
			Reporter.allGenerationGraph = new GraphPlot("ALL GENERATIONS", objectives);

		if(plotGraph && plotCompiledGraphForEveryGeneration)
			Reporter.allGenerationGraph.addData(child, "gen. " + generation);

		if(plotGraph && plotGraphForEveryGeneration)
			Reporter.plot2DPopulation(child, "GENERATION " + generation, objectives);

		if(silent && !writeToDisk) return;

		p("\n++++++++++++++++ GENERATION: " + generation + " ++++++++++++++++\n");
		p("[ START ]\n");
		p("Parent Population: " + parent.size());
		p("Child Population: " + child.size());
		p("\n======== PARENT ========\n");
		Reporter.reportPopulation(parent);
		p("\n======== CHILD ========\n");
		Reporter.reportPopulation(child);
		p("\n[ END ]");
	}

	public static void reportPopulation(Population population) {

		if(silent && !writeToDisk) return;

		for(Chromosome chromosome : population.getPopulace())
			Reporter.reportChromosome(chromosome);
	}

	public static void reportChromosome(Chromosome chromosome) {

		if(silent && !writeToDisk) return;

		Reporter.reportGeneticCode(chromosome.getGeneticCode());

		p(">> " + chromosome.toString());
	}

	public static void reportGeneticCode(List<AbstractAllele> geneticCode) {

		if(silent && !writeToDisk) return;

		StringBuilder code = new StringBuilder("# [ ");

		for(AbstractAllele allele : geneticCode)
			code.append(allele.toString()).append(" ");

		p(code.append("]").toString());
	}

	public static void plot2DPopulation(Population population, String key, List<AbstractObjectiveFunction> objectives) {

		if(!GraphPlot.isCompatible(objectives)) return;

		GraphPlot graph = new GraphPlot(key, objectives);

		graph.addData(population);
		graph.plot();
	}

	public static void plot2DParetoFront(Population population, List<AbstractObjectiveFunction> objectives) {
		Reporter.plot2DPopulation(population, "PARETO FRONT", objectives);
	}

	public static void plot2DGraphForAllGenerations(List<AbstractObjectiveFunction> objectives) {
		if(!GraphPlot.isCompatible(objectives)) return;
		Reporter.allGenerationGraph.plot();
	}

	public static void plotGraphs(Population finalChild, List<AbstractObjectiveFunction> objectives) {

		if(!GraphPlot.isCompatible(objectives)) return;
		if(plotGraph) Reporter.plot2DParetoFront(finalChild, objectives);
		if(plotCompiledGraphForEveryGeneration) Reporter.plot2DGraphForAllGenerations(objectives);
	}

	public static void terminate(Population finalChild, List<AbstractObjectiveFunction> objectives) {

		Reporter.plotGraphs(finalChild, objectives);

		if(silent && !writeToDisk) return;

		p("------------------------------------------------");
		p("NSGA-II ENDED SUCCESSFULLY\n");

		if(writeToDisk) {
			Reporter.writeToFile();
			if(diskWriteSuccessful) p("** Output saved at " + filename + "\n");
		}
	}

	private static void writeToFile() {

		try {
			FileWriter writer = new FileWriter(Reporter.filename);

			writer.write(Reporter.writeContent.toString());
			writer.close();
		} catch (Exception e) {
			diskWriteSuccessful = false;
			System.out.println("\n!!! COULD NOT WRITE FILE TO DISK!\n\n");
		}
	}

	private static void p(String s) {
		if(writeToDisk) Reporter.writeContent.append(s).append(System.lineSeparator());
		if(!silent) System.out.println(s);
	}
}
