/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.Interface;

import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;

/**
 * this is an interface that each objective function object that is created must implement.
 * all the methods must be overridden.
 * without implementing this interface, no objective function object can be plugged into the
 * algorithm as the algorithm will not understand the objective function from a generic level.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.0
 * @since   0.1
 */
public interface IObjectiveFunction {
    public double objectiveFunction(double geneVaue);
    public double objectiveFunction(Chromosome chromosome);
    public double objectiveFunction(ParetoObject paretoObject);
    public String getAxisTitle();
}
