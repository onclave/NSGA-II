/*
 * MIT License
 *
 * Copyright (c) 2019 Debabrata Acharya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.debacharya.nsgaii;

import com.debacharya.nsgaii.datastructure.BooleanAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Service {

	private Service() {}

	public static Chromosome crowdedBinaryTournamentSelection(Population population) {

		Chromosome participant1 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, population.size()));
		Chromosome participant2 = population.getPopulace().get(ThreadLocalRandom.current().nextInt(0, population.size()));

		if(participant1.getRank() < participant2.getRank())
			return participant1;
		else if(participant1.getRank() == participant2.getRank()) {
			if(participant1.getCrowdingDistance() > participant2.getCrowdingDistance())
				return participant1;
			else if(participant1.getCrowdingDistance() < participant2.getCrowdingDistance())
				return participant2;
			else return ThreadLocalRandom.current().nextBoolean() ? participant1 : participant2;
		} else return participant2;
	}

	public static Population combinePopulation(Population parent, Population child) {

		List<Chromosome> populace = parent.getPopulace();

		populace.addAll(child.getPopulace());

		return new Population(populace);
	}

	public static void sortForCrowdingDistance(List<Chromosome> populace, int lastNonDominatedSetRank) {

		int rankStartIndex = -1;
		int rankEndIndex = -1;

		for(int i = 0; i < populace.size(); i++)
			if(rankStartIndex < 0 && populace.get(i).getRank() == lastNonDominatedSetRank)
				rankStartIndex = i;
			else if(rankStartIndex >= 0 && populace.get(i).getRank() == lastNonDominatedSetRank)
				rankEndIndex = i;

		Service.randomizedQuickSortForCrowdingDistance(populace, rankStartIndex, rankEndIndex);
	}

	public static void randomizedQuickSortForRank(List<Chromosome> populace, int head, int tail) {

		if(head < tail) {

			int pivot = Service.randomizedPartitionForRank(populace, head, tail);

			Service.randomizedQuickSortForRank(populace, head, pivot - 1);
			Service.randomizedQuickSortForRank(populace, pivot + 1, tail);
		}
	}

	public static void randomizedQuickSortForObjective(List<Chromosome> populace, int head, int tail, int objectiveIndex) {

		if(head < tail) {

			int pivot = Service.randomizedPartitionForObjective(populace, head, tail, objectiveIndex);

			Service.randomizedQuickSortForObjective(populace, head, pivot - 1, objectiveIndex);
			Service.randomizedQuickSortForObjective(populace, pivot + 1, tail, objectiveIndex);
		}
	}

	public static void randomizedQuickSortForCrowdingDistance(List<Chromosome> populace, int head, int tail) {

		if(head < tail) {

			int pivot = Service.randomizedPartitionForCrowdingDistance(populace, head, tail);

			Service.randomizedQuickSortForCrowdingDistance(populace, head, pivot - 1);
			Service.randomizedQuickSortForCrowdingDistance(populace, pivot + 1, tail);
		}
	}

	public static void calculateObjectiveValues(Chromosome chromosome) {
		for(int i = 0; i < Configuration.objectives.size(); i++)
			chromosome.addObjectiveValue(i, Configuration.objectives.get(i).getValue(chromosome));
	}

	public static void calculateObjectiveValues(Population population) {
		for(Chromosome chromosome : population.getPopulace())
			Service.calculateObjectiveValues(chromosome);
	}

	public static void normalizeSortedObjectiveValues(Population population, int objectiveIndex) {

		double actualMin = population.get(0).getObjectiveValues().get(objectiveIndex);
		double actualMax = population.getLast().getObjectiveValues().get(objectiveIndex);

		for(Chromosome chromosome : population.getPopulace())
			chromosome.setNormalizedObjectiveValue(
					objectiveIndex,
					Service.minMaxNormalization(
							chromosome.getObjectiveValues().get(objectiveIndex),
							actualMin,
							actualMax
					)
			);
	}

	public static double getNormalizedGeneticCodeValue(List<BooleanAllele> geneticCode,
													   double actualMin,
													   double actualMax,
													   double normalizedMin,
													   double normalizedMax) {
		return Service.minMaxNormalization(
				Service.convertBinaryGeneticCodeToDecimal(geneticCode),
				actualMin,
				actualMax,
				normalizedMin,
				normalizedMax
		);
	}

	/**
	 * this method decodes the genetic code that is represented as a string of binary values, converted into
	 * decimal value.
	 *
	 * @param   geneticCode     the genetic code as an array of Allele. Refer Allele.java for more information
	 * @return                  the decimal value of the corresponding binary string.
	 */
	public static double convertBinaryGeneticCodeToDecimal(final List<BooleanAllele> geneticCode) {

		double value = 0;
		StringBuilder binaryString = new StringBuilder();

		for(BooleanAllele bit : geneticCode) binaryString.append(bit.toString());

		for(int i = 0; i < binaryString.length(); i++)
			if(binaryString.charAt(i) == '1')
				value += Math.pow(2, binaryString.length() - 1 - i);

		return value;
	}

	/**
	 * an implementation of min-max normalization
	 *
	 * @param   value   		the value that is to be normalized
	 * @param   actualMin   	the actual minimum value of the original scale
	 * @param   actualMax   	the actual maximum value of the original sclae
	 * @param   normalizedMin   the normalized minimum value of the target scale
	 * @param   normalizedMax   the normalized maximum value of the target scale
	 * @return          the normalized value
	 */
	public static double minMaxNormalization(double value,
											  double actualMin,
											  double actualMax,
											  double normalizedMin,
											  double normalizedMax) {
		return (((value - actualMin) / (actualMax - actualMin)) * (normalizedMax - normalizedMin)) + normalizedMin;
	}

	public static double minMaxNormalization(double value, double actualMin, double actualMax) {
		return Service.minMaxNormalization(value, actualMin, actualMax, 0, 1);
	}

	public static double roundOff(double value, double decimalPlace) {

		if(value == Double.MAX_VALUE || value == Double.MIN_VALUE) return value;

		decimalPlace = Math.pow(10, decimalPlace);

		return (Math.round(value * decimalPlace) / decimalPlace);
	}

	private static void swapForRank(List<Chromosome> populace, int firstIndex, int secondIndex) {

		Chromosome temporary = populace.get(firstIndex);

		populace.set(firstIndex, populace.get(secondIndex));
		populace.set(secondIndex, temporary);
	}

	private  static void swapForObjective(List<Chromosome> populace, int firstIndex, int secondIndex) {

		Chromosome temporary = populace.get(firstIndex);

		populace.set(firstIndex, populace.get(secondIndex));
		populace.set(secondIndex, temporary);
	}

	private static void swapForCrowdingDistance(List<Chromosome> populace, int firstIndex, int secondIndex) {

		Chromosome temporary = populace.get(firstIndex);

		populace.set(firstIndex, populace.get(secondIndex));
		populace.set(secondIndex, temporary);
	}

	private static int partitionForRank(List<Chromosome> populace, int head, int tail) {

		int pivot = populace.get(tail).getRank();
		int pivotIndex = head;

		for(int j = head; j < tail; j++)
			if(populace.get(j).getRank() <= pivot)
				Service.swapForRank(populace, pivotIndex++, j);

		Service.swapForRank(populace, pivotIndex, tail);

		return pivotIndex;
	}

	private static int partitionForObjective(List<Chromosome> populace, int head, int tail, int objectiveIndex) {

		double pivot = populace.get(tail).getObjectiveValues().get(objectiveIndex);
		int pivotIndex = head;

		for(int j = head; j < tail; j++)
			if(populace.get(j).getObjectiveValues().get(objectiveIndex) <= pivot)
				Service.swapForObjective(populace, pivotIndex++, j);

		Service.swapForObjective(populace, pivotIndex, tail);

		return pivotIndex;
	}

	private static int partitionForCrowdingDistance(List<Chromosome> populace, int head, int tail) {

		double pivot = populace.get(tail).getCrowdingDistance();
		int pivotIndex = head;

		for(int j = head; j < tail; j++)
			if(populace.get(j).getCrowdingDistance() >= pivot)
				Service.swapForCrowdingDistance(populace, pivotIndex++, j);

		Service.swapForCrowdingDistance(populace, pivotIndex, tail);

		return pivotIndex;
	}

	private static int randomizedPartitionForRank(List<Chromosome> populace, int head, int tail) {

		Service.swapForRank(
			populace,
			head,
			ThreadLocalRandom.current().nextInt(
				head,
				tail + 1
			)
		);

		return Service.partitionForRank(populace, head, tail);
	}

	private static int randomizedPartitionForObjective(List<Chromosome> populace, int head, int tail, int objectiveIndex) {

		Service.swapForObjective(
			populace,
			head,
			ThreadLocalRandom.current().nextInt(
				head,
				tail + 1
			)
		);

		return Service.partitionForObjective(populace, head, tail, objectiveIndex);
	}

	private static int randomizedPartitionForCrowdingDistance(List<Chromosome> populace, int head, int tail) {

		Service.swapForCrowdingDistance(
			populace,
			head,
			ThreadLocalRandom.current().nextInt(
				head,
				tail + 1
			)
		);

		return Service.partitionForCrowdingDistance(populace, head, tail);
	}
}
