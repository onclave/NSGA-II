/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.Interface.IObjectiveFunction;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;
import io.onclave.nsga.ii.datastructure.Population;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is the service class that does most of the under-the-hood work that is abstracted/encapsulated
 * by other classes at the business/controller layer.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   0.1
 */
public class Service {
    
    /**
     * this is an implementation of the fast non-dominated sorting algorithm as defined in the
     * NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part A.
     * 
     * @param   population  the population object that needs to undergo fast non-dominated sorting algorithm
     * @return  a HashMap with an integer key that labels the ranks and a list of chromosomes as values that clot chromosomes of same rank
     */
    public static HashMap<Integer, List<Chromosome>> fastNonDominatedSort(Population population) {
        
        HashMap<Integer, List<Chromosome>> paretoFront = new HashMap<>();
        List<Chromosome> singularFront = new ArrayList<>();
        List<Chromosome> populace = population.getPopulace();
        
        /**
         * iterating over each chromosome of the population
         */
        for(Chromosome chromosome : populace) {
            
            /**
             * an initial domination rank of 0 is set for each chromosome and a blank list is set for the number of
             * chromosomes that the present chromosome dominates.
             */
            chromosome.setDominationRank(0);
            chromosome.setDominatedChromosomes(new ArrayList<>());
            
            /**
             * for each chromosome, the program iterates over all the other remaining chromosomes to find which other
             * chromosomes are dominated by this chromosome and vice versa.
             */
            for (Chromosome competitor : populace) if(!competitor.equals(chromosome)) {
                
                /**
                 * if the present chromosome dominates the competitor, then:
                 *      i:   check if the competitor already exists in the list of dominated chromosomes of the present chromosome.
                 *     ii:   if the competitor does not exist within the list, then add it to the list of dominated chromosomes
                 *           of the present chromosome.
                 * else, if the competitor dominates the present chromosome, then increment the domination rank of the present
                 * chromosome by one.
                 */
                if(dominates(chromosome, competitor)) {
                    if(!chromosome.getDominatedChromosomes().contains(competitor)) chromosome.getDominatedChromosomes().add(competitor);
                } else if(dominates(competitor, chromosome)) chromosome.setDominationRank(chromosome.getDominationRank() + 1);
            }
            
            /**
             * if the domination rank of the present chromosome is 0, it means that this chromosome is a non-dominated chromosome
             * and hence it is added to the clot of chromosomes that are also non-dominated.
             */
            if(chromosome.getDominationRank() == 0) singularFront.add(chromosome);
        }
        
        /**
         * the first clot of non-dominated chromosomes is added to the HashMap with rank label 1.
         */
        paretoFront.put(1, singularFront);
        
        int i = 1;
        List<Chromosome> previousFront = paretoFront.get(i);
        List<Chromosome> nextFront = new ArrayList<>();
        
        /**
         * the current/previous ranked clot of chromosomes with rank i is iterated over to find the next clot of chromosomes
         * with rank (i+1)
         */
        while(previousFront != null && !previousFront.isEmpty()) {
            
            /**
             * iterating over each chromosome from the previous clot of chromosomes ranked i.
             */
            for(Chromosome chromosome : previousFront) {
                
                /**
                 * iterating over each of the dominated chromosomes from the present chromosome of rank i.
                 */
                for(Chromosome recessive : chromosome.getDominatedChromosomes()) {
                    
                    /**
                     * if the domination rank of the current recessive chromosome in consideration is not 0, then
                     * decrement it's rank by 1.
                     * if the domination rank of the current recessive chromosome in consideration is 0, then add
                     * it to the next front [clot of chromosomes that belong to rank (i+1)].
                     */
                    if(recessive.getDominationRank() != 0) recessive.setDominationRank(recessive.getDominationRank() - 1);
                    if(recessive.getDominationRank() == 0) if(!nextFront.contains(recessive)) nextFront.add(recessive);
                }
            }
            
            /**
             * this code snippet ensures "rank jumps" to create all the possible rank lists from the parent
             * population.
             * new ranks are created only when there are recessive chromosomes with domination rank = 1 which are
             * decremented to domination rank 0 and then added to the next front.
             * but, due to the randomness of the algorithm, situation may occur such that even after decrementing all recessive
             * chromosome domination ranks by 1, none have domination rank 0 and hence the next front remains empty.
             * to ensure that all recessive chromosomes are added to some rank list, the program jumps domination ranks
             * of each recessive chromosome by decrementing domination rank by 1 until at least one of them reaches a
             * domination rank count of 0 and then that recessive chromosome is added to the next front.
             * 
             * if the next front is empty and the previous front has at least one dominated chromosome:
             *      i:  find the minimum rank among all the recessive chromosomes available:
             *              1:  iterate over all the chromosomes of the previous front
             *              2:  while the chromosomes have no dominated chromosomes with rank 0:
             *                      a:  iterate over all the recessive chromosomes of the current chromosome
             *                      b:  if the minimum rank is greater than the dominated rank of the present recessive,
             *                          mark this as the minimum rank recorded among all recessive chromosomes available.
             *              3:  end while
             *     ii:  iterate over all the chromosomes of the previous front
             *              1: while the chromosomes have no dominated chromosomes with rank 0:
             *                      a:  iterate over all the dominated chromosomes of the current chromosome
             *                      b:  if the domination rank of the recessive chromosome is not 0, then decrement the
             *                          domination count by value of minimum rank.
             *                      c:  if the domination rank is 0, then add it to the next front.
             *              2:  end while
             */
            if(nextFront.isEmpty() && !isDominatedChromosomesEmpty(previousFront)) {
                
                int minimumRank = -1;
                
                for(Chromosome chromosome : previousFront)
                    while(hasRecessiveRankGreaterThanZero(chromosome))
                        for(Chromosome recessive : chromosome.getDominatedChromosomes())
                            if((minimumRank == -1) || minimumRank > recessive.getDominationRank()) minimumRank = recessive.getDominationRank();
                
                if(minimumRank != -1) for(Chromosome chromosome : previousFront)
                    while(hasRecessiveRankGreaterThanZero(chromosome)) for(Chromosome recessive : chromosome.getDominatedChromosomes()) {
                            if(recessive.getDominationRank() != 0) recessive.setDominationRank(recessive.getDominationRank() - minimumRank);
                            if(recessive.getDominationRank() == 0) if(!nextFront.contains(recessive)) nextFront.add(recessive);
                    }
            }
            
            /**
             * if the next front calculated is not empty, then it is added to the ranked HashMap data-structure
             * with the rank (i+1), else all chromosomes are sorted into some rank or the other and the program
             * breaks out of the loop.
             */
            if(!nextFront.isEmpty()) paretoFront.put(++i, nextFront); else break;
            
            /**
             * the next front (i) calculated is marked as the previous front for the next iteration (i+1) and
             * an empty next front is created.
             */
            previousFront = nextFront;
            nextFront = new ArrayList<>();
        }

        return paretoFront;
    }
    
