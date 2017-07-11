/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.datastructure;

import java.util.List;

/**
 *
 * @author sajib
 */
public class Population {
    
    public Population() {
        this(null);
    }
    
    public Population(final List<Chromosome> populace) {
        this.populace = populace;
    }
    
    private List<Chromosome> populace;

    public List<Chromosome> getPopulace() {
        return populace;
    }

    public void setPopulace(List<Chromosome> populace) {
        this.populace = populace;
    }
}
