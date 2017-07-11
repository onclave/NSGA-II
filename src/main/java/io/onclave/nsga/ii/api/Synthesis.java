/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author sajib
 */
public class Synthesis {
    
    private static final Random LOCAL_RANDOM = new Random();
    
    public static Population syntesizePopulation() {
        
        List<Chromosome> populace = new ArrayList<>();
        
        for(int i = 0; i < Configuration.getPOPULATION_SIZE(); i++) {
            
            Chromosome chromosome = new Chromosome();
            chromosome.setGeneticCode(synthesizeGeneticCode(Configuration.getCHROMOSOME_LENGTH()));
            populace.add(chromosome);
        }
        
        return new Population(populace);
    }
    
    public static Population synthesizeChild(Population parent) {
        
        Population child = new Population();
        
        return child;
    }
    
    private static Allele[] synthesizeGeneticCode(final int length) {
        
        Allele[] geneticCode = new Allele[length];
        
        for(int i = 0; i < length; i++) geneticCode[i] = synthesizeAllele();
        
        return geneticCode;
    }
    
    private static Allele synthesizeAllele() {
        return new Allele(LOCAL_RANDOM.nextBoolean());
    }
}
