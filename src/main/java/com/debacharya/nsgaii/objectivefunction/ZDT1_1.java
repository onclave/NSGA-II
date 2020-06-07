package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.ValueAllele;

import java.util.stream.Collectors;

public class ZDT1_1 extends AbstractObjectiveFunction {

	private static final String VALUE_ALLELE_INSTANCE_ERROR = "ZDT1 works with ValueAllele only. Please implement your own objective "		+
																"function class by extending the AbstractObjectiveFunction class to get "	+
																"your desired results or use genetic code of type ValueAllele to use this "	+
																"default implementation";

	public ZDT1_1() {
		this.objectiveFunctionTitle = "x1";
	}

	@Override
	public double getValue(Chromosome chromosome) {

		if(!(chromosome.getAllele(0) instanceof ValueAllele))
			throw new UnsupportedOperationException(ZDT1_1.VALUE_ALLELE_INSTANCE_ERROR);

		return chromosome.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList()).get(0).getGene();
	}
}
