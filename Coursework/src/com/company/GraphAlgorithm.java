/*
This file is used to randomly generate
a flow network after the user gives the number of edges and
number of nodes and the maximum weight an edge can have.
After the graph is made 3 more graphs are made by doubling
the number edges and the doubling the number of nodes.
Each graph is stored in a txt file.
*/

package com.company;

import com.company.models.Edge;
import com.company.models.WeightedGraph;
import com.company.util.Validator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GraphAlgorithm {
    public static void main(String[] args) throws IOException {
        //ask the user to enter the number of nodes of the graph
        int noOfNodes=-1;
        while (noOfNodes < 6 || noOfNodes>5770) {
            //getting the input
            noOfNodes = Validator.validateInteger("Enter the number of nodes in the weighted graph: ");
            //validating the input and printing a message to the user to enter a higher value.
            if (noOfNodes < 6) {
                System.out.println("Number of nodes is less than 6.");
                //5770*8 = 46160 max nodes and then the max number of edges we can generate is 2130745600 which is less than the Integer.MAX_VALUE
            }else if(noOfNodes>5770){
                System.out.println("Cannot make graphs with number of nodes greater than 46000");
            }
        }
        int noOfEdges = -1;

        int maxNoOfEdges = (noOfNodes * noOfNodes) -(3* noOfNodes)+3;
        //loops goes till the noOfEdges entered by the user is greater than or equal to 1.
        while (noOfEdges < noOfNodes || noOfEdges>maxNoOfEdges) {
            //getting the input
            noOfEdges = Validator.validateInteger("Enter the number of edges in the weighted graph: ");
            //validating the input and printing a message to the user to enter a higher value.
            if (noOfEdges < noOfNodes) {
                System.out.println("Number of edges is less than "+noOfNodes+".");
            }else if(noOfEdges>maxNoOfEdges){
                System.out.println("Cannot connect more than "+maxNoOfEdges+" edges.");
            }
        }
        //ask the user to enter the max weight of all edges
        int maxWeight = Validator.validateInteger("Enter the max weight for all edges: ");
        //the loop runs 4 times to make txt files for the doubling hypothesis
        int maxNoOfNodes = 8 * noOfNodes;
        //creating an object of random
        Random rand = new Random();
        //double i everytime
        for (int i = noOfNodes; i <= maxNoOfNodes; i += i) {
            //the number of edges allotted for the source and sink nodes depending on the number of edges entered
            int remainingEdges;
            if(i<=8){
                remainingEdges=4;
            }else if (noOfEdges<=16){
                remainingEdges=6;
            }else if (noOfEdges<=100){
                remainingEdges=8;
            }else if (noOfEdges<=1000){
                remainingEdges=noOfEdges/8;
            }else if(noOfEdges<=11500){
                remainingEdges=noOfEdges/16;
            }else{
                remainingEdges=noOfEdges/32;
            }

            //make a graph of i nodes
            WeightedGraph graph = new WeightedGraph(i);
            //loop runs till all the edges have been made
            for (int j = 0; j < noOfEdges - remainingEdges; j++) {
                //For example if 6 nodes then start 1-4
                //maxWeight given is 10 - then the max is 10 and the min is 1
                //end 1-4
                //the start node of an edge - max is the i-2 and min is 1
                int start = rand.nextInt(i - 2) + 1;
                // the weight of an edge - max is the maxWeight and min is 1
                int weight = rand.nextInt(maxWeight) + 1;
                //the end node of an edge - max is the node-2 and min is 1
                int end = rand.nextInt(i - 2) + 1;
                //using a method of the weighted graph a new edge can be added to the weighted graph.
                //if the edge that is attempted to add to the weighted graph is incorrect,
                // then false is returned by the message and i is reduced by 1
                if (graph.addWeightedEdge(start, weight, end)) {
                    j--;
                }
            }
            //enter edges for the source node
            int remainingEdgesForBegin = remainingEdges / 2;
            for (int j = 0; j < remainingEdgesForBegin; j++) {
                //ask the user to enter the start node of an edge
                int start = 0;
                //ask the user to enter the weight of an edge
                int weight = rand.nextInt(maxWeight) + 1;
                //ask the user to enter the end node of an edge
                //For example 6 nodes then 1-4
                int end = rand.nextInt(i - 2) + 1;
                //using a method of the weighted graph a new edge can be added to the weighted graph.
                //if the edge that is attempted to add to the weighted graph is incorrect,
                // then false is returned by the message and i is reduced by 1
                if (graph.addWeightedEdge(start, weight, end)) {
                    j--;
                }
            }
            //enter edges for the sink nodes
            remainingEdges -= remainingEdgesForBegin;
            for (int j = 0; j < remainingEdges; j++) {
                //ask the user to enter the start node of an edge
                int start = rand.nextInt(i - 2) + 1;
                //ask the user to enter the weight of an edge
                int weight = rand.nextInt(maxWeight) + 1;
                //ask the user to enter the end node of an edge
                int end = i - 1;
                //using a method of the weighted graph a new edge can be added to the weighted graph.
                //if the edge that is attempted to add to the weighted graph is incorrect,
                // then false is returned by the message and i is reduced by 1
                if (graph.addWeightedEdge(start, weight, end)) {
                    j--;
                }
            }
            //writing to a file the name depends on the number of nodes
            FileWriter fileWriter = new FileWriter(i + "Nodes.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //write the number of nodes
            bufferedWriter.write(i + "\n");
            //write the number of edges
            bufferedWriter.write(noOfEdges + "\n");
            //write each edge in the order start node - weight - end node
            for (LinkedList<Edge> list : graph.getLinkedAdjacencyList()) {
                for (Edge edge : list) {
                    bufferedWriter.write(edge.getStart() + " " + edge.getWeight() + " " + edge.getEnd() + "\n");
                }
            }
            bufferedWriter.close();
            fileWriter.close();
            //doubling the edge
            noOfEdges += noOfEdges;
        }
    }
}


