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

import com.debacharya.nsgaii.crossover.AbstractCrossover;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.crossover.UniformCrossover;
import com.debacharya.nsgaii.mutation.AbstractMutation;
import com.debacharya.nsgaii.mutation.SinglePointMutation;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.objectivefunction.ObjectiveProvider;
import com.debacharya.nsgaii.plugin.*;
import com.debacharya.nsgaii.termination.TerminatingCriterion;
import com.debacharya.nsgaii.termination.TerminatingCriterionProvider;

import java.util.List;

/**
 * The Configuration class is used to setup the runtime settings of the NSGA-II algorithm.
 * Since a lot of the settings within the algorithm can be changed, this class manages them all.
 * An instance of this class is needed to be setup before creating an instance of
 * NSGA2 class or running the algorithm. Changing settings of an instance of a Configuration class between
 * runs will also reflect immediately on the result. This means that, if required (but almost rarely),
 * the configuration of the algorithm within the same run can be changed dynamically.
 */
public class Configuration {

	public static final String CONFIGURATION_NOT_SETUP = "The NSGA-II configuration object is not setup properly!";
	public static final int DEFAULT_POPULATION_SIZE = 100;
	public static final int DEFAULT_GENERATIONS = 25;
	public static final int DEFAULT_CHROMOSOME_LENGTH = 20;

	public static String FITNESS_CALCULATOR_NULL = "The fitness calculation operation has not been setup. "				+
													"You need to set the AbstractObjectiveFunction#fitnessCalculator "	+
													"with an instance of FitnessCalculator!";

	public List<AbstractObjectiveFunction> objectives;

	private int populationSize;
	private int generations;
	private int chromosomeLength;
	private PopulationProducer populationProducer;
	private ChildPopulationProducer childPopulationProducer;
	private GeneticCodeProducer geneticCodeProducer;
	private AbstractCrossover crossover;
	private AbstractMutation mutation;
	private TerminatingCriterion terminatingCriterion;
	private FitnessCalculator fitnessCalculator;

