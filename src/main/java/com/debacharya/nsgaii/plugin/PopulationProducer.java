package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Population;

@FunctionalInterface
public interface PopulationProducer {
	Population produce(int populationSize, int chromosomeLength, GeneticCodeProducer geneticCodeProducer, FitnessCalculator fitnessCalculator);
}
