/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.datastructure;

/**
 * this is a simulation of an allele in a biological chromosome that contains a gene value.
 * an array of alleles create the genetic code for the chromosome.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.0
 * @since   0.1
 */
public class Allele {
    
    public Allele() {
        this(false);
    }
    
    public Allele(final boolean gene) {
        this.gene = gene;
    }
    
    private boolean gene;

    public boolean getGene() {
        return gene;
    }

    public void setGene(boolean gene) {
        this.gene = gene;
    }
    
    public void bitFlip() {
        this.gene = !this.gene;
    }
}
