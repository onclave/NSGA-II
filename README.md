[![DOI](https://zenodo.org/badge/96868928.svg)](https://zenodo.org/badge/latestdoi/96868928)

# NSGA-II
**an NSGA-II implementation using Java**

**_Original Authors of the Paper_: [Kalyanmoy Deb](http://www.egr.msu.edu/~kdeb/), [Amrit Pratap](https://scholar.google.com/citations?user=E8wJ7G8AAAAJ&hl=en), [Sameer Agarwal](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.S.%20Agarwal.QT.&newsearch=true), [T. Meyarivan](http://ieeexplore.ieee.org/search/searchresult.jsp?searchWithin=%22Authors%22:.QT.T.%20Meyarivan.QT.&newsearch=true)**

_links to original contents:_

* [NSGA-II paper: PDF](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.542.385&rep=rep1&type=pdf)
* [NSGA-II code implementation by original authors at **KanGAL**](https://www.iitk.ac.in/kangal/codes.shtml)

_**note**: this implementation of NSGA-II algorithm is in pure reference to the original published paper. This is not an effort to convert the originally implemented C code in Java. The original C code by the authors has not be referred to while writing this implementation._

_**Dependency: Java( >= 1.8), JFreeChart(1.5.3), JCommon(1.0.24)**_

### Important

> version 3.1.0 brings a lot of added functionality, fixes a few bugs and brings a few breaking changes. While it is recommended to use the latest version (3.1.0) and update your dependencies for your exisiting projects, do keep in mind about the breaking
> changes and open an issue if you are unable to do so.

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

#### This package is now available on the maven central repository!

To use it as a maven dependency add the following to your `pom.xml` file:

```xml
    <dependencies>
        ...
        <dependency>
            <groupId>com.debacharya</groupId>
            <artifactId>nsgaii</artifactId>
            <version>3.2.0</version>
        </dependency>
    </dependencies>
```

**Please note that while you can use version 3.0.1, it requires Java 13 and above. 3.0.2 has been released to be able to work with Java 1.8 and above.**

### [Getting Started](https://github.com/onclave/NSGA-II/wiki/Getting-Started)

### Contributing

This project is open to pull requests and encourages new features through contribution. The contribution guidelines shall be updated shortly.

### Citation

> You can now cite this NSGA-II implementation if you want.
>
> *Cite this library*
>
> Debabrata Acharya. (2020, September 21). onclave/NSGA-II: an NSGA-II implementation using Java. Zenodo. http://doi.org/10.5281/zenodo.4042108
