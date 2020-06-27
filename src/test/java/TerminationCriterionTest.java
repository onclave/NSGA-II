import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.crossover.SimulatedBinaryCrossover;
import com.debacharya.nsgaii.mutation.PolynomialMutation;
import com.debacharya.nsgaii.objectivefunction.ObjectiveProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;
import com.debacharya.nsgaii.termination.StabilizationOfMaximalCrowdingDistance;

public class TerminationCriterionTest {

	public static void main(String[] args) {

		StabilizationOfMaximalCrowdingDistance tc = new StabilizationOfMaximalCrowdingDistance(40, 0.025d);
		Configuration configuration = new Configuration(ObjectiveProvider.provideZDTObjectives());

		configuration.setGeneticCodeProducer(GeneticCodeProducerProvider.valueEncodedGeneticCodeProducer(0, 1, false));
		configuration.setCrossover(new SimulatedBinaryCrossover(
			CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection(),
			20
		));
		configuration.setMutation(new PolynomialMutation(0, 1));
		configuration.setTerminatingCriterion(tc);
		configuration.setGenerations(110);
		configuration.setPopulationSize(100);
		configuration.beSilent();

		NSGA2 nsga2 = new NSGA2(configuration);

		nsga2.run();
	}
}
