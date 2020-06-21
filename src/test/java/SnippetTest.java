import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SnippetTest {

	public static void main(String[] args) {
		nsga2sorting();
	}

	private static void nsga2sorting() {

		Configuration configuration = new Configuration(10, 1, 10);
		NSGA2 nsga2 = new NSGA2(configuration);

		Population parent = configuration.getPopulationProducer().produce(
			configuration.getPopulationSize(),
			configuration.getChromosomeLength(),
			configuration.getGeneticCodeProducer(),
			null
		);



		//////
		Service.calculateObjectiveValues(parent, configuration.objectives);
		nsga2.fastNonDominatedSort(parent);
		nsga2.crowdingDistanceAssignment(parent);
		///////

		// rank based sort
//		parent.getPopulace().sort(Comparator.comparingInt(Chromosome::getRank));


//		Reporter.p("\n");
//		Reporter.reportPopulation(parent);
//		Reporter.p("\n======================\n\n");




		// objective based sort
//		parent.getPopulace().sort(Collections.reverseOrder(Comparator.comparingDouble(c -> c.getObjectiveValue(0))));







//		Population child = configuration.getChildPopulationProducer().produce(
//			parent,
//			configuration.getCrossover(),
//			configuration.getMutation(),
//			configuration.getPopulationSize()
//		);







		Reporter.p("\n");
		Reporter.reportPopulation(parent);
	}

	private static void binary2decimal() {

		List<Boolean> binary = new ArrayList<>();

		binary.add(false);
		binary.add(false);
		binary.add(true);
		binary.add(true);
		binary.add(false);
		binary.add(false);
		binary.add(true);
		binary.add(false);
		binary.add(true);
		binary.add(true);

		double value = 0;
		StringBuilder binaryString = new StringBuilder();

		for(Boolean bit : binary) binaryString.append(bit ? "1" : "0");

		for(int i = 0; i < binaryString.length(); i++)
			if(binaryString.charAt(i) == '1')
				value += Math.pow(2, binaryString.length() - 1 - i);

		Reporter.p("\n\nvalue is: " + value);
	}

	private static void sortDescending() {

		List<Integer> numbers = Service.generateUniqueRandomNumbers(10);

		numbers.forEach(System.out::println);

		numbers.sort((a, b) -> (b - a));

		Reporter.p("\n\n");
		numbers.forEach(System.out::println);
	}

	private static void randomNumberTest() {

		List<Integer> numbers = Service.generateUniqueRandomNumbers(2, 10);

		numbers.forEach(System.out::println);
		Reporter.p("\n\n");

		for(int i = 0; i < (numbers.size() / 2); i++) {
			int j = i + (numbers.size() / 2);
			System.out.println("Swap: " + numbers.get(i) + " --> " + numbers.get(j));
		}
	}
}
