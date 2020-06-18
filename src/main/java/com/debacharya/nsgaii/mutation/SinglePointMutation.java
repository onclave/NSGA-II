/*
 * MIT License
 *
 * Copyright (c) 2019 Debabrata Acharya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.debacharya.nsgaii.mutation;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class SinglePointMutation extends AbstractMutation {

	private static final String BOOLEAN_ALLELE_INSTANCE_ERROR = "SinglePointMutation works with BooleanAllele only. "		+
																"Please implement your own Mutation class by extending "	+
																"the AbstractMutation class to get your desired results.";

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
					this.shouldPerformMutation()								?
						!((BooleanAllele) chromosome.getAllele(i)).getGene()	:
						((BooleanAllele) chromosome.getAllele(i)).getGene()
				)
			);

		return new Chromosome(new ArrayList<>(booleanGeneticCode));
	}
}
