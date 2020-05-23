/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file is used to allow the user to make their own graph and find the max flow.
Allows them to also modify the graph after finding the max flow.
*/
package com.company;

import com.company.models.WeightedGraph;
import com.company.util.Validator;


public class MaxFlowSolution {
    public static void calculateMaxFlowOfGraph() {
        //initializing the noOfNodes variable with a rogue value.
        int noOfNodes = -1;
        //loops goes till the noOfNodes entered by the user is greater than or equal to 6.
        while (noOfNodes < 6 || noOfNodes > 46000) {
            //getting the input
            noOfNodes = Validator.validateInteger("Enter the number of nodes in the weighted graph: ");
            //validating the input and printing a message to the user to enter a higher value.
            if (noOfNodes < 6) {
                System.out.println("Number of nodes is less than 6.");
                //validating the input and printing a message to the user to enter a lower value.
            } else if (noOfNodes > 46000) {
                System.out.println("Cannot make graphs with number of nodes greater than 46000");
            }
        }
        //next the noOfEdges variable is initialized to a rogue value as well
        int noOfEdges = -1;
        //maxNoOfEdges is product of 2 noOfNodes and then subtracting the length of one diagonal of the matrix,
        // then subtracting noOfNodes - 1 as the start node, 0 cannot be an end node and
        // finally subtracting noOfNodes - 2 as the last node of the graph cannot be a start node of an edge.
        int maxNoOfEdges = (noOfNodes * noOfNodes) - (3 * noOfNodes) + 3;
        //loops goes till the noOfEdges entered by the user is greater than or equal to 1.
        while (noOfEdges < 1 || noOfEdges > maxNoOfEdges) {
            //getting the input
            noOfEdges = Validator.validateInteger("Enter the number of edges in the weighted graph: ");
            //validating the input and printing a message to the user to enter a higher value.
            if (noOfEdges < 1) {
                System.out.println("Number of edges is less than 1.");
                //validating the input and printing a message to the user to enter a lower value.
            } else if (noOfEdges > maxNoOfEdges) {
                System.out.println("Cannot connect more than " + maxNoOfEdges + " edges.");
            }
        }
        //once they are validated a weighted graph object which has the number of nodes equal to the noOfNodes
        WeightedGraph weightedGraph = new WeightedGraph(noOfNodes);
        //the loop runs till all the edge details have been entered
        for (int i = 1; i <= noOfEdges; i++) {
            //ask the user to enter the start node of an edge
            int start = Validator.validateInteger("Enter the start node of the edge " + i + " : ");
            //ask the user to enter the weight of an edge
            int weight = Validator.validateInteger("Enter the weight of the edge " + i + " : ");
            //ask the user to enter the end node of an edge
            int end = Validator.validateInteger("Enter the end node of the edge " + i + " : ");
            //using a method of the weighted graph a new edge can be added to the weighted graph.
            //if the edge that is attempted to add to the weighted graph is incorrect,
            // then false is returned by the message and i is reduced by 1
            if (weightedGraph.addWeightedEdge(start, weight, end)) {
                i--;
            }
        }
        //once all the edge details have been read and the details have been added then the max flow of the weighted graph is found
        String foundMaxFlow = weightedGraph.findMaxFlow(false);
        if (foundMaxFlow.equalsIgnoreCase("yes")) {
            displayDetailsOfWeightedGraph(weightedGraph);
        }
        String option;
        String isSuccessful;
        do {
            System.out.println("........Sub Menu 2........");
            option = Validator.validateConsoleOption("Enter\n (a)To delete an edge.\n (b)To update an edge.\n (c)To add an edge.\n (d)To exit.", 4);
            //if true then the user is asked to enter the start and end nodes of the edge
            if (option.equalsIgnoreCase("a")) {
                int start = Validator.validateInteger("Enter the start node of the edge : ");
                int end = Validator.validateInteger("Enter the end node of the edge : ");
                //the deleteEdge method is called to delete the edge using the start and end nodes entered.
                isSuccessful = weightedGraph.deleteEdge(start, end);
                if (isSuccessful.equalsIgnoreCase("yes")) {
                    displayDetailsOfWeightedGraph(weightedGraph);
                }
                //if true then the user is asked to enter the start and end nodes of the edge with the new weight
            } else if (option.equalsIgnoreCase("b")) {
                int start = Validator.validateInteger("Enter the start node of the edge : ");
                int end = Validator.validateInteger("Enter the end node of the edge : ");
                //the new weight of the edge is entered
                int weight = Validator.validateInteger("Enter the new weight of the edge : ");
                //these integers are passed to update the specific edge
                isSuccessful = weightedGraph.updateWeightOfEdge(start, end, weight);
                if (isSuccessful.equalsIgnoreCase("yes")) {
                    displayDetailsOfWeightedGraph(weightedGraph);
                }
                //if true then the user is asked to enter the start and end nodes of the new edge along with the weight
            } else if (option.equalsIgnoreCase("c")) {
                //ask the user to enter the start node of an edge
                int start = Validator.validateInteger("Enter the start node of the edge " + " : ");
                //ask the user to enter the weight of an edge
                int weight = Validator.validateInteger("Enter the weight of the edge " + " : ");
                //ask the user to enter the end node of an edge
                int end = Validator.validateInteger("Enter the end node of the edge " + " : ");
                //using a method of the weighted graph a new edge can be added to the weighted graph.
                //if the edge that is attempted to add to the weighted graph is incorrect,
                // then false is returned by the message and i is reduced by 1
                isSuccessful = weightedGraph.addNewEdge(start, end, weight);
                if (isSuccessful.equalsIgnoreCase("yes")) {
                    displayDetailsOfWeightedGraph(weightedGraph);
                }
            }
        } while (!option.equals("d"));
        System.out.print("\n");
    }


    private static void displayDetailsOfWeightedGraph(WeightedGraph weightedGraph) {
        String option;
        do {
            System.out.println("\n........Sub Menu 1........");
            option = Validator.validateConsoleOption("Enter\n (a)Display the input graph and result graph.\n (b)Display the breakdown of the max flow calculated.\n (c)To continue", 3);
            if (option.equalsIgnoreCase("a")) {
                //displaying the original graph and the resultLinkedList
                System.out.println("\nThe input graph");
                weightedGraph.printWeightedGraph(weightedGraph.getMasterLinkedAdjacencyList());
                System.out.println("\nMax flow graph.");
                weightedGraph.printWeightedGraph(weightedGraph.getResultLinkedList());
                //displays the breakdown of the max flow and the paths used to achieve it
            } else if (option.equalsIgnoreCase("b")) {
                System.out.println("\n");
                weightedGraph.displayBreakDownOfMaxFlowCalc();
            }
        } while (!option.equals("c"));
        System.out.print("\n");
    }
}
