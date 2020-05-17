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

package com.debacharya.nsgaii.datastructure;

import java.util.List;

public class Population {

	private final List<Chromosome> populace;

	public Population(final List<Chromosome> populace) {
		this.populace = populace;
	}

	public List<Chromosome> getPopulace() {
		return populace;
	}

	public int size() {
		return this.populace.size();
	}

	public Chromosome get(int index) {
		return this.populace.get(index);
	}

	public Chromosome getLast() {
		return this.populace.get(this.populace.size() - 1);
	}

	public double selectMaximumNormalizedObjectiveValue(int objectiveIndex) {

		double result = this.populace.get(0).getNormalizedObjectiveValues().get(objectiveIndex);

		for(Chromosome chromosome : this.populace)
			if(chromosome.getNormalizedObjectiveValues().get(objectiveIndex) > result)
				result = chromosome.getNormalizedObjectiveValues().get(objectiveIndex);

		return result;
	}

	public double selectMinimumNormalizedObjectiveValue(int objectiveIndex) {

		double result = this.populace.get(0).getNormalizedObjectiveValues().get(objectiveIndex);

		for(Chromosome chromosome : this.populace)
			if(chromosome.getNormalizedObjectiveValues().get(objectiveIndex) < result)
				result = chromosome.getNormalizedObjectiveValues().get(objectiveIndex);

		return result;
	}

	@Override
	public String toString() {

		StringBuilder response = new StringBuilder();

		for(Chromosome chromosome : this.populace)
			response.append(chromosome.toString()).append("\n");

		return response.toString();
	}
}
