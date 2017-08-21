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
 *
 * @author sajib
 */
public class Configuration {
    
    private static final int POPULATION_SIZE = 1000;
    private static final int GENERATIONS = 10;
    private static final int CHROMOSOME_LENGTH = 8;
    private static final float CROSSOVER_PROBABILITY = 0.7f;
    private static final float MUTATION_PROBABILITY = 0.03f;
    private static List<IObjectiveFunction> objectives = null;
    
    public static final int ACTUAL_MIN = 0;
    public static final int ACTUAL_MAX = 255;
    public static final int NORMALIZED_MIN = 0;
    public static final int NORMALIZED_MAX = 2;
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
