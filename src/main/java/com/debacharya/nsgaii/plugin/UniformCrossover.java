package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

public class UniformCrossover extends AbstractCrossover {

	public UniformCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
		super(crossoverParticipantCreator);
	}

	@Override
	public List<Chromosome> perform(Population population) {

		List<Chromosome> result = new ArrayList<>();
		List<Chromosome> selected = this.crossoverParticipantCreator.create(population);

		if(this.shouldPerformCrossover())
			for(int i = 0; i < 2; i++)
				result.add(
					this.prepareChildChromosome(
						selected.get(0),
						selected.get(1)
					)
				);
		else {
			result.add(selected.get(0).getCopy());
			result.add(selected.get(1).getCopy());
		}

		return result;
	}

	private Chromosome prepareChildChromosome(Chromosome chromosome1, Chromosome chromosome2) {

		List<AbstractAllele> geneticCode = new ArrayList<>();

		for(int i = 0; i < chromosome1.getLength(); i++)
			switch (Math.random() <= 0.5 ? 1 : 2) {
				case 1: geneticCode.add(i, chromosome1.getGeneticCode().get(i).getCopy()); break;
				case 2: geneticCode.add(i, chromosome2.getGeneticCode().get(i).getCopy()); break;
			}

		return new Chromosome(geneticCode);
	}
}
