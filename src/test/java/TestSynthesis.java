
import io.onclave.nsga.ii.api.Reporter;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;

/*
 * This code file and the codebase/software containing it is 
 * explicitely licensed to Mr. Debabrata Acharya (@onclave)
 * unauthorized use and access of the codebase, parts of the 
 * codebase, software or parts of this software is not allowed.
 */

/**
 *
 * @author sajib
 */
public class TestSynthesis {
    
    public static void main(String[] args) {
        
        Configuration.buildObjectives();
        
        synthesizeAlleleTest();
        synthesizeGeneticCodeTest();
        syntesizePopulationTest();
        crossoverTest();
        synthesizeChildTest();
    }
    
    public static void synthesizeAlleleTest() {
        
        p("\nsynthesizeAlleleTest : START\n");
        
        for(int i = 0; i < 10; i++) p("Random Allele : " + Synthesis.synthesizeAllele().getGene());
        
        p("\nsynthesizeAlleleTest : END\n");
    }
    
    public static void synthesizeGeneticCodeTest() {
        
        p("\nsynthesizeGeneticCodeTest : START\n");
        
        Reporter.reportGeneticCode(Synthesis.synthesizeGeneticCode(10));
        
        p("\nsynthesizeGeneticCodeTest : END\n");
    }
    
    public static void syntesizePopulationTest() {
        
        p("\nsynthesizePopulationTest : START\n");
        
        Reporter.reportPopulation(Synthesis.syntesizePopulation());
        
        p("\nsynthesizePopulationTest : END");
    }
    
    public static void crossoverTest() {
        
        p("\ncrossoverTest : START\n");
        
        Chromosome chromosome1 = new Chromosome();
        Chromosome chromosome2 = new Chromosome();
        
        chromosome1.setGeneticCode(Synthesis.synthesizeGeneticCode(Configuration.getCHROMOSOME_LENGTH()));
        chromosome2.setGeneticCode(Synthesis.synthesizeGeneticCode(Configuration.getCHROMOSOME_LENGTH()));
        
        Reporter.reportGeneticCode(chromosome1.getGeneticCode());
        Reporter.reportGeneticCode(chromosome2.getGeneticCode());
        
        Chromosome[] chromosomes = Synthesis.crossover(chromosome1, chromosome2);
        
        for(Chromosome chromosome : chromosomes) Reporter.reportGeneticCode(chromosome.getGeneticCode());
        
        p("\ncrossoverTest : END");
    }
    
    public static void synthesizeChildTest() {
        
        p("\nsynthesizeChildTest : START\n");
        
        Population parent = Synthesis.syntesizePopulation();
        
        p("\nPARENT : ");
        
        Reporter.reportPopulation(parent);
        
        p("\n\nCHILD : ");
        
        Reporter.reportPopulation(Synthesis.synthesizeChild(parent));
        
        p("\nsynthesizeChildTest : END");
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
