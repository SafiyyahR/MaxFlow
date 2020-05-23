/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file is the class from which an Edge object is made from.
*/
package com.company.models;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    //start node of an edge
    private int start;
    //weight of an edge
    private int weight;
    //end node of an edge
    private int end;

    //constructor of an edge
    public Edge(int start, int weight, int end) {
        //assigning the values passed to make a new edge
        this.start = start;
        this.weight = weight;
        this.end = end;
    }

    //second constructor, accepts an edge
    //this constructor is used to clone edges
    public Edge(Edge edge) {
        //using the get methods of the attributes of the edge passed,
        // the values returned by the get methods are assigned to the attributes of this (current) edge to clone the edge passed
        this.start = edge.getStart();
        this.weight = edge.getWeight();
        this.end = edge.getEnd();
    }

    //get methods
    public int getStart() {
        return start;
    }

    public int getWeight() {
        return weight;
    }

    public int getEnd() {
        return end;
    }

    //set methods
    public void setStart(int start) {
        this.start = start;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    //toString method - converts an object to string type
    @Override
    public String toString() {
        return "Start node " + this.getStart() + " is connected to node " +
                this.getEnd() + " by a weight of " + this.getWeight();
    }

    //used to check if an object is equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return getStart() == edge.getStart() &&
                getWeight() == edge.getWeight() &&
                getEnd() == edge.getEnd();
    }

    //used when checking the equality of two objects
    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getWeight(), getEnd());
    }

    //used to help sort objects. objects by default will be sorted using the endNode.
    @Override
    public int compareTo(Edge edge) {
        return this.getEnd() - edge.getEnd();
    }
}
