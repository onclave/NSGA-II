import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.crossover.OrderCrossover;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

public class OrderCrossoverTest {

	public static void main(String[] args) {

		Population population = DefaultPluginProvider.defaultPopulationProducer().produce(
			10,
			10,
			GeneticCodeProducerProvider.permutationEncodingGeneticCodeProducer(),
			null
		);

		OrderCrossover orderCrossover = new OrderCrossover(
			CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()
		);

		orderCrossover.perform(population);

//		Reporter.reportPopulation(population);
	}
}
