package com.debacharya.nsgaii.plugin;

public class GenerationDriverProvider {

	public static GenerationDriver provideSimpleGenerationDriver() {
		return (population, generationCount, maxGenerations, extras) -> (generationCount <= maxGenerations);
	}
}