    /**
     * this is the implementation of the crowding distance assignment algorithm as defined in the
     * NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part B.
     * this ensures diversity preservation.
     * 
     * @param   singularFront   a list of chromosomes whose crowding distances are to be calculated
     * @return                  a list of ParetoObjects with assigned crowding distances. [Refer ParetoObject.java for more information]
     */
    public static List<ParetoObject> crowdingDistanceAssignment(List<Chromosome> singularFront) {
        
        int i = 0;
        int end = singularFront.size() - 1;
        Double maxObjectiveValue;
        Double minObjectiveValue;
        List<IObjectiveFunction> objectives = Configuration.getObjectives();
        List<ParetoObject> singlePareto = new ArrayList<>();
        
        /**
         * for each chromosome in the input list, a new ParetoObject with an initial crowding distance of 0
         * is created and added to the list of ParetoObjects that are to be returned.
         */
        for(Chromosome chromosome : singularFront) singlePareto.add(i++, new ParetoObject(chromosome, 0f));
        
        /**
         * iterating over each of the objective functions set [refer Configuration.java for more information],
         * the ParetoObject list is sorted according to the objective functions and the first and last ParetoObjects
         * are set a crowding distance of infinity.
         */
        for(IObjectiveFunction objective : objectives) {
            
            maxObjectiveValue = null;
            minObjectiveValue = null;
            singlePareto = sort(singlePareto, objective);
            
            singlePareto.get(0).setCrowdingDistance(Double.MAX_VALUE);
            singlePareto.get(end).setCrowdingDistance(Double.MAX_VALUE);
            
            /**
             * the max and min objective values are calculated according to the present objective function
             */
            for(ParetoObject paretoObject : singlePareto) {
                
                if((maxObjectiveValue == null) || (maxObjectiveValue < objective.objectiveFunction(paretoObject))) maxObjectiveValue = objective.objectiveFunction(paretoObject);
                if((minObjectiveValue == null) || (minObjectiveValue > objective.objectiveFunction(paretoObject))) minObjectiveValue = objective.objectiveFunction(paretoObject);
            }
            
            /**
             * the crowding distance of all ParetoObjects are calculated and assigned except the first and last ParetoObjects
             * that have infinite crowding distance
             */
            for(i = 2; i < end; i++) singlePareto.get(i).setCrowdingDistance(calculateCrowdingDistance(singlePareto,
                                                                                                        i,
                                                                                                        objective,
                                                                                                        maxObjectiveValue,
                                                                                                        minObjectiveValue));
        }
        
        return singlePareto; 
   }
    
