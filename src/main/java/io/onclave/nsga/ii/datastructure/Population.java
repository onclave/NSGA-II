/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.datastructure;

import java.util.List;

/**
 * this is a simulation of a population of chromosome known as a populace.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.0
 * @since   0.1
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
