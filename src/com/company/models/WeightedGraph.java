/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file is the class from which a weighted graph object is made from and contains the method used to calculate the max flow of the graph.
*/
package com.company.models;

import java.util.*;

public class WeightedGraph {
    //stores the number of nodes the weighted graph
    private int nodes;
    //stores the initial graph entered by the user, this list changes
    private LinkedList<Edge>[] linkedAdjacencyList;
    //stores the original graph entered by the user, this doesnt unless the user deletes an edge
    private LinkedList<Edge>[] masterLinkedAdjacencyList;
    //stores the residual graph
    private LinkedList<Edge>[] residualLinkedList;
    //stores the result graph which has the max flow
    private LinkedList<Edge>[] resultLinkedList;
    //stores the breakdown of the max flow calculated;
    private List<List<Integer>> breakDownOfMaxFlow;
    //the start node of any graph is 0 hence it is declared as a constant
    private static final int START_NODE = 0;
    //to indicate an edge is incorrect instead of returning the edge again after the search this variable is sent (similar to null/false) - this a kind of rogue edge.
    private static final Edge FALSE_EDGE = new Edge(0, 0, 0);

    //the constructor accepts the no of nodes as an argument
    public WeightedGraph(int nodes) {
        //using the set method the no of nodes are set.
        setNodes(nodes);
        //all 4 arrays linked lists have a length equal to the number of nodes
        this.residualLinkedList = new LinkedList[nodes];
        this.linkedAdjacencyList = new LinkedList[nodes];
        this.resultLinkedList = new LinkedList[nodes];
        this.masterLinkedAdjacencyList = new LinkedList[nodes];
        //initialize every element of the four arrays by a LinkedList object
        for (int index = 0; index < this.nodes; index++) {
            this.linkedAdjacencyList[index] = new LinkedList<>();
            this.residualLinkedList[index] = new LinkedList<>();
            this.resultLinkedList[index] = new LinkedList<>();
            this.masterLinkedAdjacencyList[index] = new LinkedList<>();
        }
    }

    //used to initialize the linkedList arrays so they can be cloned
    private void initialize(LinkedList<Edge>[] linkedList) {
        for (int index = 0; index < this.nodes; index++) {
            linkedList[index] = new LinkedList<>();

        }
    }

    //to check if an edge is valid
    private Edge isEdgeValid(int start, int weight, int end, LinkedList<Edge>[] linkedList) {
        //end and start can't be the same value change it
        //the start node is checked if it is greater than or equal to zero but less than number of nodes.
        if ((start < this.nodes - 1) && start >= 0) {
            //the end node is checked if it is greater than 0 and less than the number of nodes.
            if ((end < this.nodes) && end > 0 && end != start) {
                //then the weight is checked if it is greater than zero
                if (weight > 0) {
                    //if all these conditions are met then is cross-checked if the edge exists in the linkedAdjacencyList using the findEdge method
                    Edge foundEdge = findEdge(start, end, linkedList);
                    //if the edge is equal to false_edge then the edge is added to the graph. If false_edge is returned then it means the edge doesn't exist.
                    if (foundEdge.equals(FALSE_EDGE)) {
                        //making an object of an edge
                        return new Edge(start, weight, end);
                    } else {
                        //this prints to user that an edge with this start and end node exists
                        System.out.println("There is a connection between node " + start + " and " + end + " created before.");
                    }
                } else {
                    //this prints to the user that the weight of an edge has to greater than zero
                    System.out.println("Weight of an edge must be greater than zero.");
                }
                //prints specific error messages
            } else if (end == 0) {
                System.out.println("Node 0 cannot be an end node.");
            } else if (start == end) {
                System.out.println("Start node and end node cannot be the same.");
            } else {
                System.out.println("Node " + end + " is not in the graph.");
            }
            //prints specific error messages
        } else if (start == this.nodes - 1) {
            System.out.println("Node " + (this.nodes - 1) + " cannot be a start node.");
        } else {
            System.out.println("Node " + start + " is not in the graph.");
        }
        //the method returns false if the user has entered anything incorrectly.
        return FALSE_EDGE;
    }

    //the method is used to add an edge to the graph
    public boolean addWeightedEdge(int start, int weight, int end) {
        Edge edge = isEdgeValid(start, weight, end, this.linkedAdjacencyList);
        //if FALSE_EDGE has been returned then true is sent to indicate that the edge has not been added
        if (edge.equals(FALSE_EDGE)) {
            return true;
        } else {
            //else the edge is added
            this.linkedAdjacencyList[start].addLast(edge);
            return false;
        }
    }

