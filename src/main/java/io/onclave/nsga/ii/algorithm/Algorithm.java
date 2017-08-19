/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.algorithm;

import io.onclave.nsga.ii.api.Service;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;
import io.onclave.nsga.ii.datastructure.Population;
import io.onclave.nsga.ii.objectivefunction.SCH_1;
import io.onclave.nsga.ii.objectivefunction.SCH_2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sajib
 */
public class Algorithm {
    
    public static void main(String[] args) { p("\n\nGeneration : 1\n\n");
        
        Configuration.buildObjectives();
        
        Population parent = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation());
        Population child = Synthesis.synthesizeChild(parent);
        
        for(int i = 2; i <= Configuration.getGENERATIONS(); i++) { p("\n\nGeneration : " + i + "\n\n");
            
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
                } else {
//                    if(singularFront == null) p("\n\n--------" + j + "th front was null--------\n\n");
//                    else if(usableSpace <= 0) p("\n\n--------usable space was unavailable--------\n\n");
//                    else p("\n\n--------empty front--------\n\n");
                    break;
                }
            }
            
            nextChildPopulation.setPopulace(childPopulace);
            
            if(i < Configuration.getGENERATIONS()) {
                parent = child;
                child = Synthesis.synthesizeChild(nextChildPopulation);
            } else {
                
                SCH_1 sch1 = new SCH_1();
                SCH_2 sch2 = new SCH_2();
                
                for(Chromosome c : child.getPopulace()) {
                    System.out.println("(" + sch1.objectiveFunction(c) + ", " + sch2.objectiveFunction(c) + ")");
                }
            }
        }
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
