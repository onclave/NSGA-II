/*
 * This repository / codebase is Open Source and free for use and rewrite.
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
 * This is the synthesis class that does many of the under-the-hood work (biological simulation) that is abstracted/encapsulated
 * by other classes at the business/controller layer.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   0.2
 */
public class Synthesis {
    
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
        for(int i = 0; i < Configuration.getPOPULATION_SIZE(); i++) {
            
            Chromosome chromosome = new Chromosome();
            chromosome.setGeneticCode(synthesizeGeneticCode(Configuration.getCHROMOSOME_LENGTH()));
            populace.add(chromosome);
        }
        
        return new Population(populace);
    }
    
    /**
     * a child population of the same size as the parent is synthesized from the parent population
     * 
     * @param   parent  the parent population object
     * @return          a child population synthesized from the parent population
     */
    public static Population synthesizeChild(Population parent) {
        
        Population child = new Population();
        List<Chromosome> populace = new ArrayList<>();
        
        /**
         * child chromosomes undergo crossover and mutation.
         * the child chromosomes are selected using binary tournament selection.
         * crossover returns an array of exactly two child chromosomes synthesized from two parent
         * chromosomes.
         */
        while(populace.size() < Configuration.getPOPULATION_SIZE())
            for(Chromosome chromosome : crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent)))
                populace.add(mutation(chromosome));
        
        child.setPopulace(populace);
        
        return child;
    }
    
    /**
     * this is an implementation of basic binary tournament selection.
     * for a tournament of size t, select t individuals (randomly) from population and determine winner of
     * tournament with the highest fitness value.
     * in case of binary tournament selection, t = 2.
     * 
     * refer [https://stackoverflow.com/questions/36989783/binary-tournament-selection] for more information.
     * 
     * @param   population  the population from which a child chromosome is to be selected
     * @return              the selected child chromosome
     */
    private static Chromosome binaryTournamentSelection(Population population) {
        
        Chromosome individual1 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));
        Chromosome individual2 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));
        
        if(individual1.getFitness() > individual2.getFitness()) return individual1; else return individual2;
    }
    
    /**
     * this is a basic implementation of uniform crossover where the crossover/break point is the middle
     * of the chromosomes. The genetic code of both the parent chromosomes are broken from the middle
     * and crossover is done to create two child chromosomes.
     * crossover probability is considered.
     * 
     * @param   chromosome1     the first parent chromosome taking part in crossover
     * @param   chromosome2     the second parent chromosome taking part in crossover
     * @return                  an array of exactly two child chromosomes synthesized from two parent chromosomes.
     */
    public static Chromosome[] crossover(Chromosome chromosome1, Chromosome chromosome2) {
        
        Allele[] geneticCode1 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
        Allele[] geneticCode2 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
        Allele[] chromosome1geneCode = chromosome1.getGeneticCode();
        Allele[] chromosome2geneCode = chromosome2.getGeneticCode();
        Chromosome[] childChromosomes = {new Chromosome(), new Chromosome()};
        int breakPoint = Configuration.getCHROMOSOME_LENGTH() / 2;
        
        /**
         * generating a new random float value and if this value is less than equal to the
         * crossover probability mentioned in the Configuration file, then crossover occurs,
         * otherwise the parents themselves are copied as child chromosomes.
         */
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
    
    /**
     * in this mutation operation implementation, a random bit-flip takes place.
     * a random float value is generated and if this value is less than equal to the mutation
     * probability defined in Configuration, then mutation takes place, otherwise the original
     * chromosome is returned.
     * 
     * @param   chromosome  the chromosome over which the mutation takes place
     * @return              the mutated chromosome
     */
    private static Chromosome mutation(Chromosome chromosome) {
        
        if(LOCAL_RANDOM.nextFloat() <= Configuration.getMUTATION_PROBABILITY()) {
            
            Allele[] geneticCode = chromosome.getGeneticCode();
            geneticCode[LOCAL_RANDOM.nextInt(geneticCode.length)].bitFlip();
            chromosome.setGeneticCode(geneticCode);
        }
        
        return chromosome;
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
        
        for(int i = 0; i < length; i++) geneticCode[i] = synthesizeAllele();
        
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
}
