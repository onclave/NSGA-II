package com.debacharya.nsgaii;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

public class NSGA2 {

	public static final int DOMINANT = 1;
	public static final int INFERIOR = 2;
	public static final int NON_DOMINATED = 3;

	private final Configuration configuration;

	public NSGA2() {
		this.configuration = new Configuration();
	}

	public NSGA2(Configuration configuration) {
		this.configuration = configuration;
	}

	public Population run() {

		if(!this.configuration.isSetup())
			throw new UnsupportedOperationException(Configuration.CONFIGURATION_NOT_SETUP + "\n" + this.configuration.toString());

		Reporter.init(this.configuration);

		Population parent = this.preparePopulation(
			this.configuration.getPopulationProducer().produce(
				this.configuration.getPopulationSize(),
				this.configuration.getChromosomeLength(),
				this.configuration.getGeneticCodeProducer(),
				null
			)
		);

		Population child = this.preparePopulation(
			this.configuration.getChildPopulationProducer().produce(
				parent,
				this.configuration.getCrossover(),
				this.configuration.getMutation(),
				this.configuration.getPopulationSize()
			)
		);

		Reporter.reportGeneration(parent, child, 0);

		for(int generation = 1; generation <= this.configuration.getGenerations(); generation++) {

			parent = this.getChildFromCombinedPopulation(
				this.preparePopulation(
					Service.combinePopulation(
						parent,
						child
					)
				)
			);

			child = this.preparePopulation(
				this.configuration.getChildPopulationProducer().produce(
					parent,
					this.configuration.getCrossover(),
					this.configuration.getMutation(),
					this.configuration.getPopulationSize()
				)
			);

			Reporter.reportGeneration(parent, child, generation);
		}

		Reporter.terminate(child);

		return child;
	}

	public Population preparePopulation(Population population) {

		Service.calculateObjectiveValues(population);
		this.fastNonDominatedSort(population);
		this.crowdingDistanceAssignment(population);

		Service.randomizedQuickSortForRank(
			population.getPopulace(),
			0,
			population.size() - 1
		);

		return population;
	}

	public Population getChildFromCombinedPopulation(Population combinedPopulation) {

		int lastNonDominatedSetRank = combinedPopulation.get(this.configuration.getPopulationSize() - 1).getRank();
		List<Chromosome> childPopulace = new ArrayList<>();

		if(combinedPopulation.get(this.configuration.getPopulationSize()).getRank() == lastNonDominatedSetRank)
			Service.sortForCrowdingDistance(combinedPopulation.getPopulace(), lastNonDominatedSetRank);

		for(int i = 0; i < this.configuration.getPopulationSize(); i++)
			childPopulace.add(combinedPopulation.get(i));

		return new Population(childPopulace);
	}

	/**
	 * This is an implementation of the fast non-dominated sorting algorithm as defined in the
	 * NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part A.
	 *
	 * @param   population  the population object that needs to undergo fast non-dominated sorting algorithm
	 */
	public void fastNonDominatedSort(Population population) {

		List<Chromosome> populace = population.getPopulace();

		for(Chromosome chromosome : populace)
			chromosome.reset();

		for(int i = 0; i < populace.size() - 1; i++) {
			for (int j = i + 1; j < populace.size(); j++)
				switch (this.dominates(populace.get(i), populace.get(j))) {

					case NSGA2.DOMINANT:

						populace.get(i).addDominatedChromosome(populace.get(j));
						populace.get(j).incrementDominatedCount(1);
						break;

					case NSGA2.INFERIOR:

						populace.get(i).incrementDominatedCount(1);
						populace.get(j).addDominatedChromosome(populace.get(i));
						break;

					case NSGA2.NON_DOMINATED:
						break;
				}

			if(populace.get(i).getDominatedCount() == 0)
				populace.get(i).setRank(1);
		}

		if(population.getLast().getDominatedCount() == 0)
			population.getLast().setRank(1);

		for(Chromosome chromosome : populace)
			for(Chromosome dominatedChromosome : chromosome.getDominatedChromosomes()) {

				dominatedChromosome.incrementDominatedCount(-1);

				if(dominatedChromosome.getDominatedCount() == 0)
					dominatedChromosome.setRank(chromosome.getRank() + 1);
			}
	}

	/**
	 * This is the implementation of the crowding distance assignment algorithm as defined in the
	 * NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part B.
	 * this ensures diversity preservation.
	 *
	 * @param   population   the population whose crowding distances are to be calculated
	 */
	public void crowdingDistanceAssignment(Population population) {

		int size = population.size();

		for(int i = 0; i < Configuration.objectives.size(); i++) {

			Service.randomizedQuickSortForObjective(population.getPopulace(), 0, population.size() - 1, i);

			Service.normalizeSortedObjectiveValues(population, i);

			population.get(0).setCrowdingDistance(Double.MAX_VALUE);
			population.getLast().setCrowdingDistance(Double.MAX_VALUE);

			double maxNormalizedObjectiveValue = population.selectMaximumNormalizedObjectiveValue(i);
			double minNormalizedObjectiveValue = population.selectMinimumNormalizedObjectiveValue(i);

			for(int j = 1; j < size; j++)
				if(population.get(j).getCrowdingDistance() < Double.MAX_VALUE) {

					double previousChromosomeObjectiveValue = population.get(j - 1).getNormalizedObjectiveValues().get(i);
					double nextChromosomeObjectiveValue = population.get(j + 1).getNormalizedObjectiveValues().get(i);
					double objectiveDifference = nextChromosomeObjectiveValue - previousChromosomeObjectiveValue;
					double minMaxDifference = maxNormalizedObjectiveValue - minNormalizedObjectiveValue;

					population.get(j).setCrowdingDistance(
						Service.roundOff(
							population.get(j).getCrowdingDistance() +
							(objectiveDifference / minMaxDifference),
							4
						)
					);
				}
		}
	}

	public int dominates(Chromosome chromosome1, Chromosome chromosome2) {

		if(this.isDominant(chromosome1, chromosome2)) return NSGA2.DOMINANT;
		else if(this.isDominant(chromosome2, chromosome1)) return NSGA2.INFERIOR;
		else return NSGA2.NON_DOMINATED;
	}

	/**
	 * This method checks whether chromosome1 dominates chromosome2.
	 * Requires that none of the values of the objective function values of chromosome1 is smaller
	 * than the values of the objective function values of chromosome2 and
	 * at least one of the values of the objective function of chromosome1 is greater than
	 * the corresponding value of the objective function of chromosome2.
	 *
	 * @param   chromosome1     the chromosome that may dominate
	 * @param   chromosome2     the chromosome that may be dominated
	 * @return                  boolean logic whether chromosome1 dominates chromosome2.
	 */
	public boolean isDominant(Chromosome chromosome1, Chromosome chromosome2) {

		boolean atLeastOneIsBetter = false;

		for(int i = 0; i < Configuration.objectives.size(); i++)
			if(chromosome1.getObjectiveValues().get(i) < chromosome2.getObjectiveValues().get(i))
				return false;
			else if(chromosome1.getObjectiveValues().get(i) > chromosome2.getObjectiveValues().get(i))
				atLeastOneIsBetter = true;

		return atLeastOneIsBetter;
	}
}
