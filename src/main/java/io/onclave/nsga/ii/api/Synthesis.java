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
        List<Chromosome> populace = new ArrayList<>();
        
        while(populace.size() < Configuration.getPOPULATION_SIZE()) {
            
            Chromosome[] childChromosomes = crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent));
            
            for(Chromosome chromosome : childChromosomes) populace.add(mutation(chromosome));
        }
        
        child.setPopulace(populace);
        
        return child;
    }
    
    private static Chromosome binaryTournamentSelection(Population population) {
        
        Chromosome individual1 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));
        Chromosome individual2 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));
        
        if(individual1.getFitness() > individual2.getFitness()) return individual1; else return individual2;
    }
    
    private static Chromosome[] crossover(Chromosome chromosome1, Chromosome chromosome2) {
        
        Allele[] geneticCode1 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
        Allele[] geneticCode2 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
        Allele[] chromosome1geneCode = chromosome1.getGeneticCode();
        Allele[] chromosome2geneCode = chromosome2.getGeneticCode();
        Chromosome[] childChromosomes = new Chromosome[2];
        int breakPoint = Configuration.getCHROMOSOME_LENGTH() / 2;
        
        if(LOCAL_RANDOM.nextFloat() <= Configuration.getCROSSOVER_PROBABILITY()) {
            
            for(int i = 0; i < Configuration.getCHROMOSOME_LENGTH(); i++) {
            
                if(i <= breakPoint) {
                    geneticCode1[i] = chromosome1geneCode[i];
                    geneticCode2[i] = chromosome2geneCode[i];
                } else {
                    geneticCode1[i] = chromosome2geneCode[i];
                    geneticCode2[i] = chromosome1geneCode[i];
                }
            }

            childChromosomes[0].setGeneticCode(geneticCode1);
            childChromosomes[1].setGeneticCode(geneticCode2);
        } else {
            childChromosomes[0] = chromosome1;
            childChromosomes[1] = chromosome2;
        }
        
        return childChromosomes;
    }
    
    private static Chromosome mutation(Chromosome chromosome) {
        
        if(LOCAL_RANDOM.nextFloat() <= Configuration.getMUTATION_PROBABILITY()) {
            
            Allele[] geneticCode = chromosome.getGeneticCode();
            geneticCode[LOCAL_RANDOM.nextInt(geneticCode.length)].bitFlip();
            chromosome.setGeneticCode(geneticCode);
        }
        
        return chromosome;
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