    //cloning the first parameter - the array of linkedLists of edge type.
    private void setLinkedList(LinkedList<Edge>[] linkedList, LinkedList<Edge>[] settingLinkedList) {
        for (LinkedList<Edge> list : settingLinkedList) {
            for (Edge edge : list) {
                linkedList[edge.getStart()].addLast(new Edge(edge));
            }
        }
    }

    //print the weighted graph, by printing each edge
    public void printWeightedGraph(LinkedList<Edge>[] linkedList) {
        for (LinkedList<Edge> list : linkedList) {
            for (Edge edge : list) {
                System.out.println(edge);
            }
        }
    }

    public LinkedList<Edge>[] getLinkedAdjacencyList() {
        return linkedAdjacencyList;
    }

    //get method for the number of nodes
    public int getNodes() {
        return nodes;
    }

    //the set method for the number of nodes
    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    //modified bfs to find the path faster
    private boolean findPathFromSourceToSink(int[] pathIndex) {
        if (this.residualLinkedList[0].size() != 0) {
            //stores true or false if the node in the queue has been visited or not
            boolean[] hasVisited = new boolean[this.nodes];
            Queue<Integer> queue = new LinkedList<>();
            //the source is the first node to be added to the queue for traversing
            queue.add(START_NODE);
            //once the node has been added to the queue then the element of the hasVisited array which has the index equal to the start node will be updated to true
            hasVisited[START_NODE] = true;
            //this array is used to store the path from which a flow from the source to the sink is being identified
            //the first element is always a rogue value - this indicates that there is no parent node for this
            pathIndex[START_NODE] = -1;
            boolean visitedFlag = false;
            //this loop runs till all elements in the queue have been visited
            while (queue.size() != 0 && !visitedFlag) {
                //takes the first element from the queue
                int headOfQueue = queue.poll();
                //some elements in the residualLinkedList might not have any edges, the if condition is used to validate that
                if (this.residualLinkedList[headOfQueue].size() != 0) {
                    //sorting the linkedList stored in residualLinkedList[headOfQueue] according to the end node
                    Collections.sort(this.residualLinkedList[headOfQueue]);
                    //go through each edge in the linkedList
                    for (Edge edge : this.residualLinkedList[headOfQueue]) {
                        //if the endNode is not visited and if the weight of the edge is greater
                        // than zero then it is added to the graph and the headOfQueue is added to the pathIndex, to show the path.
                        if (!hasVisited[edge.getEnd()] && edge.getWeight() > 0) {
                            queue.add(edge.getEnd());
                            hasVisited[edge.getEnd()] = true;
                            pathIndex[edge.getEnd()] = headOfQueue;
                            //found a path from the source to the sink then break
                            if (edge.getEnd() == this.nodes - 1) {
                                visitedFlag = true;
                                break;
                            }
                        }
                    }
                }
            }
            //return the value stored in the element of the hasVisited array which has an index equal to the source node's
            return hasVisited[this.nodes - 1];
        } else {
            return false;
        }
    }


