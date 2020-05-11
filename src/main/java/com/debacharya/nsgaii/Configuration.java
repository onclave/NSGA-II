package com.debacharya.nsgaii;

import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.plugin.*;

import java.util.List;

public class Configuration {

	public static final String CONFIGURATION_NOT_SETUP = "The NSGA-II configuration object is not setup properly!";
	public static final int DEFAULT_POPULATION_SIZE = 100;
	public static final int DEFAULT_GENERATIONS = 25;
	public static final int DEFAULT_CHROMOSOME_LENGTH = 20;

	public static List<AbstractObjectiveFunction> objectives;
	public static String FITNESS_CALCULATOR_NULL = "The fitness calculation operation has not been setup. "				+
													"You need to set the AbstractObjectiveFunction#fitnessCalculator "	+
													"with an instance of FitnessCalculator!";

	private int populationSize;
	private int generations;
	private int chromosomeLength;
	private PopulationProducer populationProducer;
	private ChildPopulationProducer childPopulationProducer;
	private GeneticCodeProducer geneticCodeProducer;
	private AbstractCrossover crossover;
	private AbstractMutation mutation;
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
			DefaultPluginProvider.defaultGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(chromosomeLength),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
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
			DefaultPluginProvider.defaultGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
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
			DefaultPluginProvider.defaultGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
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
			DefaultPluginProvider.defaultGeneticCodeProducer(),
			objectives,
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
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
			DefaultPluginProvider.defaultGeneticCodeProducer(),
			ObjectiveProvider.provideSCHObjectives(Configuration.DEFAULT_CHROMOSOME_LENGTH),
			new UniformCrossover(CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()),
			new SinglePointMutation(),
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
						 boolean silent,
						 boolean plotGraph,
						 boolean writeToDisk) {

		this.populationSize = populationSize;
		this.generations = generations;
		this.chromosomeLength = chromosomeLength;
		this.populationProducer = populationProducer;
		this.childPopulationProducer = childPopulationProducer;
		this.geneticCodeProducer = geneticCodeProducer;
		Configuration.objectives = objectives;
		this.crossover = crossover;
		this.mutation = mutation;

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
			Configuration.objectives != null		&&
			!Configuration.objectives.isEmpty()
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
				((Configuration.objectives != null && !Configuration.objectives.isEmpty()) ? "valid" : "invalid")	+
				"]"																									+
				"\nCrossover Operator: " 																			+
				"[" 																								+
				(this.crossover != null ? "provided" : "not provided") 												+
				"]" 																								+
				"\nMutation Operator: " 																			+
				"[" 																								+
				(this.mutation != null ? "provided" : "not provided") 												+
				"]" 																								+
				"\nFitness Calculator: " 																			+
				"[" 																								+
				(this.fitnessCalculator != null ? "provided" : "not provided") 										+
				"]";
	}
}
