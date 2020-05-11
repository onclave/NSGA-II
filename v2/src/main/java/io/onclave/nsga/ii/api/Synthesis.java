/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Synthesis {
    
    /**
     * this class is never supposed to be instantiated
     */
    private Synthesis() {}
    
    private static final Random LOCAL_RANDOM = new Random();
    
    /**
     * depending on the settings available in the Configuration.java file, this method synthesizes a
     * random population of chromosomes with pseudo-randomly generated genetic code for each chromosome.
     * 
     * @return  a randomly generated population
     */
    public static Population syntesizePopulation() {
        
        List<Chromosome> populace = new ArrayList<>();
        
        /**
         * the number of chromosomes in the population is received from the Configuration.java file
         */
        for(int i = 0; i < Configuration.POPULATION_SIZE; i++) {
            
            Chromosome chromosome = new Chromosome(Synthesis.synthesizeGeneticCode(Configuration.CHROMOSOME_LENGTH));
            populace.add(chromosome);
        }
        
        return new Population(populace);
    }
    
    /**
     * a genetic code as an array of Alleles is synthesized.
     * refer Allele.java for more information.
     * 
     * @param   length  the required length of the genetic code.
     * @return          the synthesized genetic code.
     */
    public static Allele[] synthesizeGeneticCode(final int length) {
        
        Allele[] geneticCode = new Allele[length];
        
        for(int i = 0; i < length; i++) geneticCode[i] = Synthesis.synthesizeAllele();
        
        return geneticCode;
    }
    
    /**
     * an allele object with a randomly selected boolean gene value is synthesized.
     * 
     * @return  a randomly generated Allele object
     */
    public static Allele synthesizeAllele() {
        return new Allele(LOCAL_RANDOM.nextBoolean());
    }
    
    /**
     * a child population of the same size as the parent is synthesized from the parent population
     * 
     * @param   parent  the parent population object
     * @return          a child population synthesized from the parent population
     */
    public static Population synthesizeChild(Population parent) {
        
        List<Chromosome> populace = new ArrayList<>();
        
        while(populace.size() < Configuration.POPULATION_SIZE)
            if((Configuration.POPULATION_SIZE - populace.size()) == 1) populace.add(Synthesis.mutation(Synthesis.crowdedBinaryTournamentSelection(parent)));
            else for(Chromosome chromosome : Synthesis.crossover(Synthesis.crowdedBinaryTournamentSelection(parent), Synthesis.crowdedBinaryTournamentSelection(parent))) populace.add(Synthesis.mutation(chromosome));
        
        return new Population(populace);
    }
    
    public static Population createCombinedPopulation(final Population parent, final Population child) {
        
        List<Chromosome> populace = parent.getPopulace();
        
        for(Chromosome chromosome : child.getPopulace()) populace.add(chromosome);
        
        return new Population(populace);
    }
    
    private static Chromosome crowdedBinaryTournamentSelection(final Population population) {
        
        Chromosome participant1 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, Configuration.POPULATION_SIZE));
        Chromosome participant2 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, Configuration.POPULATION_SIZE));
        
        if(participant1.getRank() < participant2.getRank()) return participant1;
        else if(participant1.getRank() == participant2.getRank()) {
            if(participant1.getCrowdingDistance() > participant2.getCrowdingDistance()) return participant1;
            else if(participant1.getCrowdingDistance() < participant2.getCrowdingDistance()) return participant2;
            else return Synthesis.LOCAL_RANDOM.nextBoolean() ? participant1 : participant2;
        } else return participant2;
    }
    
    private static Chromosome mutation(final Chromosome chromosome) {
        return ((Synthesis.LOCAL_RANDOM.nextFloat() <= Configuration.MUTATION_PROBABILITY) ? Synthesis.singlePointMutation(chromosome) : chromosome);
    }
    
    private static Chromosome singlePointMutation(final Chromosome chromosome) {
        
        Allele[] geneticCode = new Allele[Configuration.CHROMOSOME_LENGTH];
        
        for(int i = 0; i < Configuration.CHROMOSOME_LENGTH; i++)
            geneticCode[i] = new Allele((Synthesis.LOCAL_RANDOM.nextFloat() <= Configuration.MUTATION_PROBABILITY) ? !chromosome.getGeneticCode()[i].getGene() : chromosome.getGeneticCode()[i].getGene());
        
        return (Synthesis.isGeneticCodeSimilar(geneticCode, chromosome.getGeneticCode()) ? chromosome.getCopy() : new Chromosome(geneticCode));
    }
    
    private static Chromosome[] crossover(final Chromosome chromosome1, final Chromosome chromosome2) {
        
        if(Synthesis.LOCAL_RANDOM.nextFloat() <= Configuration.CROSSOVER_PROBABILITY) return new Chromosome[] { Synthesis.uniformCrossover(chromosome1, chromosome2), Synthesis.uniformCrossover(chromosome1, chromosome2) };
        else return new Chromosome[] { chromosome1.getCopy(), chromosome2.getCopy() };
    }
    
    private static Chromosome uniformCrossover(final Chromosome chromosome1, final Chromosome chromosome2) {
        
        Allele[] geneticCode = new Allele[Configuration.CHROMOSOME_LENGTH];
        
        for(int i = 0; i < Configuration.CHROMOSOME_LENGTH; i++) {
            
            switch(Synthesis.LOCAL_RANDOM.nextInt(2)) {
                
                case 0: geneticCode[i] = new Allele(chromosome1.getGeneticCode()[i].getGene()); break;
                case 1: geneticCode[i] = new Allele(chromosome2.getGeneticCode()[i].getGene()); break;
            }
        }
        
        return new Chromosome(geneticCode);
    }
    
    private static boolean isGeneticCodeSimilar(final Allele[] geneticCode1, final Allele[] geneticCode2) {
        for(int i = 0; i < Configuration.CHROMOSOME_LENGTH; i++) if(geneticCode1[i].getGene() != geneticCode2[i].getGene()) return false; return true;
    }
}
