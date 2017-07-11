/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.datastructure;

/**
 *
 * @author sajib
 */
public class ParetoObject {
    
    private Chromosome chromosome = null;
    private double crowdingDistance = -1f;
    
    public ParetoObject(Chromosome chromosome) {
        this(chromosome, -1f);
    }
    
    public ParetoObject(Chromosome chromosome, float crowdingDistance) {
        this.chromosome = chromosome;
        this.crowdingDistance = crowdingDistance;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public double getCrowdingDistance() {
        return crowdingDistance;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.crowdingDistance = crowdingDistance;
    }
}
