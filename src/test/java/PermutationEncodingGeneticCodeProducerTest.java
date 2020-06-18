import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

public class PermutationEncodingGeneticCodeProducerTest {

	public static void main(String[] args) {

		Reporter.p("\n\n");

		for(int i = 0; i < 10; i++)
			Reporter.reportConcreteGeneticCode(
				GeneticCodeProducerProvider
					.PermutationEncodingGeneticCodeProducer()
					.produce(10)
			);
	}
}
