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
    
    public static int POPULATION_SIZE = 100;
    public static int GENERATIONS = 25;
    public static int CHROMOSOME_LENGTH = 20;
    
    public static String X_AXIS_TITLE = "X-AXIS";
    public static String Y_AXIS_TITLE = "Y-AXIS";
    
    public static final double ACTUAL_MIN = 0;
    public static double ACTUAL_MAX = 0;
    public static final double NORMALIZED_MIN = 0;
    public static final double NORMALIZED_MAX = 2;
    public static final float CROSSOVER_PROBABILITY = 0.7f;
    public static final float MUTATION_PROBABILITY = 0.03f;
    
    public static List<IObjectiveFunction> objectives = null;
    
    public static void configure() {
        
        Configuration.buildObjectives();
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        
        Reporter.reportAlgorithmStart();
        
        try {
            
            switch(bufferedReader.readLine()) {
                
                case "1":
                    
                    Configuration.setDefaultAxisTitles();
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
            
            Configuration.ACTUAL_MAX = Math.pow(2, CHROMOSOME_LENGTH) - 1;
        } catch(IOException e) {
            Reporter.reportIOException();
        }
    }
    
    public static void setObjectives(List<IObjectiveFunction> objectives) {
        Configuration.objectives = objectives;
    }
    
    public static String getXaxisTitle() {
        return Configuration.X_AXIS_TITLE;
    }
    
    public static String getYaxisTitle() {
        return Configuration.Y_AXIS_TITLE;
    }
    
    private static void setDefaultAxisTitles() {
        
        Configuration.X_AXIS_TITLE = Configuration.objectives.get(0).objectiveFunctionTitle();
        Configuration.Y_AXIS_TITLE = Configuration.objectives.get(1).objectiveFunctionTitle();
    }
    
    private static void setCustomAxisTitles(final BufferedReader bufferedReader) throws IOException {
        
        System.out.print("\nDo you want to provide axis titles? (y/n): ");
        
        switch(bufferedReader.readLine()) {
            
            case "y":
                
                System.out.print("\nEnter X-Axis Title: ");
                Configuration.X_AXIS_TITLE = bufferedReader.readLine();
                
                System.out.print("Enter Y-Axis Title: ");
                Configuration.Y_AXIS_TITLE = bufferedReader.readLine();
                
                break;
            case "n": break;
            default: Reporter.reportWrongInput(); break;
        }
    }
    
    private static void setCustomConfiguration(final BufferedReader bufferedReader) throws IOException {
        
        System.out.print("Enter the chromosome length to work with: ");
        Configuration.CHROMOSOME_LENGTH = Integer.parseInt(bufferedReader.readLine());

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
