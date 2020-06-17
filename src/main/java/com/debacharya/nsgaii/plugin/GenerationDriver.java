package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Population;

@FunctionalInterface
public interface GenerationDriver {
	boolean shouldRun(Population population, int generationCount, int maxGenerations, Object extras);
}
