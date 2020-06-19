package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.Reporter;
import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.datastructure.ValueAllele;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GeneticCodeProducerProvider {

	public static GeneticCodeProducer binaryGeneticCodeProducer() {
		return (length) -> {

			List<BooleanAllele> geneticCode = new ArrayList<>();

			for(int i = 0; i < length; i++)
				geneticCode.add(i, new BooleanAllele(ThreadLocalRandom.current().nextBoolean()));

			return geneticCode;
		};
	}

	public static GeneticCodeProducer valueEncodedGeneticCodeProducer(double origin, double bound, boolean unique) {
		return length -> {

			int count = -1;
			List<ValueAllele> geneticCode = new ArrayList<>();

			while(count < (length - 1)) {

				double value = ThreadLocalRandom.current().nextDouble(origin, (bound + 0.1));

				if(value > bound)
					value = bound;

				value = Service.roundOff(value, 4);

				if(unique && Service.isInGeneticCode(geneticCode, value))
					continue;

				geneticCode.add(
					++count,
					new ValueAllele(value)
				);
			}

			return geneticCode;
		};
	}

	public static GeneticCodeProducer permutationEncodingGeneticCodeProducer() {
		return length -> {

			List<ValueAllele> valueEncodedGeneticCode = GeneticCodeProducerProvider
															.valueEncodedGeneticCodeProducer(0, 1, true)
															.produce(length)
															.stream()
															.map(e -> (ValueAllele)e)
															.collect(Collectors.toList());

			List<ValueAllele> originalGeneticCode = new ArrayList<>(valueEncodedGeneticCode);
			List<IntegerAllele> permutationEncodedGeneticCode = new ArrayList<>();

			valueEncodedGeneticCode.sort(Comparator.comparingDouble(ValueAllele::getGene));

			originalGeneticCode.forEach(e -> {
				valueEncodedGeneticCode.forEach(v -> {
					if(e.getGene().equals(v.getGene()))
						permutationEncodedGeneticCode.add(
							new IntegerAllele(
								valueEncodedGeneticCode.indexOf(v) + 1
							)
						);
				});
			});

			return permutationEncodedGeneticCode;
		};
	}
}
