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
    
    public static void reportParetoFront(HashMap<Integer, List<Chromosome>> paretoFront) {
        
        int j = 1;
        
        for(int i = 1; i <= paretoFront.size(); i++) {
            
            p("\n FRONT : " + i);
            
            int d = 1;
            List<Chromosome> population = paretoFront.get(i);
            
            for(Chromosome chromosome : population) {
                
                p("Fitness : Chromosome " + j++ + " : " + chromosome.getFitness() + " | DOMINATION RANK : " + chromosome.getDominationRank() + "\n");
                
                for(Chromosome dominated : chromosome.getDominatedChromosomes()) p("\tDOMINATED CHROMOSOME : " + d++ + " : " + dominated.getFitness() + " | DOMINATION RANK : " + chromosome.getDominationRank() + "\n");
            }
        }
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
