# QPME

![QPME Logo](https://se.informatik.uni-wuerzburg.de/fileadmin/_migrated/RTE/RTEmagicC_QPME_Logo2_02.jpg.jpg)

## Overview

QPME (Queueing Petri net Modeling Environment) is an open-source tool for stochastic modeling and analysis based on the [Queueing Petri Net (QPN)](http://ls4-www.cs.tu-dortmund.de/QPN/) modeling formalism. The development of the tool started in 2003 at the Technische Universität Darmstadt and was continued at the University of Cambridge from 2006 to 2008 and at the Karlsruhe Institute of Technology (KIT) from 2008 to 2014. Since 2014, QPME is developed and maintained by the [Chair of Software Engineering](https://se.informatik.uni-wuerzburg.de/home/) at the University of Würzburg. The first version was released in January 2007 and since then the tool has been distributed to more than 120 organizations worldwide (universities, companies and research institutes). Since May 2011, QPME is distributed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html).

## Description

QPME is made of two components: QPE (QPN Editor) and SimQPN (Simulator for QPNs). QPE provides a user-friendly graphical tool for modeling using QPNs based on the [Eclipse/GEF](http://www.eclipse.org/gef/) framework. SimQPN provides an efficient discrete-event simulation engine for QPNs that makes it possible to analyze models of realistically-sized systems. QPME runs on a wide range of platforms including Windows, Linux and Mac OS among others.

The modeling approach implemented in QPME provides the following advantages:

- QPN models combine the modeling power and expressiveness of queueing networks and generalized stochastic Petri nets.
- QPN models allow the integration of hardware and software aspects of system behavior and lend themselves very well to modeling distributed systems.
- The support for hierarchical structures allows to build multi-layered models of software architectures similar to the way this is done in layered queueing networks, however, with the advantage that QPNs enjoy all the benefits of Petri nets for modeling synchronization aspects.
- The knowledge of the structure and behavior of QPNs can be exploited for fast and efficient analysis using simulation. This makes it possible to analyze models of large and complex systems ensuring that the approach scales to realistic systems.
- Many efficient qualitative analysis techniques from Petri net theory can be extended to QPNs and used to combine qualitative and quantitative system analysis.
- Last but not least, QPN models have an intuitive graphical representation that facilitates model development.

## Features

### Model Analysis Methods

- Discrete Event Simulation (SimQPN)
  - Method of non-overlapping batch means
  - Method of independent replications (replication/deletion approach)
  - Method of Welch for estimating the length of the initial transient
- Support for simulating hierarchical QPNs (HQPNs)

### Reported Metrics

- Token throughput (arrival and departure rates)
- Minimum/maximum number of tokens
- Average number of tokens
- Token color occupancy
- Queue utilization
- Minimum/maximum observed token residence time
- Mean and standard deviation of observed token residence times
- Confidence interval for the steady state mean token residence time
- Custom probes for observing token residence times across multiple places
- Histogram of observed token residence times
- Interface to the statistical tool R for further statistical analysis

### Results Visualization

- Tabular data
- Bar charts
- Pie charts
- Histograms

### Supported Queueing Disciplines

- First-Come-First-Served (FCFS)
- Processor-Sharing (PS)
- Infinite Server (IS)

### Supported Service-time Distributions for Queues

- Beta
- BreitWigner
- BreitWignerMeanSquare
- ChiSquare
- Gamma
- Hyperbolic
- Exponential
- ExponentialPower
- Logarithmic
- Normal
- StudentT
- Uniform
- VonMises
- Empirical
- Deterministic

### Supported Extensions to Standard QPNs

- Global QPN-wide list of token colors
- Distinction of firing weights at the transition level from the firing mode level
- First-In-First-Out (FIFO) token departure discipline for places
- Possibility to share queues among multiple queueing places

## Screenshots

![Main Window](https://se.informatik.uni-wuerzburg.de/fileadmin/_processed_/3/8/csm_QPE-Main_01_a85e597798.png)
