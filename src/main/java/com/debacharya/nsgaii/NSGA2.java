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

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class is used to run the NSGA-II algorithm. This class defines the core algorithm of NSGA-II.
 * Calling the `run()` method on an instance of `NSGA2` gets the algorithm running. It requires an instance of `Configuration`
 * class to run, which describes all the configuration required for that run. The `Configuration` class is described later in
 * the documentation.
 */
public class NSGA2 {

	public static final int DOMINANT = 1;
	public static final int INFERIOR = 2;
	public static final int NON_DOMINATED = 3;

	private final Configuration configuration;

	/**
	 * creates an instance of `NSGA2` with a default configuration object that provides a default implementation of every plugin
	 * needed by the algorithm to run. While this constructor is not of much use to the user, but this helps run a proof-of-concept
	 * or the algorithm itself with all the default plugins filled in.
	 */
	public NSGA2() {
		this.configuration = new Configuration();
	}

	/**
	 * creates an instance of `NSGA2` by taking a configuration object as parameter.
	 * This will be usually the most useful constructor for `NSGA2` for the user.
	 * The user can configure his / her plugins to the liking and then pass it to the `NSGA2`
	 * constructor for the algorithm to be setup according to the users needs.
	 *
	 * @param	configuration	the configuration object setup for running the algorithm
	 */
	public NSGA2(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Runs the actual NSGA-II core algorithm. It returns the Pareto Front or the last child as a `Population` object.
	 * This needs to be called on an instance of `NSGA2` to run the actual algorithm.
	 *
	 * @return	the final population as the Pareto Front
	 */
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

		int generation = 0;

		Reporter.reportGeneration(parent, child, generation, this.configuration.objectives);

		while(configuration.getGenerationDriver().shouldRun(child, ++generation, this.configuration.getGenerations(), null)) {

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

			Reporter.reportGeneration(parent, child, generation, this.configuration.objectives);
		}

		Reporter.terminate(child, this.configuration.objectives);

		if(Reporter.autoTerminate)
			Reporter.commitToDisk();

		return child;
	}

	/**
	 * This method takes a `Population` object and basically performs all the operations needed to be performed on the parent
	 * population in each generation. It executes the following operations on the population instance in order.
	 *
	 * - It calculates the objective values of all the chromosomes in the population based on the objective functions set
	 *	in the `Configuration` instance.
	 * - It then runs fast non-dominated sort on the population as defined in `NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part A.`
	 * - It then assigns crowding distance to each chromosome.
	 * - Finally, it sorts the chromosomes in the population based on its assigned rank.
	 *
	 * @param	population	the population instance to undergo the above steps
	 * @return				the same population instance that was passed as an argument
	 */
	public Population preparePopulation(Population population) {

		Service.calculateObjectiveValues(population, this.configuration.objectives);
		this.fastNonDominatedSort(population);
		this.crowdingDistanceAssignment(population);

		Service.randomizedQuickSortForRank(
			population.getPopulace(),
			0,
			population.size() - 1
		);

		return population;
	}

	/**
	 * This method takes a `Population` of size `2N` (_a combination of parent and child, both of size `N`,
	 * according to the originally proposed algorithm_) and returns a new `Population` instance of size `N` by
	 * selecting the first `N` chromosomes from the combined population, based on their rank. If it has to choose `M` chromosomes
	 * of rank `N` such that `M &gt; N`, it then sorts the `M` chromosomes based on their crowding distance.
	 *
	 * @param	combinedPopulation	the combined population of parent and child of size 2N
	 * @return						the new population of size N chosen from the combined population passed as parameter
	 */
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
	 * @param   population   the population whose crowding distances are to be calculated.
	 */
	public void crowdingDistanceAssignment(Population population) {

		int size = population.size();

		for(int i = 0; i < this.configuration.objectives.size(); i++) {

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

	/**
	 * This method checks whether one chromosome dominates the other chromosome or not. While the actual domination
	 * logic has been described in the `isDominant(Chromosome, Chromosome)` method, the `dominates(Chromosome, Chromosome)
	 * method returns one among the three values based on whether chromosome1 is dominant over chromosome2,
	 * or is inferior to chromosome2 or whether both of them are non-dominating, by returning
	 * `com.debacharya.nsgaii.NSGA2.DOMINANT`, `com.debacharya.nsgaii.NSGA2.INFERIOR` or
	 * `com.debacharya.nsgaii.NSGA2.NON_DOMINATED` respectively.
	 *
	 * @param	chromosome1	the chromosome to check whether it is dominating, inferior or non-dominated
	 * @param	chromosome2	the chromosome against which chromosome1 is checked
	 * @return				either NSGA2.DOMINANT, NSGA2.INFERIOR or NSGA2.NON_DOMINATED
	 */
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

		for(int i = 0; i < this.configuration.objectives.size(); i++)
			if(chromosome1.getObjectiveValues().get(i) < chromosome2.getObjectiveValues().get(i))
				return false;
			else if(chromosome1.getObjectiveValues().get(i) > chromosome2.getObjectiveValues().get(i))
				atLeastOneIsBetter = true;

		return atLeastOneIsBetter;
	}
}
