package ranking;

import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;
import ranking.datastructure.DataMatrix;
import ranking.datastructure.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KendelsTao extends AbstractObjectiveFunction {

	private final DataMatrix dataMatrix;
	private final List<Double> kendelsTaoDistances = new ArrayList<>();

	public KendelsTao(DataMatrix dataMatrix) {
		this.objectiveFunctionTitle = "Kendel's Tao";
		this.dataMatrix = dataMatrix;
	}

	@Override
	public double getValue(Chromosome chromosome) {

		if(!(chromosome.getAllele(0) instanceof IntegerAllele))
			throw new UnsupportedOperationException("Kendel's Tao works only with Integer Allele for Permutation Encoding");

		for(Sample sample : this.dataMatrix.getSamples())
			this.kendelsTaoDistances.add(this.calculateT(chromosome.getGeneticCode(), sample));

		return this.kendelsTaoDistances.stream().mapToDouble(i -> i).sum() / this.dataMatrix.sampleCount();
	}

	public List<Double> getKendelsTaoDistances() {
		return this.kendelsTaoDistances;
	}

	private double calculateT(List<AbstractAllele> geneticCode, Sample sample) {

		List<IntegerAllele> integerGeneticCode = geneticCode.stream().map(e -> (IntegerAllele)e).collect(Collectors.toList());
		List<Integer> geneticCodeValues = new ArrayList<>();
		List<Integer> sampleValues = new ArrayList<>();
		List<Integer> substractionSet1 = new ArrayList<>();
		List<Integer> substractionSet2 = new ArrayList<>();
		List<Integer> multiplicationSet = new ArrayList<>();
		int sampleLength = sample.length();
		int negativeCount = 0;
		double normalizedDistance;

		integerGeneticCode.forEach(v -> geneticCodeValues.add(v.getGene()));
		sample.getDataPoints().forEach(v -> sampleValues.add(v.getValue()));

		List<List<Integer>> combination1 = this.getCombinations(geneticCodeValues);
		List<List<Integer>> combination2 = this.getCombinations(sampleValues);

		combination1.forEach(combination -> substractionSet1.add(combination.get(0) - combination.get(1)));
		combination2.forEach(combination -> substractionSet2.add(combination.get(0) - combination.get(1)));

		for(int i = 0; i < combination1.size(); i++)
			multiplicationSet.add(substractionSet1.get(i) * substractionSet2.get(i));

		for(int value : multiplicationSet)
			if(value < 0)
				++negativeCount;

		normalizedDistance = negativeCount / ((sampleLength * (sampleLength - 1)) / 2.0d);

		return normalizedDistance;
	}

	private List<List<Integer>> getCombinations(List<Integer> values) {

		List<List<Integer>> combinationList = new ArrayList<>();

		for(int i = 0; i < values.size() - 1; i++)
			for(int j = i + 1; j < values.size(); j++) {

				List<Integer> combination = new ArrayList<>();

				combination.add(0, values.get(i));
				combination.add(1, values.get(j));

				combinationList.add(combination);
			}

		return combinationList;
	}
}

/*

		1	3	6	8
		6	5	9	3
		8	6	4	3
		1	4	2	7


		6	4	5	2


		comparison between sample 1 and chromosome:

		(6, 4)	(1, 3)	---> (6 - 4) = 2	(1 - 3) = -2	---> (2 * -2) = -4	---> Compare number of negative values
		(6, 5)	(1, 6)	---> (6 - 5) = 1	(1 - 6) = -5	---> (1 * -5) = -5	---> This is the Kendal's Tao Distance
		(6, 2)	(1, 8)	---> (6 - 2) = 4	(1 - 8) = -7	---> (4 * -7) = -28
		(4, 5)	(3, 6)	---> (4 - 5) = -1	(3 - 6) = -3	---> (-1 * -3) = 3
		(4, 2)	(3, 8)	---> (4 - 2) = 2	(3 - 8) = -5	---> (2 * -5) = -10
		(5, 2)	(6, 8)	---> (5 - 2) = 3	(6 - 8) = -2	---> (3 * -2) = -6
 */