    /**
     * this method sorts a list of ParetoObjects based on the Crowd-Comparison Operator using the domination
     * rank and crowding distance as discussed in the NSGA-II paper [DOI: 10.1109/4235.996017] Section III Part B.
     * 
     * @param   singleFront     a list of ParetoObjects that are to be sorted according to their crowding distance
     * @return                  a list of sorted ParetoObjects
     */
    public static List<ParetoObject> crowdComparisonSort(List<ParetoObject> singleFront) {
        
        int index = -1;
        List<ParetoObject> sortedFront = new ArrayList<>();
        ParetoObject presentParetoObject;
        ParetoObject competitor;
        
        /**
         * all the ParetoObjects are, at first, marked as false for crowding distance sorted.
         */
        singleFront.stream().forEach((paretoObject) -> { paretoObject.setCrowdingDistanceSorted(false); });
        
        /**
         * iterating over each ParetoObject in the singular front input:
         *  i:  the i-th ParetoObject is marked as presentParetoObject
         * ii:  if the presentParetoObject is not already sorted by crowding distance:
         *          1:  iterate over the rest of the ParetoObjects in the input list as competitors that are
         *              not already sorted using crowding distance
         *          2:  compare the i-th and the j-th chromosome using the crowd comparison operator:
         *                  a: for different ranks, choose the one with the lower (better) rank.
         *                  b: for same rank, choose the one which has lower crowding distance.
         *          3:  if competitor dominates the i-th chromosome, then mark competitor as presentParetoObject
         *          4:  continue until i-th chromosome is compared to all competitors.
         *          5:  mark the presentParetoObject as already sorted by crowding distance
         *          6:  add presentParetoObject into list of sorted front with an incremented index
         */
        for(int i = 0; i < singleFront.size(); i++) {
            
            presentParetoObject = singleFront.get(i);
            
            if(!presentParetoObject.isCrowdingDistanceSorted()) {
                
                for(int j = 0; j < singleFront.size(); j++) {

                    competitor = singleFront.get(j);
                    
                    if(!competitor.isCrowdingDistanceSorted()) {
                        
                        double dominationRank = presentParetoObject.getChromosome().getDominationRank();
                        double competingDominationRank = competitor.getChromosome().getDominationRank();
                        double crowdingDistance = presentParetoObject.getCrowdingDistance();
                        double competingCrowdingDistance = competitor.getCrowdingDistance();

                        if(i != j) if((dominationRank > competingDominationRank) || ((dominationRank == competingDominationRank) && (crowdingDistance < competingCrowdingDistance))) presentParetoObject = competitor;
                    }
                }
                
                presentParetoObject.setCrowdingDistanceSorted(true);
                sortedFront.add(++index, presentParetoObject);
            }
        }
        
        return sortedFront;
    }
    
    /**
     * this method is not implemented, as it is not absolutely necessary for this algorithm to work.
     * is kept if implementation is needed in future.
     * returns the same unsorted parent population as of now.
     * 
     * @param   population  the population that is to be sorted
     * @return              a sorted population
     */
    public static Population nonDominatedPopulationSort(Population population) {
        
        //--TO-DO--
        
        return population;
    }
    
    /**
     * this method checks whether competitor1 dominates competitor2.
     * requires that none of the values of the objective functions using competitor1 is smaller
     * than the values of the objective functions using competitor2.
     * at least one of the values of the objective functions using competitor1 is greater than
     * the corresponding value of the objective functions using competitor2.
     * 
     * @param   competitor1     the chromosome that may dominate
     * @param   competitor2     the chromosome that may be dominated
     * @return                  boolean logic whether competitor1 dominates competitor2.
     */
    public static boolean dominates(final Chromosome competitor1, final Chromosome competitor2) {
        
        /**
         * getting the list of configured objectives from Configuration.java
         */
        List<IObjectiveFunction> objectives = Configuration.getObjectives();
        
        /**
         * checks the negation of the predicate [none of the values of objective functions using competitor1
         * is less than values of objective functions using competitor2] meaning that at least one of the values
         * of the objective functions using competitor1 is less than the values of the objective functions using
         * competitor2, hence returning false as competitor1 does not dominate competitor2
         */
        if (!objectives.stream().noneMatch((objective) -> (objective.objectiveFunction(competitor1) < objective.objectiveFunction(competitor2)))) return false;
        
        /**
         * returns the value of the predicate [at least one of the values of the objective functions using
         * competitor1 is greater than the corresponding value of the objective function using competitor2]
         */
        return objectives.stream().anyMatch((objective) -> (objective.objectiveFunction(competitor1) > objective.objectiveFunction(competitor2)));
    }
    
