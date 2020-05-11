/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.datastructure;

import java.util.List;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Population {
    
    private final List<Chromosome> populace;
    
    public Population(final List<Chromosome> populace) {
        this.populace = populace;
    }

    public List<Chromosome> getPopulace() {
        return populace;
    }
}
