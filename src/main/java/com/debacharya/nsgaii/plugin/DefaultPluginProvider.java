package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultPluginProvider {

	public static GeneticCodeProducer defaultGeneticCodeProducer() {
		return (length) -> {

			List<BooleanAllele> geneticCode = new ArrayList<>();

			for(int i = 0; i < length; i++)
				geneticCode.add(i, new BooleanAllele(ThreadLocalRandom.current().nextBoolean()));

			return geneticCode;
		};
	}

	public static PopulationProducer defaultPopulationProducer() {
		return (populationSize, chromosomeLength, geneticCodeProducer, fitnessCalculator) -> {

			List<Chromosome> populace = new ArrayList<>();

			for(int i = 0; i < populationSize; i++)
				populace.add(
					new Chromosome(
						geneticCodeProducer.produce(chromosomeLength)
					)
				);

			return new Population(populace);
		};
	}

	public static ChildPopulationProducer defaultChildPopulationProducer() {
		return (parentPopulation, crossover, mutation, populationSize) -> {

			List<Chromosome> populace = new ArrayList<>();

			while(populace.size() < populationSize)
				if((populationSize - populace.size()) == 1)
					populace.add(
						mutation.perform(
							Service.crowdedBinaryTournamentSelection(parentPopulation)
						)
					);
				else
					for(Chromosome chromosome : crossover.perform(parentPopulation))
						populace.add(mutation.perform(chromosome));

			return new Population(populace);
		};
	}
}
