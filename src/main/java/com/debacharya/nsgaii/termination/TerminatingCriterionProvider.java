package com.debacharya.nsgaii.termination;

public class TerminatingCriterionProvider {

	/*
	 * The most commonly used stopping criterion in Evolutionary Multi-objective Algorithms is an a priori fixed number of generations
	 * (or evaluations).
	 */
	public static TerminatingCriterion fixedTerminatingCriterion() {
		return (population, generationCount, maxGenerations) -> (generationCount <= maxGenerations);
	}
}
