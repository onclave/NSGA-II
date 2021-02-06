package com.debacharya.nsgaii.crossover;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.datastructure.ValueAllele;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SimulatedBinaryCrossover extends AbstractCrossover {

	private final double distributionIndex;

	public SimulatedBinaryCrossover(CrossoverParticipantCreator crossoverParticipantCreator, double distributionIndex) {
		super(crossoverParticipantCreator);
		this.distributionIndex = distributionIndex;
	}

	public SimulatedBinaryCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
		this(crossoverParticipantCreator, 20);
	}

	@Override
	public List<Chromosome> perform(Population population) {

		List<Chromosome> result = new ArrayList<>();
		List<Chromosome> selected = this.crossoverParticipantCreator.create(population);

		if(this.shouldPerformCrossover())
			result = this.prepareChildChromosomes(
				selected.get(0),
				selected.get(1)
			);
		else {
			result.add(selected.get(0).getCopy());
			result.add(selected.get(1).getCopy());
		}

		return result;
	}

	private List<Chromosome> prepareChildChromosomes(Chromosome parent1, Chromosome parent2) {

		List<Chromosome> result = new ArrayList<>();
		List<AbstractAllele> child1GeneticCode = new ArrayList<>();
		List<AbstractAllele> child2GeneticCode = new ArrayList<>();
		List<ValueAllele> geneticCode1 = parent1.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList());
		List<ValueAllele> geneticCode2 = parent2.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList());

		for(int i = 0; i < geneticCode1.size(); i++) {

			double beta = Service.roundOff(this.generateBeta(), 4);
			double x1 = Service.roundOff((geneticCode1.get(i).getGene() + geneticCode2.get(i).getGene()) / 2, 4);
			double x2 = Service.roundOff(Math.abs(geneticCode1.get(i).getGene() - geneticCode2.get(i).getGene()) / 2, 4);

			child1GeneticCode.add(i, new ValueAllele(
				Service.roundOff((x1 + (beta * x2)), 4)
				)
			);

			child2GeneticCode.add(i, new ValueAllele(
				Service.roundOff((x1 - (beta * x2)), 4)
				)
			);
		}

		result.add(0, new Chromosome(child1GeneticCode));
		result.add(1, new Chromosome(child2GeneticCode));

		return result;
	}

	private double generateBeta() {

		double probability = ThreadLocalRandom.current().nextDouble();

		return (probability <= 0.5d)
			? Math.pow(
				2 * probability,
				1 / (this.distributionIndex + 1)
			)
			: Math.pow(
				2 * (1 - probability),
				-1 / (this.distributionIndex + 1)
		);
	}
}
