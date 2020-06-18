import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.mutation.SwapMutation;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

import java.util.List;

public class SwapMutationTest {

	public static void main(String[] args) {

		List<? extends AbstractAllele> geneticCode = GeneticCodeProducerProvider.PermutationEncodingGeneticCodeProducer().produce(10);
		SwapMutation swapMutation = new SwapMutation();
		Chromosome chromosome = new Chromosome(geneticCode);
		Chromosome mutatedChromosome = swapMutation.perform(chromosome);

		Reporter.p("\n");
		Reporter.reportConcreteGeneticCode(geneticCode);
		Reporter.reportGeneticCode(mutatedChromosome.getGeneticCode());
	}
}
