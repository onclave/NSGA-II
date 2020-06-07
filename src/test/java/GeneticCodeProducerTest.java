import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.plugin.DefaultPluginProvider;

import java.util.List;
import java.util.stream.Collectors;

public class GeneticCodeProducerTest {

	public static void main(String[] args) {

		List<? extends AbstractAllele> geneticCode = DefaultPluginProvider.defaultGeneticCodeProducer().produce(7000);

		Reporter.reportGeneticCode(geneticCode.stream().map(e -> (AbstractAllele) e).collect(Collectors.toList()));
	}
}
