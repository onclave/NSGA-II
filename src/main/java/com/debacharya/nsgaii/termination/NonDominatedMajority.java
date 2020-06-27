package com.debacharya.nsgaii.termination;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.List;
import java.util.stream.Collectors;

public class NonDominatedMajority implements TerminatingCriterion {

	private final double majority;

	public NonDominatedMajority() {
		this(80.0d);
	}

	public NonDominatedMajority(double majority) {
		this.majority = majority;
	}

	@Override
	public boolean shouldRun(Population population, int generationCount, int maxGenerations) {

		if((maxGenerations > 0) && (maxGenerations < generationCount))
			return false;

		List<Chromosome> flt = population.getPopulace().stream().filter(e -> e.getRank() == 1).collect(Collectors.toList());

		return Service.percent(
			this.majority,
			population.size()
		) > population.getPopulace().stream().filter(e -> e.getRank() == 1).count();
	}
}
