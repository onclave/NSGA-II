package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CrossoverParticipantCreatorProvider {

	public static CrossoverParticipantCreator selectByBinaryTournamentSelection() {
		return population -> {

			List<Chromosome> selected = new ArrayList<>();

			selected.add(Service.crowdedBinaryTournamentSelection(population));
			selected.add(Service.crowdedBinaryTournamentSelection(population));

			return selected;
		};
	}
}
