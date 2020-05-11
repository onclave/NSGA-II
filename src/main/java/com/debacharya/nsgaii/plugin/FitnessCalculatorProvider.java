package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;

import java.util.stream.Collectors;

public class FitnessCalculatorProvider {

	private static final String NON_BOOLEAN_ALLELE_UNSUPPORTED = "FitnessCalculator.normalizedGeneticCodeValue does not work with AbstractAllele type "			+
																" other than BooleanAllele. If you are implementing a different type of AbstractAllele object " +
																" use your own implementation of FitnessCalculator.";

	public static FitnessCalculator normalizedGeneticCodeValue(double actualMin, double actualMax, double normalizedMin, double normalizedMax) {
		return chromosome -> {

			if(!(chromosome.getGeneticCode().get(0) instanceof BooleanAllele))
				throw new UnsupportedOperationException(FitnessCalculatorProvider.NON_BOOLEAN_ALLELE_UNSUPPORTED);

			return Service.getNormalizedGeneticCodeValue(
					chromosome.getGeneticCode().stream().map(e -> (BooleanAllele) e).collect(Collectors.toList()),
					actualMin,
					actualMax,
					normalizedMin,
					normalizedMax
			);
		};
	}
}
