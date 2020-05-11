package com.debacharya.nsgaii.datastructure;

import com.debacharya.nsgaii.Service;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {

	private final List<Double> objectiveValues;
	private final List<Double> normalizedObjectiveValues;
	private final List<AbstractAllele> geneticCode;
	private  List<Chromosome> dominatedChromosomes;
	private double crowdingDistance = 0;
	private int dominatedCount = 0;
	private double fitness = Double.MIN_VALUE;
	private int rank = -1;

	public Chromosome(List<? extends AbstractAllele> geneticCode) {

		this.geneticCode = new ArrayList<>();
		this.objectiveValues = new ArrayList<>();
		this.normalizedObjectiveValues = new ArrayList<>();
		this.dominatedChromosomes = new ArrayList<>();

		for(AbstractAllele allele : geneticCode)
			this.geneticCode.add(allele.getCopy());
	}

	public Chromosome(Chromosome chromosome) {

		this(chromosome.geneticCode);

		for(int i = 0; i < chromosome.objectiveValues.size(); i++)
			this.objectiveValues.add(i, chromosome.objectiveValues.get(i));

		this.crowdingDistance = chromosome.crowdingDistance;
		this.dominatedCount = chromosome.dominatedCount;
		this.fitness = chromosome.fitness;
		this.rank = chromosome.rank;
	}

	public void addDominatedChromosome(Chromosome chromosome) {
		this.dominatedChromosomes.add(chromosome);
	}

	public void incrementDominatedCount(int incrementValue) {
		this.dominatedCount += incrementValue;
	}

	public List<Chromosome> getDominatedChromosomes() {
		return dominatedChromosomes;
	}

	public void setDominatedChromosomes(List<Chromosome> dominatedChromosomes) {
		this.dominatedChromosomes = dominatedChromosomes;
	}

	public List<Double> getObjectiveValues() {
		return objectiveValues;
	}

	public void addObjectiveValue(int index, double value) {

		double roundedValue = Service.roundOff(value, 4);

		if(this.getObjectiveValues().size() <= index) this.objectiveValues.add(index, roundedValue);
		else this.objectiveValues.set(index, roundedValue);
	}

	public List<Double> getNormalizedObjectiveValues() {
		return this.normalizedObjectiveValues;
	}

	public void setNormalizedObjectiveValue(int index, double value) {

		if(this.getNormalizedObjectiveValues().size() <= index) this.normalizedObjectiveValues.add(index, value);
		else this.normalizedObjectiveValues.set(index, value);
	}

	public List<AbstractAllele> getGeneticCode() {
		return geneticCode;
	}

	public double getCrowdingDistance() {
		return crowdingDistance;
	}

	public void setCrowdingDistance(double crowdingDistance) {
		this.crowdingDistance = crowdingDistance;
	}

	public int getDominatedCount() {
		return dominatedCount;
	}

	public void setDominatedCount(int dominationCount) {
		this.dominatedCount = dominationCount;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getLength() {
		return this.geneticCode.size();
	}

	public Chromosome getCopy() {
		return new Chromosome(this);
	}

	public void reset() {
		this.dominatedCount = 0;
		this.rank = Integer.MAX_VALUE;
		this.dominatedChromosomes = new ArrayList<>();
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder("Objective values: [");

		for(double value : this.objectiveValues)
			response.append(value).append(" ");

		response
			.append("] | Rank: ")
			.append(this.rank)
			.append(" | Crowding Distance: ")
			.append(this.crowdingDistance);

		return response.toString();
	}
}
