package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.objectivefunction.SCH_1;
import com.debacharya.nsgaii.objectivefunction.SCH_2;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveProvider {

	public static List<AbstractObjectiveFunction> provideSCHObjectives(int chromosomeLength) {
		return  ObjectiveProvider.provideSCHObjectives(
			FitnessCalculatorProvider.normalizedGeneticCodeValue(
				0,
				Math.pow(2, chromosomeLength) - 1,
				0,
				2
			)
		);
	}

	public static List<AbstractObjectiveFunction> provideSCHObjectives(FitnessCalculator fitnessCalculator) {

		List<AbstractObjectiveFunction> objectives = new ArrayList<>();

		objectives.add(new SCH_1(fitnessCalculator));
		objectives.add(new SCH_2(fitnessCalculator));

		return objectives;
	}
}
