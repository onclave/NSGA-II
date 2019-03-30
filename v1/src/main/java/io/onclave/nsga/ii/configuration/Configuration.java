/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.configuration;

import io.onclave.nsga.ii.Interface.IObjectiveFunction;
import io.onclave.nsga.ii.objectivefunction.SCH_1;
import io.onclave.nsga.ii.objectivefunction.SCH_2;
import java.util.ArrayList;
import java.util.List;

/**
 * this is the Configuration file for the algorithm, where all the values are set and the initial
 * configurations are set and run.
 * to change any aspect of the algorithm, this file may be tweaked.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.0
 * @since   0.1
 */
public class Configuration {
    
    private static final int POPULATION_SIZE = 800;
    private static final int GENERATIONS = 50;
    private static final int CHROMOSOME_LENGTH = 20;
    private static final float CROSSOVER_PROBABILITY = 0.7f;
    private static final float MUTATION_PROBABILITY = 0.03f;
    private static List<IObjectiveFunction> objectives = null;
    
    public static final double ACTUAL_MIN = 0;
    public static final double ACTUAL_MAX = Math.pow(2, CHROMOSOME_LENGTH) - 1;
    public static final double NORMALIZED_MIN = 0;
    public static final double NORMALIZED_MAX = 2;
    public static final String DEFAULT_X_AXIS_TITLE = "x-axis";
    public static final String DEFAULT_Y_AXIS_TITLE = "y-axis";

    public static int getPOPULATION_SIZE() {
        return POPULATION_SIZE;
    }

    public static int getGENERATIONS() {
        return GENERATIONS;
    }

    public static int getCHROMOSOME_LENGTH() {
        return CHROMOSOME_LENGTH;
    }
    
    /**
     * this method sets the objective functions over which the algorithm is to operate.
     * it is a list of IObjectionFunction objects.
     */
    public static void buildObjectives() {
        
        List<IObjectiveFunction> newObjectives = new ArrayList<>();
        
        newObjectives.add(new SCH_1());
        newObjectives.add(new SCH_2());
        
        setObjectives(newObjectives);
    }

    public static List<IObjectiveFunction> getObjectives() {
        return objectives;
    }

    public static void setObjectives(List<IObjectiveFunction> objectives) {
        Configuration.objectives = objectives;
    }

    public static float getMUTATION_PROBABILITY() {
        return MUTATION_PROBABILITY;
    }

    public static float getCROSSOVER_PROBABILITY() {
        return CROSSOVER_PROBABILITY;
    }
    
    public static String getXaxisTitle() {
        return getObjectives().size() > 2 ? DEFAULT_X_AXIS_TITLE : getObjectives().get(0).getAxisTitle();
    }
    
    public static String getYaxisTitle() {
        return getObjectives().size() > 2 ? DEFAULT_Y_AXIS_TITLE : getObjectives().get(1).getAxisTitle();
    }
}