    /**
     * the list is first converted to an array data-structure and then a randomized quick sort
     * algorithm is followed.
     * the resulting sorted array is again converted to a List data-structure before returning.
     * 
     * @param   singlePareto    the list of ParetoObjects that are to be sorted.
     * @param   objective       the objective function using which the ParetoObjects are sorted.
     * @return                  sorted list of ParetoObjects.
     */
    private static List<ParetoObject> sort(List<ParetoObject> singlePareto, IObjectiveFunction objective) {
        
        ParetoObject[] paretoArray = new ParetoObject[singlePareto.size()];
        singlePareto.toArray(paretoArray);
        
        randomizedQuickSort(paretoArray, 0, paretoArray.length - 1, objective);
        
        return (new ArrayList<>(Arrays.asList(paretoArray)));
    }
    
    /**
     * refer [https://jordanspencerwu.github.io/randomized-quick-sort/] for more details on randomized
     * quick sort algorithm.
     * 
     * @param   paretoArray     the array to be sorted
     * @param   head            the pointer/position of the head element
     * @param   tail            the pointer/position of the tail element
     * @param   objective       the objective function depending on which the sort is to take place
     * @return                  the pivot index.
     */
    private static int partition(ParetoObject[] paretoArray, int head, int tail, IObjectiveFunction objective) {
        
        ParetoObject pivot = paretoArray[tail];
        int i = head - 1;
        
        for(int j = head; j <= (tail - 1); j++) {
            
            if(objective.objectiveFunction(paretoArray[j]) <= objective.objectiveFunction(pivot)) {
                
                i++;
                ParetoObject temporary = paretoArray[i];
                paretoArray[i] = paretoArray[j];
                paretoArray[j] = temporary;
            }
        }
        
        ParetoObject temporary = paretoArray[i + 1];
        paretoArray[i + 1] = paretoArray[tail];
        paretoArray[tail] = temporary;
        
        return (i + 1);
    }
    
    /**
     * refer [https://jordanspencerwu.github.io/randomized-quick-sort/] for more details on randomized
     * quick sort algorithm.
     * 
     * @param   paretoArray     the array to be sorted
     * @param   head            the pointer/position of the head element
     * @param   tail            the pointer/position of the tail element
     * @param   objective       the objective function depending on which the sort is to take place
     * @return                  the random partition position index.
     */
    private static int randomizedPartition(ParetoObject[] paretoArray, int head, int tail, IObjectiveFunction objective) {
        
        int random = ThreadLocalRandom.current().nextInt(head, tail + 1);
        
        ParetoObject temporary = paretoArray[head];
        paretoArray[head] = paretoArray[random];
        paretoArray[random] = temporary;
        
        return partition(paretoArray, head, tail, objective);
    }
    
    /**
     * refer [https://jordanspencerwu.github.io/randomized-quick-sort/] for more details on randomized
     * quick sort algorithm.
     * 
     * @param   paretoArray     the array to be sorted
     * @param   head            the pointer/position of the head element
     * @param   tail            the pointer/position of the tail element
     * @param   objective       the objective function depending on which the sort is to take place
     */
    private static void randomizedQuickSort(ParetoObject[] paretoArray, int head, int tail, IObjectiveFunction objective) {
        
        if(head < tail) {
            
            int pivot = randomizedPartition(paretoArray, head, tail, objective);
            
            randomizedQuickSort(paretoArray, head, pivot - 1, objective);
            randomizedQuickSort(paretoArray, pivot + 1, tail, objective);
        }
    }
    
