package com.debacharya.nsgaii.datastructure;

import java.util.List;

public class Population {

	private final List<Chromosome> populace;

	public Population(final List<Chromosome> populace) {
		this.populace = populace;
	}

	public List<Chromosome> getPopulace() {
		return populace;
	}

	public int size() {
		return this.populace.size();
	}

	public Chromosome get(int index) {
		return this.populace.get(index);
	}

	public Chromosome getLast() {
		return this.populace.get(this.populace.size() - 1);
	}

	public double selectMaximumNormalizedObjectiveValue(int objectiveIndex) {

		double result = this.populace.get(0).getNormalizedObjectiveValues().get(objectiveIndex);

		for(Chromosome chromosome : this.populace)
			if(chromosome.getNormalizedObjectiveValues().get(objectiveIndex) > result)
				result = chromosome.getNormalizedObjectiveValues().get(objectiveIndex);

		return result;
	}

	public double selectMinimumNormalizedObjectiveValue(int objectiveIndex) {

		double result = this.populace.get(0).getNormalizedObjectiveValues().get(objectiveIndex);

		for(Chromosome chromosome : this.populace)
			if(chromosome.getNormalizedObjectiveValues().get(objectiveIndex) < result)
				result = chromosome.getNormalizedObjectiveValues().get(objectiveIndex);

		return result;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		for(Chromosome chromosome : this.populace)
			response.append(chromosome.toString()).append("\n");

		return response.toString();
	}
}
