package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMutation {

	protected float mutationProbability = 0.03f;

	public AbstractMutation() {}

	public AbstractMutation(float mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public abstract Chromosome perform(Chromosome chromosome);

	public boolean shouldPerformMutation() {
		return ThreadLocalRandom.current().nextFloat() <= this.mutationProbability;
	}
}
