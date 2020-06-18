import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;

import java.util.List;
import java.util.stream.Collectors;

public class GeneticCodeProducerTest {

	public static void main(String[] args) {

		List<? extends AbstractAllele> geneticCode = GeneticCodeProducerProvider.binaryGeneticCodeProducer().produce(7000);

		Reporter.reportGeneticCode(geneticCode.stream().map(e -> (AbstractAllele) e).collect(Collectors.toList()));
	}
}
