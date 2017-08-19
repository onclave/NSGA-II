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
 *
 * @author sajib
 */
public class Service {
    
    public static HashMap<Integer, List<Chromosome>> fastNonDominatedSort(Population population) {
        
        HashMap<Integer, List<Chromosome>> paretoFront = new HashMap<>();
        List<Chromosome> singularFront = new ArrayList<>();
        List<Chromosome> populace = population.getPopulace();
        
        for(Chromosome chromosome : populace) {
            
            chromosome.setDominationRank(0);
            chromosome.setDominatedChromosomes(new ArrayList<>());
            
            for (Chromosome competitor : populace) if(!competitor.equals(chromosome)) {
                if(dominates(chromosome, competitor)) { if(!chromosome.getDominatedChromosomes().contains(competitor)) chromosome.getDominatedChromosomes().add(competitor); }
                else if(dominates(competitor, chromosome)) chromosome.setDominationRank(chromosome.getDominationRank() + 1);
            }
            
            if(chromosome.getDominationRank() == 0) singularFront.add(chromosome);
        }
        
        paretoFront.put(1, singularFront);
        
        int i = 1;
        List<Chromosome> previousFront = paretoFront.get(i);
        List<Chromosome> nextFront = new ArrayList<>();
        
        while(previousFront != null && !previousFront.isEmpty()) {
            
            Reporter.reportSingularFront(previousFront, i);
            
            for(Chromosome chromosome : previousFront) {
                
                for(Chromosome recessive : chromosome.getDominatedChromosomes()) {
                    
                    if(recessive.getDominationRank() != 0) recessive.setDominationRank(recessive.getDominationRank() - 1);
                    if(recessive.getDominationRank() == 0) if(!nextFront.contains(recessive)) nextFront.add(recessive);
                }
            }
            
            if(nextFront.isEmpty() && !isDominatedChromosomesEmpty(previousFront)) {
                
                Chromosome chromosome = previousFront.get(0);
                
                while(hasRecessiveRankGreaterThanZero(chromosome)) {
                    
                    for(Chromosome recessive : chromosome.getDominatedChromosomes()) {
                        if(recessive.getDominationRank() != 0) recessive.setDominationRank(recessive.getDominationRank() - 1);
                        if(recessive.getDominationRank() == 0) if(!nextFront.contains(recessive)) nextFront.add(recessive);
                    }
                }
            }
            
            if(!nextFront.isEmpty()) paretoFront.put(++i, nextFront); else break;
            
            previousFront = nextFront;
            nextFront = new ArrayList<>();
        }

        return paretoFront;
    }
    
    public static List<ParetoObject> crowdingDistanceAssignment(List<Chromosome> singularFront) {
        
        int i = 0;
        int end = singularFront.size() - 1;
        List<IObjectiveFunction> objectives = Configuration.getObjectives();
        final float INFINITE_CROWDING_DISTANCE = 9999f;
        List<ParetoObject> singlePareto = new ArrayList<>();
        
        for(Chromosome chromosome : singularFront) singlePareto.add(i++, new ParetoObject(chromosome, 0f));
        
        for(IObjectiveFunction objective : objectives) {
            
            singlePareto = sort(singlePareto, objective);
            
            singlePareto.get(0).setCrowdingDistance(INFINITE_CROWDING_DISTANCE);
            singlePareto.get(end).setCrowdingDistance(INFINITE_CROWDING_DISTANCE);
            
            double maxObjectiveValue = objective.objectiveFunction(singlePareto.get(0));
            double minObjectiveValue = objective.objectiveFunction(singlePareto.get(end));
            
            for(i = 2; i < end; i++) singlePareto.get(i).setCrowdingDistance(calculateCrowdingDistance(singlePareto,
                                                                                                        i,
                                                                                                        objective,
                                                                                                        maxObjectiveValue,
                                                                                                        minObjectiveValue));
        }
        
        return singlePareto; 
   }
    
    public static List<ParetoObject> crowdComparisonSort(List<ParetoObject> singleFront) {
        
        int i = 0;
        List<ParetoObject> sortedFront = new ArrayList<>();
        
        for(ParetoObject paretoObject : singleFront) {
            
            ParetoObject presentParetoObject = paretoObject;
            int index = singleFront.indexOf(paretoObject);
            
            for(ParetoObject competitor : singleFront) {
                
                if(!((presentParetoObject.getChromosome().getDominationRank() < competitor.getChromosome().getDominationRank())
                        || ((presentParetoObject.getChromosome().getDominationRank() == competitor.getChromosome().getDominationRank())
                            && (presentParetoObject.getCrowdingDistance() > competitor.getCrowdingDistance())))) {
                    
                    presentParetoObject = competitor;
                    index = singleFront.indexOf(competitor);
                }
            }
            
            sortedFront.add(i++, singleFront.get(index));
        }
        
        return sortedFront;
    }
    
