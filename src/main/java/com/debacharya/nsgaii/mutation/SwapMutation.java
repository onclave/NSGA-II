package com.debacharya.nsgaii.mutation;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class SwapMutation extends AbstractMutation {

	private static final String TOO_MANY_SWAP_POSITIONS = "The number of swaps provided is more than the total length of the chromosome." +
															" Swap Mutation is hence not possible here.";

	final int swaps;

	public SwapMutation() {
		this(1);
	}

	public SwapMutation(float mutationProbability) {
		this(mutationProbability, 1);
	}

	public SwapMutation(int swaps) {
		super();
		this.swaps = swaps;
	}

	public SwapMutation(float mutationProbability, int swaps) {
		super(mutationProbability);
		this.swaps = swaps;
	}

	@Override
	public Chromosome perform(Chromosome chromosome) {

		int totalSwaps = this.swaps * 2;

		if(chromosome.getLength() < totalSwaps)
			throw new UnsupportedOperationException(SwapMutation.TOO_MANY_SWAP_POSITIONS);

		List<Integer> randomPositions = Service.generateUniqueRandomNumbers(totalSwaps, chromosome.getLength());
		int halfSize = randomPositions.size() / 2;

		for(int i = 0; i < halfSize; i++) {

			int j = i + halfSize;
			int p1 = randomPositions.get(i);
			int p2 = randomPositions.get(j);

			AbstractAllele temp = chromosome.getAllele(p1).getCopy();

			chromosome.setAllele(p1, chromosome.getAllele(p2));
			chromosome.setAllele(p2, temp);
		}

		return new Chromosome(new ArrayList<>(chromosome.getGeneticCode()));
	}
}
