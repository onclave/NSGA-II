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
import io.onclave.nsga.ii.datastructure.ParetoObject;
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
        
        Configuration.buildObjectives();
        
        Population initialSortedParentPopulation = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation());
        Population initialChildPopulation = Synthesis.synthesizeChild(initialSortedParentPopulation);
        
        Population parent = initialSortedParentPopulation;
        Population child = initialChildPopulation;
        
        for(int i = 2; i <= Configuration.getGENERATIONS(); i++) {
            
            Population combinedPopulation = Service.createCombinedPopulation(parent, child);
            HashMap<Integer, List<Chromosome>> paretoFront = Service.fastNonDominatedSort(combinedPopulation);
            
            Population nextChildPopulation = new Population();
            List<Chromosome> partialPopulace = new ArrayList<>();
            List<ParetoObject> latestFront = null;
            
            for(int j = 1; j < paretoFront.size(); j++) {
                
                List<Chromosome> singularFront = paretoFront.get(j);
                latestFront = Service.crowdingDistanceAssignment(singularFront);
                
                if(singularFront.size() < Configuration.getPOPULATION_SIZE()) singularFront.stream().forEach((chromosome) -> {
                    partialPopulace.add(chromosome);
                }); else if(singularFront.size() >= (Configuration.getPOPULATION_SIZE() - partialPopulace.size())) break;
            }
            
            int iterator = -1;
            
            if(latestFront != null) {
                latestFront = Service.crowdComparisonSort(latestFront);
                while((partialPopulace.size() < Configuration.getPOPULATION_SIZE()) && (++iterator > -1)) partialPopulace.add(latestFront.get(iterator).getChromosome());
            }
            
            nextChildPopulation.setPopulace(partialPopulace);
            
            if(i < Configuration.getGENERATIONS()) {
                parent = child;
                child = Synthesis.synthesizeChild(nextChildPopulation);
            }
        }
    }
}
