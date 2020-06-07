package com.debacharya.nsgaii.objectivefunction;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.ValueAllele;

import java.util.List;
import java.util.stream.Collectors;

public class ZDT1_2 extends AbstractObjectiveFunction {

	private static final String VALUE_ALLELE_INSTANCE_ERROR = "ZDT1 works with ValueAllele only. Please implement your own objective "		+
																"function class by extending the AbstractObjectiveFunction class to get "	+
																"your desired results or use genetic code of type ValueAllele to use this "	+
																"default implementation";

	public ZDT1_2() {
		this.objectiveFunctionTitle = "g(x) [1 - sqrt(x1 / g(x)]";
	}

	@Override
	public double getValue(Chromosome chromosome) {

		if(!(chromosome.getAllele(0) instanceof ValueAllele))
			throw new UnsupportedOperationException(ZDT1_2.VALUE_ALLELE_INSTANCE_ERROR);

		List<ValueAllele> geneticCode = chromosome.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList());
		double size = geneticCode.size();
		double sum = 0;

		for(int i = 1; i < size; i++)
			sum += geneticCode.get(i).getGene();

		double g = 1 + ((9 * sum) / size);
		double f = g * (1 - Math.sqrt(geneticCode.get(0).getGene() / g));

		return f;
	}
}
