package com.debacharya.nsgaii.crossover;

import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

public class OrderCrossover extends AbstractCrossover {

	public OrderCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
		super(crossoverParticipantCreator);
	}

	@Override
	public List<Chromosome> perform(Population population) {

		if(!(population.getLast().getAllele(0) instanceof IntegerAllele))
			throw new UnsupportedOperationException("Order Crossover works only with Permutation Encoded chromosomes.");

		List<Chromosome> parents = this.crossoverParticipantCreator.create(population);
		Chromosome parent1 = parents.get(0);
		Chromosome parent2 = parents.get(1);
		List<IntegerAllele> child1geneticCode = new ArrayList<>();
		List<IntegerAllele> child2geneticCode = new ArrayList<>();

		int partition1 = Service.generateUniqueRandomNumbers(1, 0, parent1.getLength() / 2).get(0);
		int partition2 = Service.generateUniqueRandomNumbers(1, parent1.getLength() / 2, parent1.getLength()).get(0);
		int child1pointer = partition2;

		//////
		Reporter.p(partition1);
		Reporter.p(partition2);
		Reporter.reportGeneticCode(parent1.getGeneticCode());
		Reporter.reportGeneticCode(parent2.getGeneticCode());
		///////

		for(int i = 0; i < parent1.getLength(); i++) {
			child1geneticCode.add(i, new IntegerAllele(-1));
			child2geneticCode.add(i, new IntegerAllele(-1));
		}

		for(int i = partition1; i <= partition2; i++)
			child1geneticCode.set(i, (IntegerAllele) parent1.getAllele(i));

		if(partition2 < (parent1.getLength() - 1))
			for(int i = (partition2 + 1); i < parent1.getLength(); i++)
				if(!Service.isInGeneticCode(child1geneticCode, ((IntegerAllele) parent2.getAllele(i)).getGene()))
					child1geneticCode.set(++child1pointer, (IntegerAllele) parent2.getAllele(i));

		if(partition1 > 0)
			for(int i = 0; i < partition2; i++) {
				if(child1pointer < (parent1.getLength() - 1))
					if(!Service.isInGeneticCode(child1geneticCode, ((IntegerAllele) parent2.getAllele(i)).getGene()))
						child1geneticCode.set(++child1pointer, (IntegerAllele) parent2.getAllele(i));

			}









		Reporter.reportConcreteGeneticCode(child1geneticCode);
		Reporter.p("pointer: " + child1pointer);



		return null;

	}
}


/*

	parent1 	-> * * * * | % % % | # # # #
	parent2		-> @ @ @ @ | ^ ^ ^ | ! ! ! !

	child1 		-> @ @ @ @ | % % % | ! ! ! !
	child2		->

 */
