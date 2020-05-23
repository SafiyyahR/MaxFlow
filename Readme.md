# Algorithm Coursework 1

Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020

## Description

This project finds the maximum flow of a directed graph using an implementation of the Ford-Fulkerson's algorithm. It utilizes Adjacency LinkedLists of the user-defined class Edge to store the directed graph. It uses breadth-first search to find the augmentation path of the flow map.

## Getting Started

These set of instructions will assist you to set up the project and execute the functions.

### Prerequisites

1. Download Java SE Development Kit 8 and set the environment variables
2. Download and setup a Java IDE preferably IntelliJ

## Usage

To start the program, the user needs to run the MaxFlowOfNetwork.java which will display the following menu.

```{
........Main Menu........

Enter
 (a)Find maximum flow from the source to the sink of a graph.
 (b)Find the Big O Notation of the algorithm used.
 (c)Exit.

```

### Find the maximum flow of a directed graph

1. When the user enters 'a', the **calculateMaxFlowOfGraph()** in the MaxFlowSolution.java file is invoked.

2. The user is then asked to enter the number of nodes. An object of the *WeightedGraph* class is created by passing the number of nodes the user enters to constructor of the object. The user then enters number of edges of the directed graph. The two inputs are validated to ensure that the details of the graph the user hopes to enter will be valid.

3. The user then enters the details of each edge of the graph which is stored in the **linkedAdjacencyList** of the weightedGraph object. After the details the **findMaxFlow** of the weightedGraph is invoked to calculate the maximum flow of the graph and the distributed flow of the graph.

#### Example

The input graph

![Input Graph with 6 nodes and 8 edges](https://github.com/SafiyyahR/MaxFlow/blob/master/assests/images/InputGraph.png)

The expected result graph

![Output Graph with 6 nodes and 8 edges](https://github.com/SafiyyahR/MaxFlow/blob/master/assests/images/MaxFlowGraph.png)


The beginning of the program

```{
........Main Menu........

Enter
 (a)Find maximum flow from the source to the sink of a graph.
 (b)Find the Big O Notation of the algorithm used.
 (c)Exit.
a
Enter the number of nodes in the weighted graph:
6
Enter the number of edges in the weighted graph:
8
Enter the start node of the edge 1 :
0
Enter the weight of the edge 1 :
10
Enter the end node of the edge 1 :
1
Enter the start node of the edge 2 :
0
Enter the weight of the edge 2 :
12
Enter the end node of the edge 2 :
2
Enter the start node of the edge 3 :
1
Enter the weight of the edge 3 :
5
Enter the end node of the edge 3 :
3
Enter the start node of the edge 4 :
2
Enter the weight of the edge 4 :
15
Enter the end node of the edge 4 :
4
Enter the start node of the edge 5 :
2
Enter the weight of the edge 5 :
5
Enter the end node of the edge 5 :
3
Enter the start node of the edge 6 :
1
Enter the weight of the edge 6 :
8
Enter the end node of the edge 6 :
4
Enter the start node of the edge 7 :
3
Enter the weight of the edge 7 :
13
Enter the end node of the edge 7 :
5
Enter the start node of the edge 8 :
4
Enter the weight of the edge 8 :
10
Enter the end node of the edge 8 :
5
The source - 0.
The sink - 5.
The maximum possible flow is 20.

........Sub Menu 1........
Enter
 (a)Display the input graph and result graph.
 (b)Display the breakdown of the max flow calculated.
 (c)To continue
a

The input graph
Start node 0 is connected to node 1 by a weight of 10
Start node 0 is connected to node 2 by a weight of 12
Start node 1 is connected to node 3 by a weight of 5
Start node 1 is connected to node 4 by a weight of 8
Start node 2 is connected to node 4 by a weight of 15
Start node 2 is connected to node 3 by a weight of 5
Start node 3 is connected to node 5 by a weight of 13
Start node 4 is connected to node 5 by a weight of 10

Max flow graph.
Start node 0 is connected to node 1 by a weight of 10
Start node 0 is connected to node 2 by a weight of 10
Start node 1 is connected to node 3 by a weight of 5
Start node 1 is connected to node 4 by a weight of 5
Start node 2 is connected to node 3 by a weight of 5
Start node 2 is connected to node 4 by a weight of 5
Start node 3 is connected to node 5 by a weight of 10
Start node 4 is connected to node 5 by a weight of 10

........Sub Menu 1........
Enter
 (a)Display the input graph and result graph.
 (b)Display the breakdown of the max flow calculated.
 (c)To continue
b

0 -> 1 -> 3 -> 5 = 5
0 -> 1 -> 4 -> 5 = 5
0 -> 2 -> 3 -> 5 = 5
0 -> 2 -> 4 -> 5 = 5

........Sub Menu 1........
Enter
 (a)Display the input graph and result graph.
 (b)Display the breakdown of the max flow calculated.
 (c)To continue
```
When the user clicks continue they are given the choice of editing the details by adding, deleting or updating the edges of the graph. The max flow is recalculated after the modification.

### Find the Big O Notation of the algorithm developed

1. When the user 'b', the **findBigONotationOfAlgorithm()** in the MaxFlowFile.java is invoked.

2. The four files in the folder are individually executed and the time taken to do so is recorded.

3. This time is then used to find the ratio between T(2n)/T(n) and in turn used to find the Big O Notation of the Algorithm.

#### Example

The expected Big O Notation for this algorithm is O(n^2) as the double nested loops used in the algorithm.

```{
    ........Main Menu........

Enter
 (a)Find maximum flow from the source to the sink of a graph.
 (b)Find the Big O Notation of the algorithm used.
 (c)Exit.
b
For file 5750Nodes.txt
The source - 0.
The sink - 5749.
The maximum possible flow is 100399.
Time taken for file 0.823

For file 11500Nodes.txt
The source - 0.
The sink - 11499.
The maximum possible flow is 212589.
Time taken for file 1.489

For file 23000Nodes.txt
The source - 0.
The sink - 22999.
The maximum possible flow is 433323.
Time taken for file 5.555

For file 46000Nodes.txt
The source - 0.
The sink - 45999.
The maximum possible flow is 844228.
Time taken for file 28.025
Ratio calculated between the time taken to execute file 15000Nodes.txt and file 7500Nodes.txt (Ratio 1) - 1.8092345078979346
Ratio calculated between the time taken to execute file 30000Nodes.txt and file 15000Nodes.txt (Ratio 2) - 3.7306917394224306
Ratio calculated between the time taken to execute file 60000Nodes.txt and file 30000Nodes.txt (Ratio 3) - 5.0450045004500454
The log of ratio 1 - 0.8553794181604745.
The log of ratio 2 - 1.8994431576943545.
The log of ratio 3 - 2.3348555563036975.
The approximate Big O Notation of this algorithm is O(n^2).
```
