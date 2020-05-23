/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file is used to read the txt files and print the max flow of each graph stored in the file,
and print the time taken to calculate the time taken to execute the function for each graph
and finally calculate the big o of the algorithm.
*/
package com.company;

import com.company.models.WeightedGraph;
import com.company.util.Timer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MaxFlowFile {
    private static final double LOG_2 = Math.log(2);

    public static void findBigONotationOfAlgorithm(String filename1, String filename2, String filename3, String filename4) throws IOException {
        //start the stopwatch by making an object of the Stopwatch object from the stdlib.jar added
        Timer startTime = new Timer();
        //pass the filename to the maxFlowFile
        maxFlowFile(filename1);
        //display the time taken to find the max flow of the data in the file
        double file1Time = startTime.stopTimer();
        System.out.println("Time taken for file " + file1Time+"\n");
        //start the stopwatch by making an object of the Stopwatch object from the stdlib.jar added
        Timer startTime1 = new Timer();
        //pass the filename to the maxFlowFile
        maxFlowFile(filename2);
        //display the time taken to find the max flow of the data in the file
        double file2Time = startTime1.stopTimer();
        System.out.println("Time taken for file " + file2Time+"\n");
        //start the stopwatch by making an object of the Stopwatch object from the stdlib.jar added
        Timer startTime2 = new Timer();
        //pass the filename to the maxFlowFile
        maxFlowFile(filename3);
        //display the time taken to find the max flow of the data in the file
        double file3Time = startTime2.stopTimer();
        System.out.println("Time taken for file " + file3Time+"\n");
        //start the stopwatch by making an object of the Stopwatch object from the stdlib.jar added
        Timer startTime3 = new Timer();
        //pass the filename to the maxFlowFile
        maxFlowFile(filename4);
        //display the time taken to find the max flow of the data in the file
        double file4Time = startTime3.stopTimer();
        System.out.println("Time taken for file " + file4Time+"\n");
        System.out.println("Ratio calculated between the time taken to execute file 15000Nodes.txt and file 7500Nodes.txt (Ratio 1) - " + file2Time / file1Time);
        System.out.println("Ratio calculated between the time taken to execute file 30000Nodes.txt and file 15000Nodes.txt (Ratio 2) - " + file3Time / file2Time);
        System.out.println("Ratio calculated between the time taken to execute file 60000Nodes.txt and file 30000Nodes.txt (Ratio 3) - " + file4Time / file3Time+"\n");
        double logRatio1 = Math.log(file2Time / file1Time) / LOG_2;
        double logRatio2 = Math.log(file3Time / file2Time) / LOG_2;
        double logRatio3 = Math.log(file4Time / file3Time) / LOG_2;
        System.out.println("The log of ratio 1 - " + logRatio1 + ".\nThe log of ratio 2 - " + logRatio2 + ".\nThe log of ratio 3 - " + logRatio3 + ".\n");
        int avgLogRatio = Math.toIntExact(Math.round((logRatio1 + logRatio2 + logRatio3) / 3));
        System.out.println("The approximate Big O Notation of this algorithm is O(n^" + avgLogRatio + ").\n");
    }

    private static void maxFlowFile(String fileName) throws IOException {
        FileReader fr;
        BufferedReader br;
        try {
            System.out.println("For file " + fileName);
            //reads the data and converts to bytes of the file passed
            //"E:\\2nd Year\\Algorithms\\CWK1\\Coursework\\src\\"
            fr = new FileReader(fileName);
            //reads the data from the file reader and can read text line by line
            br = new BufferedReader(fr);
            //initialize the line variable which hold the data of a line of the file
            String line;
            //loops goes till the noOfNodes entered by the user is greater than or equal to 6.
            int noOfNodes;
            do {
                //the first line of every file is the number of nodes of the graph.
                noOfNodes = Integer.parseInt(br.readLine());
                //validating the input and printing a message to the user to enter a higher value.
                if (noOfNodes < 6) {
                    System.out.println("Number of nodes is less than 6.");
                }else if(noOfNodes>46000){
                    System.out.println("Cannot make graphs with number of nodes greater than 46000");
                }
            } while (noOfNodes < 6 || noOfNodes>46000);
            //make a weighted graph which has a number of nodes equal to noOfNodes.
            WeightedGraph weightedGraph = new WeightedGraph(noOfNodes);
            //the number of lines of the file after the second line is equal to the noOfEdges.
            int noOfEdges;

            int maxNoOfEdges = (noOfNodes * noOfNodes) -(3* noOfNodes)+3;
            //loops goes till the noOfEdges entered by the user is greater than or equal to 1.
            do {
                //the second line of every file is the number of nodes of the graph
                noOfEdges = Integer.parseInt(br.readLine());
                //validating the input and printing a message to the user to enter a higher value.
                if (noOfEdges < 1) {
                    System.out.println("Number of nodes is less than 1");
                } else if (noOfEdges > maxNoOfEdges) {
                    System.out.println("Cannot connect more than " + maxNoOfEdges + ".");
                }
            } while (noOfEdges < 1 || noOfEdges > maxNoOfEdges);
            //using a for loop a new edge is created and added to the weighted graph
            for (int i = 0; i < noOfEdges; i++) {
                //read a line from the file
                line = br.readLine();
                if (line != null) {
                    //there are 3 integers in each line which are separated by a space.
                    // Hence the can be captured by splitting by a space and assigning the result to an array of String type
                    String[] arr = line.split(" ");
                    //the first integer is the start node of the edge
                    int startNode = Integer.parseInt(arr[0]);
                    //the second integer is the weight of the edge
                    int weight = Integer.parseInt(arr[1]);
                    //the third integer is the end node of the edge
                    int endNode = Integer.parseInt(arr[2]);
                    //using a method of the weighted graph a new edge can be added to the weighted graph.
                    //if the edge that is attempted to add to the weighted graph is incorrect,
                    // then false is returned by the message and i is reduced by 1
                    if (weightedGraph.addWeightedEdge(startNode, weight, endNode)) {
                        i--;
                    }

                }
            }

            //once all the edge details have been read and the details have been added then the max flow of the weighted graph is found
            weightedGraph.findMaxFlow(false);
            br.close();
            fr.close();
            //if the details entered in the file are not integers
        } catch (NumberFormatException ex) {
            System.out.println("The data in the file are not integers");
            //if there are certain information missing from the file
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("The information provided is not sufficient.");
        }

    }
}
