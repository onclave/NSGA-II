/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.Interface;

import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;

/**
 *
 * @author sajib
 */
public interface IObjectiveFunction {
    public double objectiveFunction(Chromosome chromosome);
    public double objectiveFunction(ParetoObject paretoObject);
}
