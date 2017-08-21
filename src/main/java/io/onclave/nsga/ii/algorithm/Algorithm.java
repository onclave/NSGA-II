/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.algorithm;

import io.onclave.nsga.ii.api.Reporter;
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
        
        Population parent = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation());
        Population child = Synthesis.synthesizeChild(parent);
        
        for(int i = 2; i <= Configuration.getGENERATIONS(); i++) {
            
            Population combinedPopulation = Service.createCombinedPopulation(parent, child);
            HashMap<Integer, List<Chromosome>> paretoFront = Service.fastNonDominatedSort(combinedPopulation);
            
            Population nextChildPopulation = new Population();
            List<Chromosome> childPopulace = new ArrayList<>();
            
            for(int j = 1; j <= paretoFront.size(); j++) {
                
                List<Chromosome> singularFront = paretoFront.get(j);
                int usableSpace = Configuration.getPOPULATION_SIZE() - childPopulace.size();
                
                if(singularFront != null && !singularFront.isEmpty() && usableSpace > 0) {
                
                    if(usableSpace >= singularFront.size()) childPopulace.addAll(singularFront);
                    else {
                        
                        List<ParetoObject> latestFront = Service.crowdComparisonSort(Service.crowdingDistanceAssignment(singularFront));
                        
                        for(int k = 0; k < usableSpace; k++) childPopulace.add(latestFront.get(k).getChromosome());
                    }
                } else break;
            }
            
            nextChildPopulation.setPopulace(childPopulace);
            
            if(i < Configuration.getGENERATIONS()) {
                parent = child;
                child = Synthesis.synthesizeChild(nextChildPopulation);
            } else Reporter.render2DGraph(child);
        }
    }
}
