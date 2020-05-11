/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.interfaces;

/**
 * 
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public interface IObjectiveFunction {
    
    public String objectiveFunctionTitle();
    public double getObjectiveValue(double fitness);
}
