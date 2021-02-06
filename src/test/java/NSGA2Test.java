import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.termination.StabilizationOfObjectives;

public class NSGA2Test {

	public static void main(String[] args) {

		Configuration configuration = new Configuration();

		configuration.beSilent();
		configuration.setGenerations(200);
		configuration.setTerminatingCriterion(new StabilizationOfObjectives(0.03d));

		NSGA2 nsga2 = new NSGA2(configuration);
		Population paretoFront = nsga2.run();
	}
}
