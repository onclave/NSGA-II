/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package io.onclave.nsga.ii.api;

import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;
import org.jfree.ui.RefineryUtilities;

import java.util.HashMap;
import java.util.List;

/**
 * this is the add-on class that communicates with the console and prints appropriate object
 * details as necessary.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   1.0
 */
public class Reporter {
    
    public static void render2DGraph(final Population population) {
        
        if(Configuration.getObjectives().size() > 2) {
            p("\n\nThis Implementation has more than 2 objectives and cannot be plotted on a 2D graph. Either minimize objectives to 2, or use other plotting implemenataions.\n\n"); return;
        }
        
        GraphPlot graph = new GraphPlot(population);
                
        graph.configurePlotter(Configuration.getXaxisTitle(), Configuration.getYaxisTitle());
        graph.pack();

        RefineryUtilities.centerFrameOnScreen(graph);

        graph.setVisible(true);
    }
    
    public static void reportPopulation(Population population) {
        
        int i = 1;
        
        for(Chromosome chromosome : population.getPopulace())
            p("Chromosome " + i++ + " : " + chromosome.getUniqueID() + " | " + chromosome.getFitness());
    }
    
    public static void reportGeneticCode(Allele[] geneticCode) {
        
        String geneticString = "";
        
        for(Allele allele : geneticCode) geneticString += allele.getGene() ? "1" : "0";
        
        p("GENETIC CODE : " + geneticString);
    }
    
    public static void reportSingularFront(List<Chromosome> singularFront, int i) {
        
        p("\n\nFRONT : " + i);

        for(Chromosome c : singularFront) {
            int j = 0;
            p("\tmaster : " + c.getUniqueID() + " | " + Double.toString(c.getFitness()));
            for(Chromosome dc : c.getDominatedChromosomes()) p("\t\tslave : " + ++j + " | " + dc.getUniqueID() + " | " + Integer.toString(dc.getDominationRank()));
        }
    }
    
    public static void reportParetoFront(HashMap<Integer, List<Chromosome>> paretoFront) {
        
        int j = 1;
        int max = paretoFront.size();
        
        for(int i = 1; i <= max; i++) {
            
            p("\n\nFRONT : " + i);
            
            int d = 0;
            List<Chromosome> population = paretoFront.get(i);
            
            if(population != null && !population.isEmpty()) {
                
                for(Chromosome c : population) {
                    
                    p("\tmaster : " + c.getUniqueID() + " | " + Double.toString(c.getFitness()));
                    
                    for(Chromosome dc : c.getDominatedChromosomes()) p("\t\tslave : " + ++d + " | " + dc.getUniqueID() + " | " + Integer.toString(dc.getDominationRank()));
                }
            }
        }
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
