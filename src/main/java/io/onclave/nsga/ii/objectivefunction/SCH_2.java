/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.objectivefunction;

import io.onclave.nsga.ii.interfaces.IObjectiveFunction;

/**
 * the SCH objective function [f(x) = (x - 2)^2]
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   0.1
 */
public class SCH_2 implements IObjectiveFunction {
    
    private static final String OBJECTIVE_TITLE = "pow(x - 2, 2)";

    @Override
    public String objectiveFunctionTitle() {
        return SCH_2.OBJECTIVE_TITLE;
    }

    @Override
    public double getObjectiveValue(final double fitness) {
        return Math.pow(fitness - 2d, 2);
    }
    
}
