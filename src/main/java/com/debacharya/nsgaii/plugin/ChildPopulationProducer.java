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

import com.debacharya.nsgaii.crossover.AbstractCrossover;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.mutation.AbstractMutation;

/**
 * The ChildPopulationProducer interface is used to produce a population of chromosomes for the algorithm to use.
 * It has only one method, `produce()` that produces a child population from a provided parent population. For every child
 * population, a fixed amount of chromosomes is created by executing the provided `crossover` and `mutation` operators on the parent
 * population. This interface is used to generate every child population at each generation. Note that the initial parent population
 * is generated using the `PopulationProducer` interface.
 */
@FunctionalInterface
public interface ChildPopulationProducer {
	/**
	 * This method generates a child `Population`, which is a collection of `Chromosomes` equal to provided `populationSize`, from the
	 * given `parentPopulation`. The child chromosomes are created by executing the provided `crossover` and `mutation` operators on
	 * the `parentPopulation`. A new `Population` is hence created.
	 * @param parentPopulation the population from which the child population is to be generated.
	 * @param crossover the crossover operator which is a concrete implementation of `AbstractCrossover`.
	 * @param mutation the mutation operator which is a concrete implementation of `AbstractMutation`.
	 * @param populationSize the size of the population, i.e., the number of child chromosomes to be created.
	 * @return a new child `Population`, with `populationSize` number of chromosomes.
	 */
	Population produce(Population parentPopulation, AbstractCrossover crossover, AbstractMutation mutation, int populationSize);
}
