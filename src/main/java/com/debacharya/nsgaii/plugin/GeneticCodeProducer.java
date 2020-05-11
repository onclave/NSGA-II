package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.AbstractAllele;

import java.util.List;

@FunctionalInterface
public interface GeneticCodeProducer {
	List<? extends AbstractAllele> produce(int length);
}
