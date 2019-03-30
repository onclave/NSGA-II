/*
 * This code / file / algorithm is completely free to use and modify as necessary.
 * Any attribution is welcome and highly appriciated.
 */
package io.onclave.nsga.ii.algorithm;

import io.onclave.nsga.ii.api.Configuration;
import io.onclave.nsga.ii.api.GraphPlot;
import io.onclave.nsga.ii.api.NSGAII;
import io.onclave.nsga.ii.api.Reporter;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.datastructure.Population;
import java.io.IOException;
import org.jfree.ui.RefineryUtilities;

/**
 * This is the starting point of the main NSGA-II algorithm.
 * Run this class to get the desired output.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 2.0
 * @since   2.0
 */
public class Algorithm {
    
    public static void main(String[] args) throws IOException {
        
        Configuration.configure();
        
        GraphPlot multiPlotGraph = new GraphPlot();
        
        Reporter.reportInitialParentPopulationGeneration();
        Reporter.reportGeneration(1);
        
        Population parent = NSGAII.preparePopulation(Synthesis.syntesizePopulation());
        Population child = Synthesis.synthesizeChild(parent);
        Population combinedPopulation;
        
        for(int generation = 1; generation <= Configuration.GENERATIONS; generation++) {
            
            Reporter.reportGeneration(generation + 1);
            
            combinedPopulation = NSGAII.preparePopulation(Synthesis.createCombinedPopulation(parent, child));
            parent = NSGAII.getChildFromCombinedPopulation(combinedPopulation);
            child = Synthesis.synthesizeChild(parent);
            
            multiPlotGraph.prepareMultipleDataset(child, generation, "gen. " + generation);
        }
        
        Reporter.reportGraphPlotAlert();
        Reporter.render2DGraph(child);
        
        /**
         * the plotted and rendered chart/graph is viewed to the user.
         */
        multiPlotGraph.configureMultiplePlotter(Configuration.getXaxisTitle(), Configuration.getYaxisTitle(), "All Pareto");
        multiPlotGraph.pack();
        RefineryUtilities.centerFrameOnScreen(multiPlotGraph);
        multiPlotGraph.setVisible(true);
        
        Reporter.reportAlgorithmEnd();
    }
}
