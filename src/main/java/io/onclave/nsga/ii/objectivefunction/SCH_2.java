/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.objectivefunction;

import io.onclave.nsga.ii.Interface.IObjectiveFunction;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;

/**
 * the SCH objective function [f(x) = (x - 2)^2]
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.0
 * @since   0.1
 */
public class SCH_2 implements IObjectiveFunction {
    
    private static final String AXIS_TITLE = "pow(x - 2, 2)";

    @Override
    public double objectiveFunction(Chromosome chromosome) {
        return objectiveFunction(chromosome.getFitness());
    }

    @Override
    public double objectiveFunction(ParetoObject paretoObject) {
        return objectiveFunction(paretoObject.getChromosome());
    }

    @Override
    public double objectiveFunction(double geneVaue) {
        return Math.pow(geneVaue - 2, 2);
    }

    @Override
    public String getAxisTitle() {
        return AXIS_TITLE;
    }
    
}
