package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Chromosome;

@FunctionalInterface
public interface FitnessCalculator {
	double calculate(Chromosome chromosome);
}
