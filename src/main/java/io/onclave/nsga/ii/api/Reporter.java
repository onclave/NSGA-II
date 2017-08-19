/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sajib
 */
public class Reporter {
    
    public static void reportPopulation(Population population) {
        
        int i = 1;
        
        for(Chromosome chromosome : population.getPopulace()) p("Chromosome " + i++ + " : " + chromosome.getUniqueID() + " | " + chromosome.getFitness());
    }
    
    public static void reportGeneticCode(Allele[] geneticCode) {
        
        String geneticString = "";
        
        for(Allele allele : geneticCode) geneticString += allele.getGene() ? "1" : "0";
        
        p("GENETIC CODE : " + geneticString);
    }
    
    public static void reportSingularFront(List<Chromosome> singularFront, int i) {
        
        p("\n\nFRONT : " + i);

        for(Chromosome c : singularFront) {
            int j = 0;
            p("\tmaster : " + c.getUniqueID() + " | " + Double.toString(c.getFitness()));
            for(Chromosome dc : c.getDominatedChromosomes()) p("\t\tslave : " + ++j + " | " + dc.getUniqueID() + " | " + Integer.toString(dc.getDominationRank()));
        }
    }
    
    public static void reportParetoFront(HashMap<Integer, List<Chromosome>> paretoFront) {
        
        int j = 1;
        int max = paretoFront.size();
        
        for(int i = 1; i <= max; i++) {
            
            p("\n\nFRONT : " + i);
            
            int d = 0;
            List<Chromosome> population = paretoFront.get(i);
            
            if(population != null && !population.isEmpty()) {
                
                for(Chromosome c : population) {
                    
                    p("\tmaster : " + c.getUniqueID() + " | " + Double.toString(c.getFitness()));
                    
                    for(Chromosome dc : c.getDominatedChromosomes()) p("\t\tslave : " + ++d + " | " + dc.getUniqueID() + " | " + Integer.toString(dc.getDominationRank()));
                }
            }
        }
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
