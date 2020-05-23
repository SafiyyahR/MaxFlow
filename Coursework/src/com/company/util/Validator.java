/*
Name: Safiyyah Thur Rahman
UoW ID: W1714855
IIT ID: 2018025
Course: BEng. Software Engineering
Submission Date:30/03/2020
Coursework 01 for Algorithms
This file contains static methods which are used to get user's input and validate them.
*/
package com.company.util;

import java.util.Arrays;
import java.util.Scanner;

public class Validator {
    //making a scanner object to get the user's input.
    private static Scanner input = new Scanner(System.in);

    //I have used this method before
    //method used to get inputs from the user by prompting the message string passed a parameter and validate it to check whether it is of integer type
    public static int validateInteger(String message) {
        System.out.println(message);
        //this loop will run until the user enters an integer an error message will be displayed to prompt the user that the data wasn't an integer
        while (!input.hasNextInt()) {
            //prints the message in red
            System.err.println("Data entered is not Integer");
            System.out.println(message);
            input.next();
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    //method used to get inputs from the user by prompting the message string passed a parameter and validate it to check whether the option entered is within the option array
    public static String validateConsoleOption(String message, int noOfOptions) {
        String[] optionArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        if (noOfOptions > optionArray.length) {
            System.err.println("Cannot have a menu with that many choices.");
            return "error";
        } else if (noOfOptions < 1) {
            System.err.println("Cannot have a menu with less that one choice.");
            return "error";
        } else {
            String[] chosenOptionArray = Arrays.copyOfRange(optionArray, 0, noOfOptions);
            String option = "";
            boolean inputFlag = false;
            while (!inputFlag) {
                System.out.println(message);
                //this loop will run until the user enters a boolean an error message will be displayed to prompt the user that the data wasn't an boolean
                option = input.nextLine();
                //an error message is printed stating the option entered was incorrect
                for (String optionAvailable : chosenOptionArray) {
                    if (option.equalsIgnoreCase(optionAvailable)) {
                        inputFlag = true;
                        break;
                    }
                }
                if (!inputFlag) {
                    System.err.println("Option entered is not in " + Arrays.toString(chosenOptionArray));
                }
            }
            //returns true is yes is entered and false if no is entered
            return option;
        }
    }
}




