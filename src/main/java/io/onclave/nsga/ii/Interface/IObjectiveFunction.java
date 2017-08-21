/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.Interface;

import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;

/**
 *
 * @author sajib
 */
public interface IObjectiveFunction {
    public double objectiveFunction(double geneVaue);
    public double objectiveFunction(Chromosome chromosome);
    public double objectiveFunction(ParetoObject paretoObject);
    public String getAxisTitle();
}
