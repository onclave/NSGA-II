import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.plugin.GeneticCodeProducerProvider;
import ranking.KendelsTao;
import ranking.datastructure.DataMatrix;
import ranking.datastructure.DataPoint;
import ranking.datastructure.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class KendelTaoTest {

	public static void main(String[] args) {

		KendelsTao kendelsTao = new KendelsTao(new DataMatrix(null));
		List<AbstractAllele> geneticCode = GeneticCodeProducerProvider
											.permutationEncodingGeneticCodeProducer()
											.produce(10)
											.stream()
											.map(e -> (IntegerAllele) e)
											.collect(Collectors.toList());

		Reporter.reportConcreteGeneticCode(geneticCode);


	}

	private static Sample generateSample(int length) {

		List<DataPoint> dataPoints = new ArrayList<>();

		for(int i = 0; i < length; i++)
			dataPoints.add(i, new DataPoint(
				ThreadLocalRandom.current().nextInt(1, length + 1)
			));

		return new Sample(dataPoints);
	}
}
