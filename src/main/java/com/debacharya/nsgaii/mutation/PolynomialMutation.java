package com.debacharya.nsgaii.mutation;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.ValueAllele;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PolynomialMutation extends AbstractMutation {

	private final double origin;
	private final double bound;
	private final double distributionIndex;

	public PolynomialMutation(double origin, double bound, double distributionIndex, float mutationProbability) {
		super(mutationProbability);
		this.origin = origin;
		this.bound = bound;
		this.distributionIndex = distributionIndex;
	}

	public PolynomialMutation(double origin, double bound, double distributionIndex) {
		super();
		this.origin = origin;
		this.bound = bound;
		this.distributionIndex = distributionIndex;
	}

	public PolynomialMutation(double origin, double bound) {
		this(origin, bound, 20);
	}

	public PolynomialMutation(double distributionIndex) {
		this(0, 1, distributionIndex);
	}

	public PolynomialMutation(float mutationProbability) {
		this(0, 1, 20, mutationProbability);
	}

	@Override
	public Chromosome perform(Chromosome chromosome) {

		List<ValueAllele> childGeneticCode = new ArrayList<>();
		List<ValueAllele> parentGeneticCode = chromosome.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList());

		for(int i = 0; i < parentGeneticCode.size(); i++)
			childGeneticCode.add(i, new ValueAllele(
				this.shouldPerformMutation() ?
					this.getMutatedValue(parentGeneticCode.get(i).getGene()) :
					parentGeneticCode.get(i).getGene()
			));

		return new Chromosome(new ArrayList<>(childGeneticCode));
	}

	private double getMutatedValue(double originalValue) {

		double probability = ThreadLocalRandom.current().nextDouble();
		double delta = Service.roundOff(this.generateDelta(probability), 4);
		double result;

		if(probability < 0.5)
			result = originalValue + (delta * (originalValue - this.origin));
		else
			result = originalValue + (delta * (this.bound - originalValue));

		if(result < this.origin)
			result = this.origin;
		else if(result > this.bound)
			result = this.bound;

		return Service.roundOff(result, 4);
	}

	private double generateDelta(double probability) {
		return (probability < 0.5)
			? (Math.pow(
				2 * probability,
				1 / (this.distributionIndex + 1)
			) - 1)
			: (1 - Math.pow(
				2 * (1 - probability),
				1 / (this.distributionIndex + 1)
			));
	}
}
