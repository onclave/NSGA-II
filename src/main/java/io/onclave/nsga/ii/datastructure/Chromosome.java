/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.datastructure;

import io.onclave.nsga.ii.api.Service;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajib
 */
public class Chromosome {
    
    public Chromosome() {
        this(-9999d);
    }
    
    public Chromosome(final double fitness) {
        this(null, fitness, "");
    }
    
    public Chromosome(final Allele[] geneticCode, final double fitness, final String extraInfo) {
        this(geneticCode, fitness, extraInfo, 0);
        
    }
    
    public Chromosome(final Allele[] geneticCode, final double fitness, final String extraInfo, final int rank) {
        
        this.geneticCode = geneticCode;
        this.fitness = fitness;
        this.extraInfo = extraInfo;
        this.dominationRank = rank;
        this.uniqueID = Long.toString(System.currentTimeMillis()) + "-" + Integer.toString(Service.generateRandomInt());
        this.dominatedChromosomes = new ArrayList<>();
    }
    
    private Allele[] geneticCode;
    private double fitness;
    private String extraInfo;
    private int dominationRank = 0;
    private String uniqueID;
    private List<Chromosome> dominatedChromosomes;

    public List<Chromosome> getDominatedChromosomes() {
        return dominatedChromosomes;
    }

    public void setDominatedChromosomes(List<Chromosome> dominatedChromosomes) {
        this.dominatedChromosomes = dominatedChromosomes;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getDominationRank() {
        return dominationRank;
    }

    public void setDominationRank(int dominationRank) {
        this.dominationRank = dominationRank;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Allele[] getGeneticCode() {
        return geneticCode;
    }

    public void setGeneticCode(Allele[] geneticCode) {
        this.geneticCode = geneticCode;
        this.setFitness(Service.calculateFitness(geneticCode));
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
