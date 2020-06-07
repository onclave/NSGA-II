import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.Population;

public class NSGA2Test {

	public static void main(String[] args) {

		Configuration configuration = new Configuration();
		NSGA2 nsga2 = new NSGA2(configuration);
		Population paretoFront = nsga2.run();
	}
}
