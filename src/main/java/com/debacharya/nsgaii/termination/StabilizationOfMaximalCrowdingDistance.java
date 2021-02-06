package com.debacharya.nsgaii.termination;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

/*
 * This is the implementation of stabilization of maximal crowding distance as proposed by Olga Rudenko, Marc Schoenauer.
 * A Steady Performance Stopping Criterion for Pareto-based Evolu- tionary Algorithms. 6th International Multi-Objective
 * Programming and Goal Programming Confer- ence, Apr 2004, Hammamet, Tunisia. hal-01909120
 */
public class StabilizationOfMaximalCrowdingDistance implements TerminatingCriterion {

	private final int latestLgenerations;
	private final double threshold;
	private final boolean shouldRoundOff;
	private final double roundOffDecimalPlace;
	private final List<Double> maximalCrowdingDistances;

	public StabilizationOfMaximalCrowdingDistance() {
		this(20);
	}

	public StabilizationOfMaximalCrowdingDistance(int latestLgenerations) {
		this(latestLgenerations, 0.02d);
	}

	public StabilizationOfMaximalCrowdingDistance(int latestLgenerations, double threshold) {
		this(latestLgenerations, threshold, false);
	}

	public StabilizationOfMaximalCrowdingDistance(int latestLgenerations, double threshold, boolean shouldRoundOff) {
		this(latestLgenerations, threshold, shouldRoundOff, 3);
	}

	public StabilizationOfMaximalCrowdingDistance(int latestLgenerations,
												  double threshold,
												  boolean shouldRoundOff,
												  double roundOffDecimalPlace) {
		this.latestLgenerations = latestLgenerations;
		this.threshold = threshold;
		this.shouldRoundOff = shouldRoundOff;
		this.roundOffDecimalPlace = roundOffDecimalPlace;
		this.maximalCrowdingDistances = new ArrayList<>();
	}

	@Override
	public boolean shouldRun(Population population, int generationCount, int maxGenerations) {

		if((maxGenerations > 0) && (maxGenerations < generationCount))
			return false;

		this.maximalCrowdingDistances.add(
			generationCount - 1,
			this.getMaximalCrowdingDistance(population.getPopulace())
		);

		if(generationCount <= this.latestLgenerations)
			return true;

		double stabilityMeasure = Math.sqrt((1.0d / this.latestLgenerations) * this.getSquaredSummation());

		if(this.shouldRoundOff)
			stabilityMeasure = Service.roundOff(stabilityMeasure, this.roundOffDecimalPlace);

		return (stabilityMeasure >= this.threshold);
	}

	private double getSquaredSummation() {

		double result = 0;
		List<Double> latestNmaximalCrowdingDistances = this.maximalCrowdingDistances.subList(
			Math.max(this.maximalCrowdingDistances.size() - this.latestLgenerations, 0),
			this.maximalCrowdingDistances.size()
		);

		double avgMaximalCrowdingDistance = latestNmaximalCrowdingDistances
												.stream()
												.mapToDouble(i -> i)
												.sum() / latestNmaximalCrowdingDistances.size();

		for(double value : latestNmaximalCrowdingDistances)
			result += Math.pow(value - avgMaximalCrowdingDistance, 2);

		return result;
	}

	private double getMaximalCrowdingDistance(List<Chromosome> populace) {

		double maximalCrowdingDistance = Double.NEGATIVE_INFINITY;

		for(Chromosome chromosome : populace)
			if(chromosome.getRank() == 1)
				if(chromosome.getCrowdingDistance() != Double.MAX_VALUE)
					if(chromosome.getCrowdingDistance() > maximalCrowdingDistance)
						maximalCrowdingDistance = chromosome.getCrowdingDistance();

		return maximalCrowdingDistance;
	}
}
