/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.datastructure.Population;
import org.jfree.ui.RefineryUtilities;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Reporter {
    
    /**
     * this class is never supposed to be instantiated
     */
    private Reporter() {}
    
    public static void reportInitialParentPopulationGeneration() {
        
        System.out.println("\n\n=============================================================");
        System.out.println("CREATING INITIAL PARENT POPULATION");
        System.out.println("=============================================================\n\n");
    }
    
    public static void reportGeneration(int generation) {
        
        System.out.println("\n\n=============================================================");
        System.out.println("GENERATION : " + generation);
        System.out.println("=============================================================\n\n");
    }
    
    public static void reportAlgorithmStart() {
        
        System.out.println("\n\n=============================================================");
        System.out.println("RUNNING ALGORITHM");
        System.out.println("=============================================================\n\nPlease provide configuration(s) / settings - Press:");
        System.out.println("1 - To use default configuration(s).\n2 - To provide custom configuration(s).");
        System.out.print("\n: ");
    }
    
    public static void reportDefaultConfiguration() {
        
        System.out.println("\n\nUsing default configuration(s) . . .\n");
        Reporter.reportConfiguration();
    }
    
    public static void reportCustomConfigurationStartInput() {
        System.out.println("\n\nPlease provide your required configuration(s) . . .\n");
    }
    
    public static void reportWrongInput() {
        System.out.println("\n\nWrong input provided. Program will now exit!\n\n");
    }
    
    public static void reportCustomConfiguration() {
        
        System.out.println("\nUsing custom configuration(s) . . .\n");
        Reporter.reportConfiguration();
    }
    
    public static void reportConfiguration() {
        
        System.out.println("NUMBER OF GENES TO WORK WITH : " + Configuration.NUMBER_OF_GENES);
        System.out.println("POPULATION SIZE : " + Configuration.POPULATION_SIZE);
        System.out.println("NUMBER OF GENERATIONS : " + Configuration.GENERATIONS);
    }
    
    public static void reportIOException() {
        System.out.println("\n\nInput / Output Exception caught. Program will now exit!\n\n");
    }
    
    public static void reportAlgorithmEnd() {
        
        System.out.println("\n\n=============================================================");
        System.out.println("ALGORITHM ENDED SUCCESSFULLY");
        System.out.println("=============================================================\n\n");
    }
    
    public static void reportGraphPlotAlert() {
        System.out.println("\n\n=============================================================");
        System.out.println("CHECK PARETO FRONT OUTPUT");
        System.out.println("=============================================================\n\n");
    }
    
    public static void render2DGraph(final Population population) {
        
        if(Configuration.objectives.size() > 2) {
            System.out.println("\n\nThis Implementation has more than 2 objectives and cannot be plotted on a 2D graph. Either minimize objectives to 2, or use other plotting implemenataions.\n\n"); return;
        }
        
        GraphPlot graph = new GraphPlot(population);
                
        graph.configurePlotter(Configuration.getXaxisTitle(), Configuration.getYaxisTitle());
        graph.pack();

        RefineryUtilities.centerFrameOnScreen(graph);

        graph.setVisible(true);
    }
}
