import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

public class TestChromosomeEquality {

	public static void main(String[] args) {

		Chromosome chromosome1 = new Chromosome(GeneticCodeProducerProvider.binaryGeneticCodeProducer().produce(10));
		Chromosome chromosome2 = new Chromosome(GeneticCodeProducerProvider.binaryGeneticCodeProducer().produce(10));
		Chromosome chromosome3 = new Chromosome(chromosome1.getGeneticCode());

		Reporter.p("\n");
		Reporter.reportChromosome(chromosome1);
		Reporter.reportChromosome(chromosome2);

		Reporter.p(chromosome1.equals(chromosome2));
		Reporter.p(chromosome1.equals(chromosome3));
	}
}
