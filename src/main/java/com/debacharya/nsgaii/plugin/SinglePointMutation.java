package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class SinglePointMutation extends AbstractMutation {

	private static final String BOOLEAN_ALLELE_INSTANCE_ERROR = "SinglePointMutation only works with BooleanAllele only. "								+
																"Please implement your own Mutation class by extending the AbstractMutation class "		+
																"to get your desired results.";

	public SinglePointMutation() {
		super();
	}

	public SinglePointMutation(float mutationProbability) {
		super(mutationProbability);
	}

	@Override
	public Chromosome perform(Chromosome chromosome) {

		for(AbstractAllele allele : chromosome.getGeneticCode())
			if(!(allele instanceof BooleanAllele))
				throw new UnsupportedOperationException(SinglePointMutation.BOOLEAN_ALLELE_INSTANCE_ERROR);

		List<BooleanAllele> booleanGeneticCode = new ArrayList<>();

		for(int i = 0; i < chromosome.getLength(); i++)
			booleanGeneticCode.add(
				i, new BooleanAllele(
					this.shouldPerformMutation()										?
						!((BooleanAllele) chromosome.getGeneticCode().get(i)).getGene()	:
						((BooleanAllele) chromosome.getGeneticCode().get(i)).getGene()
				)
			);

		return new Chromosome(new ArrayList<>(booleanGeneticCode));
	}
}