    /**
     * implementation of crowding distance calculation as defined in NSGA-II paper
     * [DOI: 10.1109/4235.996017] Section III Part B.
     * 
     * I[i]distance = I[i]distance + (I[i+1].m - I[i-1].m)/(f-max - f-min)
     * 
     * I[i]distance = crowding distance of the i-th individual
     * I[i+1].m = m-th objective function value of the (i+1)-th individual
     * I[i-1].m = m-th objective function value of the (i-1)-th individual
     * f-max, f-min = maximum and minimum values of the m-th objective function
     * 
     * @param   singlePareto            the list of ParetoObjects
     * @param   presentIndex            the present index of ParetoObject whose crowding distance is to be calculated
     * @param   objective               the objective function over which the value of i-th individual is to be calculated
     * @param   maxObjectiveValue       the maximum value for this objective function
     * @param   minObjectiveValue       the minimum value for this objective function
     * @return                          the crowding distance
     */
    private static double calculateCrowdingDistance(List<ParetoObject> singlePareto,
                                                    final int presentIndex,
                                                    final IObjectiveFunction objective,
                                                    final double maxObjectiveValue,
                                                    final double minObjectiveValue) {
        
        return (
            singlePareto.get(presentIndex).getCrowdingDistance()
            + ((objective.objectiveFunction(singlePareto.get(presentIndex + 1))
            - objective.objectiveFunction(singlePareto.get(presentIndex - 1))) / (maxObjectiveValue - minObjectiveValue))
        );
    }
    
    /**
     * checks whether any of the dominated chromosome list of the given front is empty,
     * returns true if at least one set of dominated chromosomes is not non-empty.
     * 
     * @param   front   list of chromosomes whose dominated chromosomes are to be checked
     * @return          boolean logic whether the dominated chromosomes are empty
     */
    private static boolean isDominatedChromosomesEmpty(List<Chromosome> front) {
        return front.stream().anyMatch((chromosome) -> (!chromosome.getDominatedChromosomes().isEmpty()));
    }
    
    /**
     * checks if any of the dominated chromosomes of the input chromosome has a domination rank of 0,
     * returns true if at least one dominated chromosome contains domination rank 0.
     * 
     * @param   chromosome  chromosome to check whether it contains any dominated chromosome with rank 0
     * @return  boolean logic whether dominated chromosomes contain rank 0.
     */
    private static boolean hasRecessiveRankGreaterThanZero(Chromosome chromosome) {
        
        if(chromosome.getDominatedChromosomes().isEmpty()) return false;
        
        return chromosome.getDominatedChromosomes().stream().noneMatch((recessive) -> (recessive.getDominationRank() == 0));
    }
    
    /**
     * the child and parent population is combined to create a larger population pool
     * 
     * @param   parent  parent population
     * @param   child   child population
     * @return          combined parent + child population
     */
    public static Population createCombinedPopulation(Population parent, Population child) {
        
        List<Chromosome> combinedPopulace = new ArrayList<>();
        Population combinedPopulation = new Population();

        combinedPopulace.addAll(parent.getPopulace());
        combinedPopulace.addAll(child.getPopulace());
        combinedPopulation.setPopulace(combinedPopulace);
        
        return combinedPopulation;
    }
    
    /**
     * this method decodes the genetic code that is represented as a string of binary values, converted into
     * decimal value.
     * 
     * @param   geneticCode     the genetic code as an array of Allele. Refer Allele.java for more information
     * @return                  the decimal value of the corresponding binary string.
     */
    public static double decodeGeneticCode(final Allele[] geneticCode) {

        double value = 0;
        String binaryString = "";
        
        for(Allele bit : geneticCode) binaryString += bit.getGene() ? "1" : "0";
        for(int i = 0; i < binaryString.length(); i++) if(binaryString.charAt(i) == '1') value += Math.pow(2, binaryString.length() - 1 - i);
        
        return value;
    }
    
    /**
     * fitness is calculated using min-max normalization
     * 
     * @param   geneticCode     the genetic code whose fitness is to be calculated
     * @return                  the corresponding calculated fitness
     */
    public static double calculateFitness(Allele[] geneticCode) {
        return minMaxNormalization(decodeGeneticCode(geneticCode));
    }
    
    /**
     * an implementation of min-max normalization
     * 
     * @param   value   the value that is to be normalized
     * @return          the normalized value
     */
    private static double minMaxNormalization(final double value) {
        return (((value - Configuration.ACTUAL_MIN) / (Configuration.ACTUAL_MAX - Configuration.ACTUAL_MIN)) * (Configuration.NORMALIZED_MAX - Configuration.NORMALIZED_MIN)) + Configuration.NORMALIZED_MIN;
    }
    
    /**
     * used to generate a random integer value
     * 
     * @return a random integer value
     */
    public static int generateRandomInt() {
        return ThreadLocalRandom.current().nextInt();
    }
    
    /**
     * a short hand for System.out.println().
     * 
     * @param string    the string to print to console.
     */
    public static void p(String string) {
        System.out.println(string);
    }
}
