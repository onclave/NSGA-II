package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.plugin.FitnessCalculator;

public abstract class AbstractObjectiveFunction {

	protected String objectiveFunctionTitle = "Objective Title Not Implemented";
	protected FitnessCalculator fitnessCalculator;

	public AbstractObjectiveFunction() {}

	public AbstractObjectiveFunction(FitnessCalculator fitnessCalculator) {
		this.fitnessCalculator = fitnessCalculator;
	}

	public abstract double getValue(Chromosome chromosome);

	public String getObjectiveTitle() {
		return this.objectiveFunctionTitle;
	}
}
