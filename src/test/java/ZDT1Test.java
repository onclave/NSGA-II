import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.crossover.SimulatedBinaryCrossover;
import com.debacharya.nsgaii.mutation.PolynomialMutation;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.objectivefunction.ZDT1_1;
import com.debacharya.nsgaii.objectivefunction.ZDT1_2;
import com.debacharya.nsgaii.plugin.*;

import java.util.ArrayList;
import java.util.List;

public class ZDT1Test {

	public static void main(String[] args) {

		List<AbstractObjectiveFunction> objectives = new ArrayList<>();

		objectives.add(new ZDT1_1());
		objectives.add(new ZDT1_2());

		Configuration configuration = new Configuration();

		configuration.objectives = objectives;

		configuration.setPopulationSize(200);
		configuration.setGenerations(100);
		configuration.setChromosomeLength(30);
		configuration.setGeneticCodeProducer(GeneticCodeProducerProvider.valueEncodedGeneticCodeProducer(0, 1, false));
		configuration.setMutation(new PolynomialMutation(0, 1));
		configuration.setCrossover(new SimulatedBinaryCrossover(
			CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()
		));

		(new NSGA2(configuration)).run();
	}
}
