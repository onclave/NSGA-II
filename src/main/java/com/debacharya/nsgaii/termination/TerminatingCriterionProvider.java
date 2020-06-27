package com.debacharya.nsgaii.termination;

import com.debacharya.nsgaii.termination.TerminatingCriterion;

public class TerminatingCriterionProvider {

	/*
	 * The most commonly used stopping criterion in Evolutionary Multi-objective Algorithms is an a priori fixed number of generations
	 * (or evaluations).
	 */
	public static TerminatingCriterion fixedTerminatingCriterion() {
		return (population, generationCount, maxGenerations) -> (generationCount <= maxGenerations);
	}
}
