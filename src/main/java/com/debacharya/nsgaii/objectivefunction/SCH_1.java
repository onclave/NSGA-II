package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.plugin.FitnessCalculator;

public class SCH_1 extends AbstractObjectiveFunction {

	public SCH_1(FitnessCalculator fitnessCalculator) {
		super(fitnessCalculator);
		this.objectiveFunctionTitle = "pow(x, 2)";
	}

	@Override
	public double getValue(Chromosome chromosome) {
		return Service.roundOff(
			Math.pow(this.fitnessCalculator.calculate(chromosome), 2),
			4
		);
	}
}
