package com.debacharya.nsgaii.termination;

import com.debacharya.nsgaii.datastructure.Population;

@FunctionalInterface
public interface TerminatingCriterion {
	boolean shouldRun(Population population, int generationCount, int maxGenerations);
}
