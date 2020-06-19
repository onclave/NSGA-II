import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.Comparator;

public class FrontSortTest {

	public static void main(String[] args) {

		Configuration configuration = new Configuration(200, 1, 100);
		NSGA2 nsga2 = new NSGA2(configuration);

		Population parent = configuration.getPopulationProducer().produce(
			configuration.getPopulationSize(),
			configuration.getChromosomeLength(),
			configuration.getGeneticCodeProducer(),
			null
		);

		Service.calculateObjectiveValues(parent, configuration.objectives);
		nsga2.fastNonDominatedSort(parent);
		nsga2.crowdingDistanceAssignment(parent);
		parent.getPopulace().sort(Comparator.comparingInt(Chromosome::getRank));

		Service.sortFrontWithCrowdingDistance(parent.getPopulace(), 2);

//		Reporter.p("\n");
//		Reporter.reportPopulation(parent);
	}
}
