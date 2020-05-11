package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Population;

@FunctionalInterface
public interface ChildPopulationProducer {
	Population produce(Population parentPopulation, AbstractCrossover crossover, AbstractMutation mutation, int populationSize);
}
