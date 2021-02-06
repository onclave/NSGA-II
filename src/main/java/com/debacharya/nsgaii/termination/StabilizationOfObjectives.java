package com.debacharya.nsgaii.termination;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;

public class StabilizationOfObjectives implements TerminatingCriterion {

	private final double stabilizationThreshold;
	private final int latestGenerationsToConsider;
	private final List<Double> avgObjectives = new ArrayList<>();
	private final boolean shouldRoundOff;
	private final double roundOffDecimalPlace;

	public StabilizationOfObjectives() {
		this(0.015d, 4);
	}

	public StabilizationOfObjectives(double stabilizationThreshold) {
		this(stabilizationThreshold, 4);
	}

	public StabilizationOfObjectives(int latestGenerationsToConsider) {
		this(0.015d, latestGenerationsToConsider);
	}

	public StabilizationOfObjectives(double stabilizationThreshold, int latestGenerationsToConsider) {
		this(stabilizationThreshold, latestGenerationsToConsider, true, 4);
	}

	public StabilizationOfObjectives(double stabilizationThreshold,
									 int latestGenerationsToConsider,
									 boolean shouldRoundOff,
									 double roundOffDecimalPlace) {
		this.stabilizationThreshold = stabilizationThreshold;
		this.latestGenerationsToConsider = latestGenerationsToConsider;
		this.shouldRoundOff = shouldRoundOff;
		this.roundOffDecimalPlace = roundOffDecimalPlace;
	}

	@Override
	public boolean shouldRun(Population population, int generationCount, int maxGenerations) {

		if((maxGenerations > 0) && (maxGenerations < generationCount))
			return false;

		this.avgObjectives.add(population
			.getPopulace()
			.stream()
			.filter(e -> e.getRank() == 1)
			.mapToDouble(Chromosome::getAvgObjectiveValue)
			.summaryStatistics()
			.getAverage()
		);

		if(generationCount < this.latestGenerationsToConsider)
			return true;

		List<Double> avgObjectivesToConsider = this.avgObjectives.subList(
			Math.max(this.avgObjectives.size() - this.latestGenerationsToConsider, 0),
			this.avgObjectives.size()
		);

		for(int i = 1; i < avgObjectivesToConsider.size(); i++)
			if((this.shouldRoundOff
				? Service.roundOff(
					Math.abs(avgObjectivesToConsider.get(i - 1) - avgObjectivesToConsider.get(i)),
					this.roundOffDecimalPlace
				) : Math.abs(avgObjectivesToConsider.get(i - 1) - avgObjectivesToConsider.get(i))) > this.stabilizationThreshold)
				return true;

		return false;
	}
}
