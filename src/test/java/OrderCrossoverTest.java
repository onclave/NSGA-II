import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.crossover.OrderCrossover;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

import java.util.List;

public class OrderCrossoverTest {

	public static void main(String[] args) {

		for(int i = 0; i < 10; i++)
			test();
	}

	private static void test() {

		Population population = DefaultPluginProvider.defaultPopulationProducer().produce(
			10,
			10,
			GeneticCodeProducerProvider.permutationEncodingGeneticCodeProducer(),
			null
		);

		OrderCrossover orderCrossover = new OrderCrossover(
			CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()
		);

		List<Chromosome> children = orderCrossover.perform(population);

		children.forEach(Reporter::reportChromosome);
	}
}