	public Configuration() {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH
		);
	}

	public Configuration(int populationSize, int generations, int chromosomeLength) {
		this(
			populationSize,
			generations,
			chromosomeLength,
			DefaultPluginProvider.defaultPopulationProducer(),
			DefaultPluginProvider.defaultChildPopulationProducer(),
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(chromosomeLength),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true
		);
	}

	public Configuration(PopulationProducer populationProducer) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			populationProducer,
			DefaultPluginProvider.defaultChildPopulationProducer(),
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true
		);
	}

	public Configuration(ChildPopulationProducer childPopulationProducer) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			DefaultPluginProvider.defaultPopulationProducer(),
			childPopulationProducer,
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true
		);
	}

	public Configuration(GeneticCodeProducer geneticCodeProducer) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			DefaultPluginProvider.defaultPopulationProducer(),
			DefaultPluginProvider.defaultChildPopulationProducer(),
			geneticCodeProducer,
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true
		);
	}

	public Configuration(TerminatingCriterion terminatingCriterion) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			DefaultPluginProvider.defaultPopulationProducer(),
			DefaultPluginProvider.defaultChildPopulationProducer(),
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			terminatingCriterion,
			false,
			true,
			true
		);
	}

	public Configuration(List<AbstractObjectiveFunction> objectives) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			DefaultPluginProvider.defaultPopulationProducer(),
			DefaultPluginProvider.defaultChildPopulationProducer(),
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			objectives,
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true
		);
	}

	public Configuration(FitnessCalculator fitnessCalculator) {
		this(
			Configuration.DEFAULT_POPULATION_SIZE,
			Configuration.DEFAULT_GENERATIONS,
			Configuration.DEFAULT_CHROMOSOME_LENGTH,
			DefaultPluginProvider.defaultPopulationProducer(),
			DefaultPluginProvider.defaultChildPopulationProducer(),
			GeneticCodeProducerProvider.binaryGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
			TerminatingCriterionProvider.fixedTerminatingCriterion(),
			false,
			true,
			true,
			fitnessCalculator
		);
	}

	public Configuration(int populationSize,
						 int generations,
						 int chromosomeLength,
						 PopulationProducer populationProducer,
						 ChildPopulationProducer childPopulationProducer,
						 GeneticCodeProducer geneticCodeProducer,
						 List<AbstractObjectiveFunction> objectives,
						 AbstractCrossover crossover,
						 AbstractMutation mutation,
						 TerminatingCriterion terminatingCriterion,
						 boolean silent,
						 boolean plotGraph,
						 boolean writeToDisk) {

		this.populationSize = populationSize;
		this.generations = generations;
		this.chromosomeLength = chromosomeLength;
		this.populationProducer = populationProducer;
		this.childPopulationProducer = childPopulationProducer;
		this.geneticCodeProducer = geneticCodeProducer;
		this.objectives = objectives;
		this.crossover = crossover;
		this.mutation = mutation;
		this.terminatingCriterion = terminatingCriterion;

		Reporter.silent = silent;
		Reporter.plotGraph = plotGraph;
		Reporter.writeToDisk = writeToDisk;
	}

	public Configuration(int populationSize,
						 int generations,
						 int chromosomeLength,
						 PopulationProducer populationProducer,
						 ChildPopulationProducer childPopulationProducer,
						 GeneticCodeProducer geneticCodeProducer,
						 List<AbstractObjectiveFunction> objectives,
						 AbstractCrossover crossover,
						 AbstractMutation mutation,
						 TerminatingCriterion terminatingCriterion,
						 boolean silent,
						 boolean plotGraph,
						 boolean writeToDisk,
						 FitnessCalculator fitnessCalculator) {

		this(populationSize,
				generations,
				chromosomeLength,
				populationProducer,
				childPopulationProducer,
				geneticCodeProducer,
				objectives,
				crossover,
				mutation,
			terminatingCriterion,
				silent,
				plotGraph,
				writeToDisk);
		this.fitnessCalculator = fitnessCalculator;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		if(populationSize < 1)
			throw new UnsupportedOperationException("Population size cannot be less than 1.");
		this.populationSize = populationSize;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		if(generations < 1)
			throw new UnsupportedOperationException("Generations cannot be less than 1.");
		this.generations = generations;
	}

	public int getChromosomeLength() {
		return chromosomeLength;
	}

	public void setChromosomeLength(int chromosomeLength) {
		if(chromosomeLength < 1)
			throw new UnsupportedOperationException("Chromosome length cannot be less than 1.");
		this.chromosomeLength = chromosomeLength;
	}

	public PopulationProducer getPopulationProducer() {
		return populationProducer;
	}

	public void setPopulationProducer(PopulationProducer populationProducer) {
		this.populationProducer = populationProducer;
	}

	public ChildPopulationProducer getChildPopulationProducer() {
		return childPopulationProducer;
	}

	public void setChildPopulationProducer(ChildPopulationProducer childPopulationProducer) {
		this.childPopulationProducer = childPopulationProducer;
	}

	public GeneticCodeProducer getGeneticCodeProducer() {
		return geneticCodeProducer;
	}

	public void setGeneticCodeProducer(GeneticCodeProducer geneticCodeProducer) {
		this.geneticCodeProducer = geneticCodeProducer;
	}

	public AbstractCrossover getCrossover() {
		return crossover;
	}

	public void setCrossover(AbstractCrossover crossover) {
		this.crossover = crossover;
	}

	public AbstractMutation getMutation() {
		return mutation;
	}

	public void setMutation(AbstractMutation mutation) {
		this.mutation = mutation;
	}

	public TerminatingCriterion getTerminatingCriterion() {
		return terminatingCriterion;
	}

	public void setTerminatingCriterion(TerminatingCriterion terminatingCriterion) {
		this.terminatingCriterion = terminatingCriterion;
	}

	public FitnessCalculator getFitnessCalculator() {

		if(this.fitnessCalculator == null)
			this.fitnessCalculator = FitnessCalculatorProvider.normalizedGeneticCodeValue(
				0,
				Math.pow(2, this.chromosomeLength) - 1,
				0,
				2
			);

		return this.fitnessCalculator;
	}

	public void setFitnessCalculator(FitnessCalculator fitnessCalculator) {
		this.fitnessCalculator = fitnessCalculator;
	}

	public boolean isSetup() {
		return (
			this.populationSize != 0 				&&
			this.generations != 0 					&&
			this.chromosomeLength != 0 				&&
			this.populationProducer != null 		&&
			this.childPopulationProducer != null	&&
			this.geneticCodeProducer != null 		&&
			this.crossover != null					&&
			this.mutation != null					&&
			this.terminatingCriterion != null			&&
			this.objectives != null		&&
			!this.objectives.isEmpty()
		);
	}

	public void beSilent() {
		Reporter.silent = true;
	}

	public void plotGraph(boolean value) {
		Reporter.plotGraph = value;
	}

	public void writeToDisk(boolean value) {
		Reporter.writeToDisk = value;
	}

	public void completeSilence() {
		this.beSilent();
		this.plotGraph((false));
		this.writeToDisk(false);
	}

	@Override
	public String toString() {
		return "\nPopulation Size: " 																				+
				this.populationSize 																				+
				" [" 																								+
				(this.populationSize > 0 ? "valid" : "invalid") 													+
				"]" 																								+
				"\nGenerations: " 																					+
				this.generations 																					+
				" [" 																								+
				(this.generations > 0 ? "valid" : "invalid") 														+
				"]" 																								+
				"\nChromosome Length: " 																			+
				this.chromosomeLength				 																+
				" [" 																								+
				(this.chromosomeLength > 0 ? "valid" : "invalid") 													+
				"]" 																								+
				"\nPopulation Producer: " 																			+
				"[" 																								+
				(this.populationProducer != null ? "valid" : "invalid") 											+
				"]" 																								+
				"\nChild Population Producer: " 																	+
				"[" 																								+
				(this.childPopulationProducer != null ? "valid" : "invalid")				 						+
				"]" 																								+
				"\nGenetic Code Producer: " 																		+
				"[" 																								+
				(this.geneticCodeProducer != null ? "valid" : "invalid") 											+
				"]" 																								+
				"\nObjectives: " 																					+
				"[" 																								+
				((this.objectives != null && !this.objectives.isEmpty()) ? "valid" : "invalid")						+
				"]"																									+
				"\nCrossover Operator: " 																			+
				"[" 																								+
				(this.crossover != null ? "provided" : "not provided") 												+
				"]" 																								+
				"\nMutation Operator: " 																			+
				"[" 																								+
				(this.mutation != null ? "provided" : "not provided") 												+
				"]" 																								+
				"\nGeneration Driver: " 																			+
				"[" 																								+
				(this.terminatingCriterion != null ? "provided" : "not provided") 										+
				"]" 																								+
				"\nFitness Calculator: " 																			+
				"[" 																								+
				(this.fitnessCalculator != null ? "provided" : "not provided") 										+
				"]";
	}
}
