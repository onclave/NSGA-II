/*
 * This repository / codebase is Open Source and free for use and rewrite.
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
