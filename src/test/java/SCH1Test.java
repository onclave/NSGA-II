import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.plugin.FitnessCalculator;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

import java.util.stream.Collectors;

public class SCH1Test {

	public static void main(String[] args) {

		Chromosome chromosome = new Chromosome(GeneticCodeProducerProvider.binaryGeneticCodeProducer().produce(10));
		FitnessCalculator fitnessCalculator = c -> {
			return Service.convertBinaryGeneticCodeToDecimal(
				c.getGeneticCode().stream().map(e -> (BooleanAllele) e).collect(Collectors.toList())
			);
		};

		Reporter.reportChromosome(chromosome);
		Reporter.p(fitnessCalculator.calculate(chromosome));
	}
}
