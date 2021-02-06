package com.debacharya.nsgaii.crossover;

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

		List<Chromosome> children = new ArrayList<>();
		List<Chromosome> parents = this.crossoverParticipantCreator.create(population);
		Chromosome parent1 = parents.get(0);
		Chromosome parent2 = parents.get(1);
		int partition1 = Service.generateUniqueRandomNumbers(1, 1, parent1.getLength() / 2).get(0);
		int partition2 = Service.generateUniqueRandomNumbers(1, parent1.getLength() / 2, parent1.getLength() - 1).get(0);

		children.add(0, this.prepareChild(
			parent1,
			parent2,
			partition1,
			partition2
		));

		children.add(1, this.prepareChild(
			parent2,
			parent1,
			partition1,
			partition2
		));

		return children;
	}

	private Chromosome prepareChild(Chromosome parent1, Chromosome parent2, int partition1, int partition2) {

		List<IntegerAllele> childgeneticCode = new ArrayList<>();
		int pointerStart = -1;
		int pointerEnd = partition2;

		for(int i = 0; i < parent1.getLength(); i++)
			childgeneticCode.add(i, new IntegerAllele(-1));

		for(int i = partition1; i <= partition2; i++)
			childgeneticCode.set(i, (IntegerAllele) parent1.getAllele(i));

		if(partition2 < (parent1.getLength() - 1))
			for(int i = (partition2 + 1); i < parent1.getLength(); i++)
				if(!Service.isInGeneticCode(childgeneticCode, ((IntegerAllele) parent2.getAllele(i)).getGene()))
					childgeneticCode.set(++pointerEnd, (IntegerAllele) parent2.getAllele(i));

		for(int i = 0; i <= partition2; i++)
			if(!Service.isInGeneticCode(childgeneticCode, ((IntegerAllele) parent2.getAllele(i)).getGene()))
				if(pointerEnd < (parent1.getLength() - 1))
					childgeneticCode.set(++pointerEnd, (IntegerAllele) parent2.getAllele(i));
				else if(partition1 > 0)
					childgeneticCode.set(++pointerStart, (IntegerAllele) parent2.getAllele(i));
				else break;

		return new Chromosome(childgeneticCode);
	}
}
