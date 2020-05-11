package com.debacharya.nsgaii.datastructure;

public class BooleanAllele extends AbstractAllele {

	public BooleanAllele(boolean gene) {
		super(gene);
	}

	@Override
	public Boolean getGene() {
		return (Boolean) this.gene;
	}

	@Override
	public AbstractAllele getCopy() {
		return new BooleanAllele((Boolean) this.gene);
	}

	@Override
	public String toString() {
		return ((Boolean) this.gene ? "1" : "0");
	}
}
