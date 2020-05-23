/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file contains the psvm method used to access the other methods that
can show the doubling hypothesis and the big O() notation calculation
Also the user can enter a graph and see the max flow,by modifying the graph multiple times.
*/
package com.company;

import com.company.util.Validator;

import java.io.IOException;

public class MaxFlowOfNetwork {
    public static void main(String[] args) {
        String option;
        do {
            System.out.println("........Main Menu........\n");
            option = Validator.validateConsoleOption("Enter\n (a)Find maximum flow from the source to the sink of a graph.\n (b)Find the Big O Notation of the algorithm used.\n (c)Exit.",3);
            if (option.equalsIgnoreCase("a")) {
                MaxFlowSolution.calculateMaxFlowOfGraph();
            } else if (option.equalsIgnoreCase("b")) {
                try {
                    MaxFlowFile.findBigONotationOfAlgorithm("5750Nodes.txt","11500Nodes.txt","23000Nodes.txt","46000Nodes.txt");
                } catch (IOException e) {
                    System.out.println("Couldn't find the file.");
                }
            }

        } while (!option.equals("c"));

        System.out.println("********************* Thank You **************************");
    }
}
