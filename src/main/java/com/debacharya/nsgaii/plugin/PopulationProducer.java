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

package com.debacharya.nsgaii.plugin;

import com.debacharya.nsgaii.datastructure.Population;

/**
 * The PopulationProducer interface is used to produce a population of chromosomes for the algorithm to use.
 * It has only one method, `produce()` that produces a fixed amount of chromosomes using the `GeneticCodeProvider`, each having
 * a fixed chromosome length. It also integrates a `FitnessCalculator` which can be used to provide any extra computation required
 * to perform during the creation of the population. Note that any concrete implementation of this interface is used to only to
 * generate the initial parent population at the very beginning of the algorithm. For all consecutive population production at later
 * generations, the `ChildPopulationProducer` interface is used.
 */
@FunctionalInterface
public interface PopulationProducer {
	/**
	 * This method generates a `Population`, which is a collection of `Chromosomes` equal to provided `populationSize`, each chromosome
	 * having a length equal to `chromosomeLength`. The chromosomes are created using the genetic code produced by the provided
	 * `geneticCodeProducer`. A `fitnessCalculator` is provided which may be used to carry out additional computation with each
	 * chromosome during the preparation of the population.
	 *
	 * @param populationSize the size of the population, i.e., the number of chromosomes to be created.
	 * @param chromosomeLength the length of each chromosome to be created, i.e., the length of the genetic code.
	 * @param geneticCodeProducer the `GeneticCodeProducer` object used to generate the genetic code of each chromosome.
	 * @param fitnessCalculator the `FitnessCalculator` object to carry out any additional computation on the chromosome.
	 * @return a `Population` with `populationSize` number of chromosomes.
	 */
	Population produce(int populationSize, int chromosomeLength, GeneticCodeProducer geneticCodeProducer, FitnessCalculator fitnessCalculator);
}
