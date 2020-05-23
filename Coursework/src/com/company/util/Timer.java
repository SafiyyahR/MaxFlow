/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file is used to calculate the time taken to execute functions.
*/
package com.company.util;


public class Timer {
    //start the timer
    private final long startTime = System.currentTimeMillis();

    //this method is used to return the time taken from the start time in nano seconds
    public double stopTimer() {
        long endTime = System.currentTimeMillis();
        return (double)(endTime - this.startTime)/1000.0D;
    }

}