    //function used to find the max flow of the graph
    public String findMaxFlow(boolean afterDelUpdFlag) {
        //stores the breakDown path and the values
        this.breakDownOfMaxFlow = new ArrayList<>(this.nodes);
        //if the user is not updating or deleting an edge from then the linkedAdjacencyList is cloned to  masterLinkedAdjacencyList
        if (!afterDelUpdFlag) {
            this.setLinkedList(this.masterLinkedAdjacencyList, this.linkedAdjacencyList);
        } else {

            initialize(this.residualLinkedList);
            initialize(this.resultLinkedList);
        }

        //clone the linkedAdjacencyList to residualLinkedList
        this.setLinkedList(this.residualLinkedList, this.linkedAdjacencyList);
        //make an array to store the path found in the breadthFirstSearch which shows a path from the source to the sink
        int[] pathIndex = new int[this.nodes];
        //initially the max flow is zero
        int max_flow = 0;
        //declaring the start and end variable
        int start, end;
        //if the value returned by the breadthFirstSearch() iw
        while (this.findPathFromSourceToSink(pathIndex)) {
            List<Integer> individualBreakDown = new ArrayList<>();
            //if true was returned then the minimum weight value of the path found must be identified
            int path_flow = Integer.MAX_VALUE;
            //finding the minimum value from the path identified
            for (end = this.nodes - 1; end > START_NODE; end = pathIndex[end]) {
                //the value stored in the pathIndex[end] is the start of the edge between start and end
                start = pathIndex[end];
                //once the edge is found the weight is extracted and compared with the path_flow to find the minimum value
                Edge foundEdge = findEdge(start, end, this.residualLinkedList);
                int weight = foundEdge.getWeight();
                path_flow = Math.min(path_flow, weight);
                individualBreakDown.add(end);
            }
            individualBreakDown.add(end);
            individualBreakDown.add(path_flow);
            this.breakDownOfMaxFlow.add(individualBreakDown);
            //the path_flow is subtracted from the weight of the edges in the path identified from the breadthFirstSearch
            for (end = this.nodes - 1; end > START_NODE; end = pathIndex[end]) {
                //the value stored in the pathIndex[end] is the start of the edge between start and end
                start = pathIndex[end];
                //once the edge is found then the weight is subtracted by the path_flow
                Edge foundEdge = findEdge(start, end, this.residualLinkedList);
                foundEdge.setWeight(foundEdge.getWeight() - path_flow);
            }
            //the path_flow is add to the max_flow
            max_flow += path_flow;
        }
        if (max_flow != 0) {
            //the result graph is found which gives the max flow
            findLinkedListResult();
            System.out.println("The source - " + 0 + ".");
            System.out.println("The sink - " + (this.getNodes() - 1) + ".");
            System.out.println("The maximum possible flow is " + max_flow + ".");
            return "yes";
        } else {
            System.out.println("Cannot find maximum possible flow as there is no connection between the sink and source.");
        }
        return "no";

    }

    //method used to find if the edge exists in the array of linkedList passed
    private Edge findEdge(int start, int end, LinkedList<Edge>[] linkedList) {
        //it is checked if the start node is greater than 0
        if (start >= 0) {
            for (Edge edge : linkedList[start]) {
                if (edge.getEnd() == end) {
                    return edge;
                }
            }
        }
        //if the edge doesnt exist then FALSE_EDGE
        return FALSE_EDGE;
    }


    //this method is used to delete an edge from the weighted graph
    public String deleteEdge(int start, int end) {
        String isSuccessful = "ab";
        //first it is checked if the edge is in the weighted graph
        Edge foundEdge = this.findEdge(start, end, this.masterLinkedAdjacencyList);
        //if the edge is found then it is removed from the masterLinkedList and then the masterLinkedList is cloned to LinkedAdjacencyList.
        //then the new max flow is calculated
        if (!foundEdge.equals(FALSE_EDGE)) {
            this.masterLinkedAdjacencyList[foundEdge.getStart()].remove(foundEdge);
            initialize(this.linkedAdjacencyList);
            setLinkedList(this.linkedAdjacencyList, this.masterLinkedAdjacencyList);
            isSuccessful = this.findMaxFlow(true);
            //if the edge is not found then the print statement is executed.
        } else {
            System.out.println("Edge to be deleted is not in the graph.");
        }
        return isSuccessful;
    }

    //this method is used to update the weight of an edge from the weighted graph
    public String updateWeightOfEdge(int start, int end, int weight) {
        String isSuccessful = "ab";
        //first it is checked if the edge is in the weighted graph
        Edge foundEdge = this.findEdge(start, end, this.masterLinkedAdjacencyList);
        //if the edge is found then it is removed from the masterLinkedList then the weight of the foundEdge is set.
        // The edge is appended to the masterLinkedAdjacencyList.
        // The masterLinkedList is cloned to LinkedAdjacencyList.
        //then the new max flow is calculated
        if (!foundEdge.equals(FALSE_EDGE)) {
            if (weight >= 1) {
                this.masterLinkedAdjacencyList[foundEdge.getStart()].remove(foundEdge);
                foundEdge.setWeight(weight);
                this.masterLinkedAdjacencyList[foundEdge.getStart()].addLast(foundEdge);
                //initialize the LinkedAdjacencyList before cloning
                initialize(this.linkedAdjacencyList);
                setLinkedList(this.linkedAdjacencyList, this.masterLinkedAdjacencyList);
                isSuccessful = this.findMaxFlow(true);
            } else {
                //this prints to the user that the weight of an edge has to greater than zero
                System.out.println("Weight of an edge must be greater than zero.");
            }
        } else {
            //if the edge is not found then the print statement is executed.
            System.out.println("Edge to be updated is not in the graph.");
        }
        return isSuccessful;
    }

