package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.List;

@FunctionalInterface
public interface CrossoverParticipantCreator {
	List<Chromosome> create(Population population);
}
