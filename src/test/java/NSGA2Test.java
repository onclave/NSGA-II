import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.datastructure.Population;

public class NSGA2Test {

	public static void main(String[] args) {

		NSGA2 nsga2 = new NSGA2();
		Population paretoFront = nsga2.run();
	}
}