    public static Population nonDominatedPopulationSort(Population population) {
        
        Population newPopulation = new Population();
        
        
        //--TO-DO--
        
        
        
        
        
        return population;
    }
    
    public static boolean dominates(final Chromosome competitor1, final Chromosome competitor2) {
        
        List<IObjectiveFunction> objectives = Configuration.getObjectives();
        
        if (!objectives.stream().noneMatch((objective) -> (objective.objectiveFunction(competitor1) < objective.objectiveFunction(competitor2)))) return false;
        
        return objectives.stream().anyMatch((objective) -> (objective.objectiveFunction(competitor1) > objective.objectiveFunction(competitor2)));
    }
    
    private static List<ParetoObject> sort(List<ParetoObject> singlePareto, IObjectiveFunction objective) {
        
        ParetoObject[] paretoArray = new ParetoObject[singlePareto.size()];
        singlePareto.toArray(paretoArray);
        
        randomizedQuickSort(paretoArray, 0, paretoArray.length - 1, objective);
        
        return (new ArrayList<>(Arrays.asList(paretoArray)));
    }
    
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
    
    private static int randomizedPartition(ParetoObject[] paretoArray, int head, int tail, IObjectiveFunction objective) {
        
        int random = ThreadLocalRandom.current().nextInt(head, tail + 1);
        
        ParetoObject temporary = paretoArray[head];
        paretoArray[head] = paretoArray[random];
        paretoArray[random] = temporary;
        
        return partition(paretoArray, head, tail, objective);
    }
    
    private static void randomizedQuickSort(ParetoObject[] paretoArray, int head, int tail, IObjectiveFunction objective) {
        
        if(tail < head) {
            
            int pivot = randomizedPartition(paretoArray, head, tail, objective);
            
            randomizedQuickSort(paretoArray, head, pivot - 1, objective);
            randomizedQuickSort(paretoArray, pivot + 1, tail, objective);
        }
    }
    
    private static double calculateCrowdingDistance(List<ParetoObject> singlePareto,
                                                    final int presentIndex,
                                                    final IObjectiveFunction objective,
                                                    final double maxObjectiveValue,
                                                    final double minObjectiveValue) {
        
        return (
            singlePareto.get(presentIndex).getCrowdingDistance()
            + ((objective.objectiveFunction(singlePareto.get(presentIndex + 1))
            + objective.objectiveFunction(singlePareto.get(presentIndex - 1))) / (maxObjectiveValue - minObjectiveValue))
        );
    }
    
    private static boolean isDominatedChromosomesEmpty(List<Chromosome> front) {
        
        if(front.isEmpty()) return true;
        else if (!front.stream().noneMatch((chromosome) -> (!chromosome.getDominatedChromosomes().isEmpty()))) return false;
        
        return true;
    }
    
    private static boolean hasRecessiveRankGreaterThanZero(Chromosome chromosome) {
        
        List<Chromosome> recessives = chromosome.getDominatedChromosomes();
        
        if(recessives.isEmpty()) return false;
        else if (!recessives.stream().noneMatch((recessive) -> (recessive.getDominationRank() == 0))) return false;
        
        return true;
    }
    
    public static Population createCombinedPopulation(Population parent, Population child) {
        
        List<Chromosome> combinedPopulace = new ArrayList<>();
        Population combinedPopulation = new Population();

        combinedPopulace.addAll(parent.getPopulace());
        combinedPopulace.addAll(child.getPopulace());
        combinedPopulation.setPopulace(combinedPopulace);
        
        return combinedPopulation;
    }
    
    public static double decodeGeneticCode(final Allele[] geneticCode) {

        double value = 0;
        String binaryString = "";
        
        for(Allele bit : geneticCode) binaryString += bit.getGene() ? "1" : "0";
        for(int i = 0; i < binaryString.length(); i++) if(binaryString.charAt(i) == '1') value += Math.pow(2, binaryString.length() - 1 - i);
        
        return value;
    }
    
    public static double calculateFitness(Allele[] geneticCode) {
        return minMaxNormalization(decodeGeneticCode(geneticCode));
    }
    
    private static double minMaxNormalization(final double value) {
        return (((value - Configuration.ACTUAL_MIN) / (Configuration.ACTUAL_MAX - Configuration.ACTUAL_MIN)) * (Configuration.NORMALIZED_MAX - Configuration.NORMALIZED_MIN)) + Configuration.NORMALIZED_MIN;
    }
    
    public static int generateRandomInt() {
        return ThreadLocalRandom.current().nextInt();
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
