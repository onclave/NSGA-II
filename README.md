# NSGA-II
**an NSGA-II implementation using Java**

**_Original Authors of the Paper_: [Kalyanmoy Deb](http://www.egr.msu.edu/~kdeb/), [Amrit Pratap](https://scholar.google.com/citations?user=E8wJ7G8AAAAJ&hl=en), [Sameer Agarwal](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.S.%20Agarwal.QT.&newsearch=true), [T. Meyarivan](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.T.%20Meyarivan.QT.&newsearch=true)**

_links to original contents:_

* [NSGA-II paper: PDF](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.542.385&rep=rep1&type=pdf)
* [NSGA-II code implementation by original authors at **KanGAL**](https://www.iitk.ac.in/kangal/codes.shtml)

_**note**: this implementation of NSGA-II algorithm is in pure reference to the original published paper. This is not an effort to convert the originally implemented C code in Java. The original C code by the authors has not be referred to while writing this implementation._

_**Dependency: Java( >= 13), JFreeChart(1.5.0), JCommon(1.0.24)**_

### Please Note:

This is **v3** of the algorithm implementation. This reference implementation has been updated to be:

* Used as package.
* Much more generic, customizable and hence more powerful.
* More efficient than the previous version.

The reference **v2** implementation can be found [here](https://github.com/onclave/NSGA-II/tree/master/v2). The _README_ of that implementation can be found [here](https://github.com/onclave/NSGA-II/blob/master/v2/README.md).

The reference **v1** implementation can be found [here](https://github.com/onclave/NSGA-II/tree/master/v1). The _README_ of that implementation can be found [here](https://github.com/onclave/NSGA-II/blob/master/v1/README.md).

**note:** Code commenting of **v3** is under progress and not all code is commented properly. This shall be done shortly. In the mean time, if you are unable to understand any part of the code, feel free to open an _issue_ about it and I shall try to
 resolve it.

### Documentation

This is a fully customizable implementation of the NSGA-II algorithm, made as generic as possible. This documentation assumes you have basic understanding of the NSGA-II algorithm. Apart from the core concepts of the algorithm, everything else in this
 package can be implemented as per the user's choice and plugged into the algorithm dynamically.
 
 By default, the package provides a default implementation of every plugin and hence the package can be run with just one line of code as a PoC.
 
 ```java
(new NSGA2()).run();
 ```

For more information, visit the [Wiki](https://github.com/onclave/NSGA-II/wiki)

For full documentation, visit the [Documentation Wiki](https://github.com/onclave/NSGA-II/wiki/Documentation).

### Using it in your project

This package shall be published to maven shortly. Till then you can use the source package directly in your project.

### [Getting Started](https://github.com/onclave/NSGA-II/wiki/Getting-Started)

### Contributing

This project is open to pull requests and encourages new features through contribution. The contribution guidelines shall be updated shortly.