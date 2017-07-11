/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
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
    private static final int GENERATIONS = 100;
    private static final int CHROMOSOME_LENGTH = 30;
    private static final float CROSSOVER_PROBABILITY = 0.7f;
    private static final float MUTATION_PROBABILITY = 0.03f;
    private static List<IObjectiveFunction> objectives = null;

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
}
