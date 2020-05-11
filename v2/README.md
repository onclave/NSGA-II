# NSGA-II
**an NSGA-II implementation using Java**

**_Original Authors of the Paper_: [Kalyanmoy Deb](http://www.egr.msu.edu/~kdeb/), [Amrit Pratap](https://scholar.google.com/citations?user=E8wJ7G8AAAAJ&hl=en), [Sameer Agarwal](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.S.%20Agarwal.QT.&newsearch=true), [T. Meyarivan](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.T.%20Meyarivan.QT.&newsearch=true)**

_links to original contents:_

* [NSGA-II paper: PDF](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.542.385&rep=rep1&type=pdf)
* [NSGA-II code implementation by original authors at **KanGAL**](https://www.iitk.ac.in/kangal/codes.shtml)

_**note**: this implementation of NSGA-II algorithm is in pure reference to the original published paper. This is not an effort to convert the originally implemented C code in Java. The original C code by the authors has not be referred to while writing this implementation._

_**Dependency: Java(>=1.8), JFreeChart(1.5.0), JCommon(1.0.24)**_

### Please Note:

This is **v2** of the algorithm implementation. This reference implementation has been updated to be:

* Much simpler than the previous version.
* More efficient than the previous version.
* Resolves an issue with the Binary Tournament Selection procedure.
* Provides a simple interactive console while running the algorithm.
* Updated dependencies.

The reference **v1** implemention can be found [here](https://github.com/onclave/NSGA-II/tree/master/v1). The _README_ of that implementation can be found [here](https://github.com/onclave/NSGA-II/blob/master/v1/README.md).

**note:** Code commenting of **v2** is under progress and not all code is commented properly. This shall be done shortly. In the mean time, if you are unable to understand any part of the code, feel free to open an _issue_ about it and I shall try to resolve it.

### How It Works

This code has 5 packages:

* _io.onclave.nsga.ii.interfaces_ : This package has the **IObjectiveFunction** interface that must be implemented by any Objective Function class in package _io.onclave.nsga.ii.objectivefunction_
* _io.onclave.nsga.ii.algorithm_ : This package contains the main algorithm class, **Algorithm.java** that must be run during execution.
* _io.onclave.nsga.ii.api_ : This package contains
    * the **Configuration.java** class which prepares and configures various modules before the algorithm is executed.
    * the **GraphPlot.java** class which currently supports 2D graph plotting using [JFreeChart](http://www.jfree.org/jfreechart/) library,
    * the **NSGAII.java** class which has all the NSGA-II specific methods and procedures such as **NSGAII#fastNonDominatedSort** and **NSGAII#crowdingDistanceAssignment** among others. The implementation of this class is completely new in contrast to the [v1](https://github.com/onclave/NSGA-II/tree/master/v1) reference implementation of this algoithm,
    * the **Reporter.java** that is used to print to console various states of the dataset and other information during runtime,
    * the **Service.java** that contains most of the essential functions required to run NSGA-II. Much of the implementation of this class has changed since v1, and
    * **Synthesis.java** that is essential for genetic algorithm specific functions.
* _io.onclave.nsga.ii.datastructure_ : This package contains the datastructures required for NSGA-II. An Object Oriented approached has been used.
    * **Allele.java**
    * **Chromosome.java** - This class has a new implementation from v1.
    * **Population.java**
* _io.onclave.nsga.ii.objectivefunction_ : contains various objective function specific classes that can be used with NSGA-II. All objective function classes must implement **IObjectiveFunction**

This implementation of the algorithm works in accordance to the original algorithm proposed in the paper.

### Procedure

* The algorithm can be run by executing **Algorithm.java**. This class just shows how the actual NSGA-II algorithm works. This class is not absolutely necessary to run this algorithm and can be used as just an example to study how the actual implementation works.
* For the initial generation, a population of size **_N_** = **Configuration#POPULATION_SIZE** is created using **Synthesis.syntesizePopulation** after which the NSGA-II specific procedures of fast non-dominated sort and crowding distance assignment are called on this initial population at **NSGAII#preparePopulation**.
    * Within **NSGAII#preparePopulation**, the **NSGAII#fastNonDominatedSort**  and **NSGAII#crowdingDistanceAssignment** are called on the population followed by **Service#randomizedQuickSortForRank** to sort them based on their rank.
* This creates the first parent population.
* The first child population is created from the parent population using **Synthesis#synthesizeChild**.
* Then, for each consecutive generation,
    * The parent and child population are combined to create a combined population of size **_2N_** using **Synthesis#createCombinedPopulation**.
    * The **NSGAII#fastNonDominatedSort**  and **NSGAII#crowdingDistanceAssignment** are called on the combined population via **NSGAII#preparePopulation** followed by **Service#randomizedQuickSortForRank**.
    * the child population is obtained from the combined population from **NSGAII#getChildFromCombinedPopulation** based on their _crowding distance_. This becomes the new parent population.
    * The new child population is then synthesized from this parent population using **Synthesis#synthesizeChild**.

### Configurations

To change various general configurations within the code, please refer to **Configuration** class and change the required values.

* **POPULATION_SIZE** : population size at each generation. Default is 100.
* **GENERATIONS** : number of generations the algorithm should run. Default is 25.
* **CHROMOSOME_LENGTH** : chromosome length _(n)_ in bits. Default is 20. Please note that, changing this to a very large value may throw a _NumberFormatException_ since the _n_ bit binary string is directly converted to a corresponding double value **to calculate the fitness value of each chromosome**. A relatively large binary string may throw exceptions during conversions. For example, _n = 8_ bit binary string keeps the value within 0 to 255. **This may not be a problem if you decide to change how you might want to calculate your fitness value for each chromosome**. By default, the fitness value is calculated for each chromosome as soon as a new chromosome is synthesized within the **Chromosome#Chromosome(Allele[])** constructor. Change this to use your own implementation.
* **CROSSOVER_PROBABILITY** : Default is 0.7.
* **MUTATION_PROBABILITY** : Default is 0.03.
* The binary strings generated as genetic code for each chromosome is converted to the corresponding double value which is the fitness of the chromosomes. This value is then normalized using min-max normalisation between 0 to 2. Change the variables **ACTUAL_MIN**, **ACTUAL_MAX**, **NORMALIZED_MIN**, **NORMALIZED_MAX** for other values.
* To change the default way the fitness value is calculated for each chromosome, implement **Service#calculateFitness** method according to requirement. This method would be automatically called during creation of every new instance of a chromosome. To change this behaviour, tweak the **Chromosome(Allele[])** constructor within the **Chromosome** class.
* The **Configuration#buildObjectives** method is called at the very beginning of the algorithm during call to **Configuration#configure**. This method should configure the **Configuration#objectives** property before the algorithm can execute anything. All the objectives must be configured and added to this list at the very beginning. Refer to **Configuration#buildObjectives** method for basic implementation.
* The x-axis and y-axis titles are retrieved from the objective functions by default using the **IObjectiveFunction#objectiveFunctionTitle** method. This can be changed to provide other values in the interactive console during runtime.
* The interactive console behaviour can be tweaked and changed according to need in the **Configuration#configure** method.

To change various synthesis configurations within the code, please refer to **Synthesis** class and change the required methods.

* a crowded binary tournament selection procedure is used to select a chromosome from parent to child at **Synthesis#crowdedBinaryTournamentSelection**
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