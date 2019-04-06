/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.datastructure;

import io.onclave.nsga.ii.api.Configuration;
import io.onclave.nsga.ii.api.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Chromosome {
    
    private List<Chromosome> dominatedChromosomes = new ArrayList<>();
    private final List<Double> objectiveValues = new ArrayList<>();
    private double crowdingDistance = 0;
    private final Allele[] geneticCode;
    private int dominationCount = 0;
    private double fitness;
    private int rank;
    
    private Chromosome(final Chromosome chromosome) {
        
        this.geneticCode = new Allele[Configuration.CHROMOSOME_LENGTH];
        
        for(int i = 0; i < Configuration.CHROMOSOME_LENGTH; i++) this.geneticCode[i] = new Allele(chromosome.getGeneticCode()[i].getGene());
        for(int i = 0; i < Configuration.objectives.size(); i++) this.objectiveValues.add(chromosome.getObjectiveValues().get(i));
    }
    
    public Chromosome(final Allele[] geneticCode) {
        
        this.geneticCode = geneticCode;
        this.fitness = Service.calculateFitness(this.geneticCode);
        
        for(int index = 0; index < Configuration.objectives.size(); index++) {
            this.objectiveValues.add(index, Configuration.objectives.get(index).getObjectiveValue(this.fitness));
        }
    }
    
    public void reset() {
        
        this.dominationCount = 0;
        this.rank = Integer.MAX_VALUE;
        this.dominatedChromosomes = new ArrayList<>();
    }
    
    public Chromosome getCopy() {
        return new Chromosome(this);
    }
    
    public void setDominatedChromosome(final Chromosome chromosome) {
        this.dominatedChromosomes.add(chromosome);
    }
    
    public void incrementDominationCount(int incrementValue) {
        this.dominationCount += incrementValue;
    }

    public Allele[] getGeneticCode() {
        return geneticCode;
    }

    public List<Double> getObjectiveValues() {
        return objectiveValues;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }

    public int getDominationCount() {
        return dominationCount;
    }

    public List<Chromosome> getDominatedChromosomes() {
        return dominatedChromosomes;
    }
    
    @Override
    public String toString() {
        return null;
    }
}
