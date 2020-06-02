import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import com.debacharya.nsgaii.objectivefunction.ZDT1_1;
import com.debacharya.nsgaii.objectivefunction.ZDT1_2;
import com.debacharya.nsgaii.plugin.CrossoverParticipantCreatorProvider;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.PolynomialMutation;
import com.debacharya.nsgaii.plugin.SimulatedBinaryCrossover;

import java.util.ArrayList;
import java.util.List;

public class ZDT1Test {

	public static void main(String[] args) {

		List<AbstractObjectiveFunction> objectives = new ArrayList<>();

		objectives.add(new ZDT1_1());
		objectives.add(new ZDT1_2());

		Configuration configuration = new Configuration();

		configuration.objectives = objectives;

		configuration.setPopulationSize(100);
		configuration.setGenerations(500);
		configuration.setChromosomeLength(30);
		configuration.setGeneticCodeProducer(DefaultPluginProvider.valueEncodedGeneticCodeProducer(0, 1));
		configuration.setMutation(new PolynomialMutation(0, 1));
		configuration.setCrossover(new SimulatedBinaryCrossover(
			CrossoverParticipantCreatorProvider.selectByBinaryTournamentSelection()
		));

		(new NSGA2(configuration)).run();
	}
}
