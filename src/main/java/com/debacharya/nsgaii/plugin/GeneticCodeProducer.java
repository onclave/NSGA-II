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

import com.debacharya.nsgaii.datastructure.AbstractAllele;

import java.util.List;

/**
 * The GeneticCodeProducer interface is used to create a genetic code, which in turn is used to encode a Chromosome
 * for the algorithm to use. It has only one method, `produce`, which takes as its argument, the length of the genetic
 * code to be produced.
 */
@FunctionalInterface
public interface GeneticCodeProducer {
	/**
	 * This method is used to create a list of `AbstractAllele`s which represents the genetic code of a `Chromosome` and has
	 * a size equal to the `length` argument passed as parameter.
	 *
	 * @param length the length of the list of `AbstractAllele`s to be created. This should be the length of the genetic code.
	 * @return a list of `AbstractAlleles` whose size should be equal to the length argument.
	 */
	List<? extends AbstractAllele> produce(int length);
}
