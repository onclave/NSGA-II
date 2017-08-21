# NSGA-II
**an NSGA-II implementation using Java**

**_Original Authors of the Paper_: [Kalyanmoy Deb](http://www.egr.msu.edu/~kdeb/), [Amrit Pratap](https://scholar.google.com/citations?user=E8wJ7G8AAAAJ&hl=en), [Sameer Agarwal](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.S.%20Agarwal.QT.&newsearch=true), [T. Meyarivan](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.T.%20Meyarivan.QT.&newsearch=true)**

_links to original contents:_

* [NSGA-II paper: PDF](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.542.385&rep=rep1&type=pdf)
* [NSGA-II code implementation by original authors at **KanGAL**](https://www.iitk.ac.in/kangal/codes.shtml)

_**note**: this implementation of NSGA-II algorithm is in pure reference to the original published paper. This is not an effort to convert the originally implemented C code in Java. The original C code by the authors has not be referred to while writing this implementation._

_**Dependency: Java(>=1.8), JFreeChart(1.0.13), JCommon(1.0.24)**_

### How It Works

This code has 6 packages:

* _io.onclave.nsga.ii.Interface_ : This package has the **IObjectiveFunction** interface that must be implemented by any Objective Function class in package _io.onclave.nsga.ii.objectivefunction_
* _io.onclave.nsga.ii.algorithm_ : This package contains the main algorithm class, **Algorithm.java** that must be run during execution.
* _io.onclave.nsga.ii.api_ : This package contains
    * **GraphPlot.java** which currently supports 2D graph plotting using [JFreeChart](http://www.jfree.org/jfreechart/) library,
    * the **Reporter.java** that is used to print to console various states of the dataset during runtime,
    * the **Service.java** that contains most of the essential functions specific to NSGA-II and
    * **Synthesis.java** that is essential for genetic algorithm specific functions.
* _io.onclave.nsga.ii.configuration_ : This package contains the **Configuration** class which prepares and configures various modules before the algorithm is executed.
* _io.onclave.nsga.ii.datastructure_ : This package contains the datastructures required for NSGA-II. An Object Oriented approached has been used.
    * **Allele.java**
    * **Chromosome.java**
    * **ParetoObject.java**
    * **Population.java**
* _io.onclave.nsga.ii.objectivefunction_ : contains various objective function specific classes that can be used with NSGA-II. All objective function classes must implement **IObjectiveInterface**

This implementation of the algorithm works in accordance to the original algorithm proposed in the paper.

Right now, the algorithm does not implement the method **Service#nonDominatedPopulationSort** but calls the method in the actual algorithm, so that if required, this method may be implemented as required.

The sorting algorithm used during **crowding distance assignment** in **Service#crowdingDistanceAssignment** is **randomized quick sort** implemented in **Service#RandomizedQuickSort**. This gives the optimal average case time complexity. If this is required to be changed, change the code at **Service#sort** to call your own sorting algorithm as required.

### Configurations

To change various general configurations within the code, please refer to **Configuration** class and change the required values.

* **POPULATION_SIZE** : population size at each generation.
* **GENERATIONS** : number of generations the algorithm should run.
* **CHROMOSOME_LENGTH** : chromosome length in bits. Default is 8. Please note that, changing this value may throw a _NumberFormatException_ since the 8 bit binary string is directly converted to a corresponding double value. A relatively large binary string may throw exceptions during conversions. The 8 bit binary string keeps the value within 0 to 255.
* **CROSSOVER_PROBABILITY**
* **MUTATION_PROBABILITY**
* The binary strings generated as genetic code for each chromosome is converted to the corresponding double value which is the fitness of the chromosomes. The default fitness value is always between 0 and 255 since the default chromosome length is 8. This value is then normalized using min-max normalisation between 0 to 2. Change the variables **ACTUAL_MIN**, **ACTUAL_MAX**, **NORMALIZED_MIN**, **NORMALIZED_MAX** for other values.
* To change the default way the fitness value is calculated for each chromosome, implement **Service#calculateFitness** method according to requirement. This method would be automatically called during call on **Chromosome#setGeneticCode** for every chromosome. To change this behaviour, tweak the **Chromosome#setGeneticCode** method.
* The **Configuration#buildObjectives** method is called at the very beginning of the algorithm. This method should configure the **Configuration#objectives** property before the algorithm can execute anything. All the objectives must be configured and added to this list at the very beginning. Refer to **Configuration#buildObjectives** method for basic implementation.

To change various synthesis configurations within the code, please refer to **Synthesis** class and change the required methods.

* a binary tournament selection procedure is used to select a chromosome from parent to child at **Synthesis#binaryTournamentSelection**
* a uniform crossover is used where the chromosomes are broken from the middle. To change this behavious, tweak **Synthesis#crossover**
* a bit flip is used for mutation
* **Synthesis#synthesizePopulation** method is used to generate a new population
* **Synthesis#synthesizeChild** method generates a new child population from parent population
* **Synthesis#synthesizeGeneticCode** method generates the genetic code of each chromosome

To change various plotting configurations within the code, please refer to **GraphPlot** class and change the required values.

* **GRAPH_TITLE**
* **KEY**
* **DIMENSION_X** : x-axis dimension of generated graph
* **DIMENSION_Y** : y-axis dimension of generated graph
* **COLOR**
* **STROKE_THICKNESS**

To set these values using setters, call the setter methods prior to calling the **GraphPlot#configurePlotter**

The **Reporter#render2DGraph** works only if number of objectives is 2. It uses the **Configuration#getObjectives** method to check this condition. Implement your own renderer method to render various other graphs.