/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.datastructure;

/**
 *
 * @author sajib
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
