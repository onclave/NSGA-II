/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.configuration;

import io.onclave.nsga.ii.Interface.IObjectiveFunction;
import io.onclave.nsga.ii.objectivefunction.SCH_1;
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

    public static int getPOPULATION_SIZE() {
        return POPULATION_SIZE;
    }

    public static int getGENERATIONS() {
        return GENERATIONS;
    }

    public static int getCHROMOSOME_LENGTH() {
        return CHROMOSOME_LENGTH;
    }
    
    public static List<IObjectiveFunction> buildObjectives() {
        
        List<IObjectiveFunction> objectives = new ArrayList<>();
        
        return objectives;
    }
}
