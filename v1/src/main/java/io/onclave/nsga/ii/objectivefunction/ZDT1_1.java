/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */
package io.onclave.nsga.ii.objectivefunction;

import io.onclave.nsga.ii.Interface.IObjectiveFunction;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.ParetoObject;

/**
 *
 * @author sajib
 */
public class ZDT1_1 implements IObjectiveFunction {
    
    private static final String AXIS_TITLE = "x1";

    @Override
    public double objectiveFunction(double geneVaue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double objectiveFunction(Chromosome chromosome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double objectiveFunction(ParetoObject paretoObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAxisTitle() {
        return AXIS_TITLE;
    }
    
}
