/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.algorithm;

import io.onclave.nsga.ii.api.GraphPlot;
import io.onclave.nsga.ii.api.Reporter;
import io.onclave.nsga.ii.api.Service;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;
import io.onclave.nsga.ii.datastructure.Population;
import org.jfree.ui.RefineryUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the starting point of the main NSGA-II algorithm.
 * Run this class to get the desired output.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   0.1
 */
public class Algorithm {
    
    /**
     * This method first prepares the multi-objectives that it is going to work with. In this case,
     * it works with 2 objectives. At generation 0, a random initial population is generated and then
     * sorted using non-dominated population sorting. Using this initial parent population, a child
     * population is generated. As the initial parent and child population are created, new generations
     * are simulated and at each generation, the following actions are carried out:
     *      1:  the present parent and child are combined to create a new population containing all
     *          chromosomes from both parent and child. This ensures elitism.
     *      2:  this new combined population is then sorted using the fast non-dominated sorting algorithm
     *          to get a list of chromosomes that are grouped according to their rank. The higher the rank,
     *          more desirable they are to be carried forward into the next generation.
     *      3:  an iteration is carried over all the ranks as follows:
     *              i:  the list of chromosomes from the current iterated rank is taken into account.
     *             ii:  the amount of free remaining space in the new child population is calculated.
     *            iii:  a crowd comparison sort is done after assigning crowding distance to the chromosomes.
     *             iv:  if the number of chromosomes in this rank is less than or equal to the amount of free
     *                  space available in the new child population, then the whole chromosome cluster is added
     *                  to the new population, else only the available number of chromosomes is added to the
     *                  child population according to their crowding distance. This is done for diversity
     *                  preservation.
     *              v:  end.
     *      4:  the new synthesized populace is added to the new child population.
     *      5:  if this is the last generation, then the present child is shown as the Pareto Front, otherwise,
     *          the present child is labeled as the new parent for the next generation, and a new child
     *          population is generated from this newly labeled parent population. This combination now becomes
     *          the new parent/child for the next generation.
     *      6:  all the child from all the generations are added to the Graph Rendering engine to show all the
     *          child data as fronts for that generation.
     *      7:  end.
     * the plotted graphs are viewed.
     * 
     * @param   args    pass command line arguments. Not required to run this code.
     * @see             Plotted graphs of all fronts as well as the Pareto Front as output. 
     */
    
    public static void main(String[] args) {
        
        /* prepares the objectives [See Configuration.java file for more information.] */
        Configuration.buildObjectives();
        GraphPlot multiPlotGraph = new GraphPlot();
        
        /**
         * a new random population is synthesized and sorted using non-dominated population sort to get
         * a sorted list of parent chromosomes at generation 0.
         * child population generated from parent population.
         */
        Population parent = Service.nonDominatedPopulationSort(Synthesis.syntesizePopulation());
        Population child = Synthesis.synthesizeChild(parent);
        
        /**
         * a loop is run that iterates as new generations are created (new child population is created from previous parent
         * population.
         * the number of generations to be simulated are defined in the Configuration.java file.
         */
        for(int i = 2; i <= Configuration.getGENERATIONS(); i++) {
            
            System.out.println("GENERATION : " + i);
            
            /**
             * a combined population of both latest parent and child is created to ensure elitism.
             * the combined population created is then sorted using fast non-dominated sorting algorithm,
             * to create rank wise divisions [chromosomes with rank 1 (non-dominated),
             * chromosomes with rank 2 (dominated by 1 chromosome), etc.]
             * this information is stored in a HashMap data-structure that maps one integer value
             * to one list of chromosomes. The integer refers to the rank number while the list refers
             * to the chromosomes that belong to that rank.
             */
            HashMap<Integer, List<Chromosome>> rankedFronts = Service.fastNonDominatedSort(Service.createCombinedPopulation(parent, child));
            
            Population nextChildPopulation = new Population();
            List<Chromosome> childPopulace = new ArrayList<>();
            
            /**
             * an iteration is carried over the HashMap to go through each rank of chromosomes, and the
             * most desired chromosomes (higher ranks) are included into the child population of the
             * next generation.
             */
            for(int j = 1; j <= rankedFronts.size(); j++) {
                
                /**
                 * during iteration, the current ranked list of chromosomes is chosen and the amount of
                 * free space (to accommodate chromosomes) of the current child population is calculated
                 * to check whether chromosomes from this rank can be fit into the new child population.
                 */
                List<Chromosome> singularFront = rankedFronts.get(j);
                int usableSpace = Configuration.getPOPULATION_SIZE() - childPopulace.size();
                
                /**
                 * if the new list of chromosomes is not null and if the child population has free usable space,
                 * then an attempt to include some or all of the chromosomes is made otherwise, there is no more
                 * space in the child population and hence no more rank/chromosome checks are done and the program
                 * breaks out from the inner for-loop.
                 */
                if(singularFront != null && !singularFront.isEmpty() && usableSpace > 0) {
                
                    /**
                     * if the amount of usable space is more than or equal to the number of chromosomes in the clot,
                     * the whole clot of chromosomes is added to the child population/populace, otherwise, only the
                     * number of chromosomes that can be fit within the usable space is chosen according to the
                     * crowding distance of the chromosomes.
                     */
                    if(usableSpace >= singularFront.size()) childPopulace.addAll(singularFront);
                    else {
                        
                        /**
                         * a crowd comparison sort is carried over the present clot of chromosomes after assigning them a
                         * crowding distance (to preserve diversity) and hence a list of ParetoObjects are prepared.
                         * [refer ParetoObject.java for more information]
                         */
                        List<ParetoObject> latestFront = Service.crowdComparisonSort(Service.crowdingDistanceAssignment(singularFront));
                        
                        for(int k = 0; k < usableSpace; k++) childPopulace.add(latestFront.get(k).getChromosome());
                    }
                } else break;
            }
            
            /**
             * the new populace is added to the new child population
             */
            nextChildPopulation.setPopulace(childPopulace);
            
            /**
             * if this iteration is not the last generation, the new child created is made the parent for the next
             * generation, and a new child is synthesized from this new parent for the next generation.
             * this is the new parent and child for the next generation.
             * if this is the last generation, no new parent/child combination is created, instead the Pareto Front
             * is plotted and rendered as the latest created child is the actual Pareto Front.
             */
            if(i < Configuration.getGENERATIONS()) {
                parent = child;
                child = Synthesis.synthesizeChild(nextChildPopulation);
            } else Reporter.render2DGraph(child);
            
            /**
             * this adds the child of each generation to the plotting to render the front of all the generations.
             */
            multiPlotGraph.prepareMultipleDataset(child, i, "generation " + i);
        }
        
        System.out.println("\n\n----CHECK PARETO FRONT OUTPUT----\n\n");
        
        /**
         * the plotted and rendered chart/graph is viewed to the user.
         */
        multiPlotGraph.configureMultiplePlotter(Configuration.getXaxisTitle(), Configuration.getYaxisTitle(), "All Pareto");
        multiPlotGraph.pack();
        RefineryUtilities.centerFrameOnScreen(multiPlotGraph);
        multiPlotGraph.setVisible(true);
    }
}
