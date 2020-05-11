package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractCrossover {

	protected final CrossoverParticipantCreator crossoverParticipantCreator;

	protected float crossoverProbability = 0.7f;

	public AbstractCrossover(CrossoverParticipantCreator crossoverParticipantCreator) {
		this.crossoverParticipantCreator = crossoverParticipantCreator;
	}

	public abstract List<Chromosome> perform(Population population);

	public boolean shouldPerformCrossover() {
		return ThreadLocalRandom.current().nextFloat() <= this.crossoverProbability;
	}
}
