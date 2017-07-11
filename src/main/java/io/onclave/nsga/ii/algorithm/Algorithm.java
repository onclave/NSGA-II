/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.algorithm;

import io.onclave.nsga.ii.api.Service;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sajib
 */
public class Algorithm {
    
    public static void main(String[] args) {
        
        Population initialSortedParentPopulation = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation());
        Population initialChild = Synthesis.synthesizeChild(initialSortedParentPopulation);
        
        for(int i = 2; i <= Configuration.getGENERATIONS(); i++) {
            
            List<Chromosome> combinedPopulace = new ArrayList<>();
            Population combinedPopulation = new Population();
            
            combinedPopulace.addAll(initialSortedParentPopulation.getPopulace());
            combinedPopulace.addAll(initialChild.getPopulace());
            combinedPopulation.setPopulace(combinedPopulace);
            
            HashMap<Integer, List<Chromosome>> paretoFront = Service.fastNonDominatedSort(combinedPopulation, Configuration.buildObjectives());
        }
    }
}
