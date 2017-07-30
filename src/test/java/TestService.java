
import io.onclave.nsga.ii.api.Reporter;
import io.onclave.nsga.ii.api.Service;
import io.onclave.nsga.ii.api.Synthesis;
import io.onclave.nsga.ii.configuration.Configuration;
import io.onclave.nsga.ii.datastructure.Allele;
import io.onclave.nsga.ii.datastructure.Chromosome;
import io.onclave.nsga.ii.datastructure.Population;
import java.util.HashMap;
import java.util.List;

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
public class TestService {
    
    public static void main(String[] args) {
        
        Configuration.buildObjectives();
        
        decodeGeneticCodeTest();
        calculateFitnessTest();
        createCombinedPopulationTest();
        fastNonDominatedSortTest();
    }
    
    public static void decodeGeneticCodeTest() {
        
        p("\ndecodeGeneticCodeTest : START\n");
        
        for(int i = 0; i < 10; i++) p("Fitness : " + Service.decodeGeneticCode(Synthesis.synthesizeGeneticCode(10)));
        
        p("\ndecodeGeneticCodeTest : END\n");
    }
    
    public static void calculateFitnessTest() {
        
        p("\ncalculateFitnessTest : START\n");
        
        for(int i = 0; i < 10; i++) p("Fitness : " + Service.calculateFitness(Synthesis.synthesizeGeneticCode(10)));
        
        p("\ncalculateFitnessTest : END\n");
    }
    
    public static void createCombinedPopulationTest() {
        
        p("\ncreateCombinedPopulationTest : START\n");
        
        Population parent = Synthesis.syntesizePopulation();
        Population child = Synthesis.synthesizeChild(parent);
        Population combined = Service.createCombinedPopulation(parent, child);
        
        p("\nPARENT : ");
        
        Reporter.reportPopulation(parent);
        
        p("\n\nCHILD : ");
        
        Reporter.reportPopulation(child);
        
        p("\nCOMBINED");
        
        Reporter.reportPopulation(combined);
        
        p("\ncreateCombinedPopulationTest : END\n");
    }
    
    public static void fastNonDominatedSortTest() {
        
        p("\nfastNonDominatedSortTest : START\n");
        
        Population parent = Synthesis.syntesizePopulation();
        Population child = Synthesis.synthesizeChild(parent);
        Population combined = Service.createCombinedPopulation(parent, child);
        
        p("\nPARENT : ");
        
        Reporter.reportPopulation(parent);
        
        p("\n\nCHILD : ");
        
        Reporter.reportPopulation(child);
        
        p("\nCOMBINED");
        
        Reporter.reportPopulation(combined);
        
        p("\nPARETO FRONT");
        
        Reporter.reportParetoFront(Service.fastNonDominatedSort(combined));
        
        p("\nfastNonDominatedSortTest : END\n");
    }
    
    public static void p(String string) {
        System.out.println(string);
    }
}