    //this method is used to update the weight of an edge from the weighted graph
    public String addNewEdge(int start, int end, int weight) {
        String isSuccessful = "ab";
        //check if the edge is valid
        Edge edge = isEdgeValid(start, weight, end, this.masterLinkedAdjacencyList);
        //if the edge is not FALSE_EDGE then it is added to the masterLinkedAdjacencyList
        if (!edge.equals(FALSE_EDGE)) {
            this.masterLinkedAdjacencyList[start].addLast(edge);
            //initialize the LinkedAdjacencyList before cloning
            initialize(this.linkedAdjacencyList);
            setLinkedList(this.linkedAdjacencyList, this.masterLinkedAdjacencyList);
            //find maxFlow
            isSuccessful = this.findMaxFlow(true);
        }
        return isSuccessful;
    }


    //used to find the resultLinkedList
    private void findLinkedListResult() {
        //cloning the linkedAdjacencyList to resultLinkedList
        setLinkedList(this.resultLinkedList, this.linkedAdjacencyList);
        for (int i = 0; i < this.linkedAdjacencyList.length; i++) {
            //sort the linkedAdjacencyList, residualLinkedList, resultLinkedList
            Collections.sort(this.linkedAdjacencyList[i]);
            Collections.sort(this.residualLinkedList[i]);
            Collections.sort(this.resultLinkedList[i]);
            //for every element in the linkedList
            for (int j = 0; j < resultLinkedList[i].size(); j++) {
                int k;
                //another loop is used to identify the corresponding edge from the residualLinkedList
                //loop 2
                for (k = 0; k < this.residualLinkedList[i].size(); k++) {
                    //the end nodes must match
                    if (this.resultLinkedList[i].get(j).getEnd() == this.residualLinkedList[i].get(k).getEnd()) {
                        //once found it breaks from the loop 2
                        break;
                    }
                }
                //the minimum and the maximum weight between the edge of the resultLinkedList and the residualLinkedList is found
                int min = Math.min(this.resultLinkedList[i].get(j).getWeight(), this.residualLinkedList[i].get(k).getWeight());
                int max = Math.max(this.resultLinkedList[i].get(j).getWeight(), this.residualLinkedList[i].get(k).getWeight());
                //if the max and min values are the same or if the weight of the edge from the residualLinkedList is greater than the resultLinkedList then the edge is removed from all the LinkedLists
                if ((max - min) == 0 || (this.resultLinkedList[i].get(j).getWeight() < this.residualLinkedList[i].get(k).getWeight())) {
                    this.resultLinkedList[i].remove(this.resultLinkedList[i].get(j));
                    this.residualLinkedList[i].remove(this.residualLinkedList[i].get(k));
                    this.linkedAdjacencyList[i].remove(this.linkedAdjacencyList[i].get(j));
                    //j is decreased as the loop has to go to the last element
                    j--;
                    // else if the weight of the edge from the residualLinkedList is not equal to zero then the max-min will give the result weight
                } else if (this.residualLinkedList[i].get(k).getWeight() != 0) {
                    this.resultLinkedList[i].get(j).setWeight(max - min);
                }
                //if it is zero the value is the same in the resultLinkedList
            }
        }
    }

    //displays how the max flow was added, step by step
    public void displayBreakDownOfMaxFlowCalc() {
        for (List<Integer> outerList : this.breakDownOfMaxFlow) {
            //find the index of 0 aka the source
            int indexOfSource = outerList.indexOf(0);
            //loop till i is 0 where 0 is the index of the arrayList which has the sink
            for (int i = indexOfSource; i >= 0; i--) {
                System.out.print(outerList.get(i));
                if (i == 0) {
                    System.out.print(" = ");
                } else {
                    System.out.print(" -> ");
                }
            }
            //print the path flow
            System.out.print(outerList.get(outerList.size() - 1) + "\n");
        }
    }

    //getters for the LinkedList<Edge>[] of the weighted graph
    public LinkedList<Edge>[] getMasterLinkedAdjacencyList() {
        return masterLinkedAdjacencyList;
    }

    public LinkedList<Edge>[] getResidualLinkedList() {
        return residualLinkedList;
    }

    public LinkedList<Edge>[] getResultLinkedList() {
        return resultLinkedList;
    }

}
