/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.interfaces.IObjectiveFunction;
import io.onclave.nsga.ii.objectivefunction.SCH_1;
import io.onclave.nsga.ii.objectivefunction.SCH_2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Configuration {
    
    /**
     * this class is never supposed to be instantiated
     */
    private Configuration() {}
    
    public static int NUMBER_OF_GENES = 100;
    public static int POPULATION_SIZE = 30;
    public static int GENERATIONS = 10;
    public static String DEFAULT_X_AXIS_TITLE = "X-AXIS";
    public static String DEFAULT_Y_AXIS_TITLE = "Y-AXIS";
    
    public static final float CROSSOVER_PROBABILITY = 0.7f;
    public static final float MUTATION_PROBABILITY = 0.03f;
    
    public static String SELECTED_DATASET;
    public static int CHROMOSOME_LENGTH;
    public static int DATA_SAMPLE_COUNT;
    public static int UNPROCESSED_GENE_COUNT;
    
    public static List<IObjectiveFunction> objectives = null;
    
    public static void configure() {
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        
        Reporter.reportAlgorithmStart();
        
        try {
            
            switch(bufferedReader.readLine()) {
                
                case "1":
                    
                    Reporter.reportDefaultConfiguration();
                    break;
                case "2":
                    
                    Reporter.reportCustomConfigurationStartInput();
                    
                    Configuration.setCustomConfiguration(bufferedReader);
                    Configuration.setCustomAxisTitles(bufferedReader);
                    
                    Reporter.reportCustomConfiguration();
                    
                    break;
                default: Reporter.reportWrongInput(); break;
            }
            
            Configuration.CHROMOSOME_LENGTH = Configuration.NUMBER_OF_GENES;
            
            Configuration.buildObjectives();
        } catch(IOException e) {
            Reporter.reportIOException();
        }
    }
    
    public static void setObjectives(List<IObjectiveFunction> objectives) {
        Configuration.objectives = objectives;
    }
    
    public static String getXaxisTitle() {
        return Configuration.DEFAULT_X_AXIS_TITLE;
    }
    
    public static String getYaxisTitle() {
        return Configuration.DEFAULT_Y_AXIS_TITLE;
    }
    
    private static void setCustomAxisTitles(final BufferedReader bufferedReader) throws IOException {
        
        System.out.print("\nDo you want to provide axis titles? (y/n): ");
        
        switch(bufferedReader.readLine()) {
            
            case "y":
                
                System.out.print("\nEnter X-Axis Title: ");
                Configuration.DEFAULT_X_AXIS_TITLE = bufferedReader.readLine();
                
                System.out.print("Enter Y-Axis Title: ");
                Configuration.DEFAULT_Y_AXIS_TITLE = bufferedReader.readLine();
                
                break;
            case "n": break;
            default: Reporter.reportWrongInput(); break;
        }
    }
    
    private static void setCustomConfiguration(final BufferedReader bufferedReader) throws IOException {
        
        System.out.print("Enter the number genes to work with: ");
        Configuration.NUMBER_OF_GENES = Integer.parseInt(bufferedReader.readLine());

        System.out.print("Enter population size: ");
        Configuration.POPULATION_SIZE = Integer.parseInt(bufferedReader.readLine());

        System.out.print("Enter number of generations: ");
        Configuration.GENERATIONS = Integer.parseInt(bufferedReader.readLine());
    }
    
    /**
     * this method sets the objective functions over which the algorithm is to operate.
     * it is a list of IObjectionFunction objects.
     */
    private static void buildObjectives() {
        
        List<IObjectiveFunction> newObjectives = new ArrayList<>();
        
        newObjectives.add(0, new SCH_1());
        newObjectives.add(1, new SCH_2());
        
        Configuration.setObjectives(newObjectives);
    }
}
