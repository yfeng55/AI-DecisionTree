package com.yf833;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static int num_reviewers;
    public static int success_amount;
    public static int failure_amount;
    public static float S;
    public static float F;
    public static Reviewer[] reviewers;


    public static void main(String[] args) throws FileNotFoundException {

        getInput(args[0]);
        System.out.println();

    }


    public static void getInput(String inputpath) throws FileNotFoundException {

        Scanner s = new Scanner(new File(inputpath));

        //process first line
        String[] firstline = s.nextLine().split(" +");
        num_reviewers = Integer.parseInt(firstline[0]);
        success_amount = Integer.parseInt(firstline[1]);
        failure_amount = Integer.parseInt(firstline[2]);
        S = Float.parseFloat(firstline[3]);
        F = 1 - S;

        reviewers = new Reviewer[num_reviewers];

        //add reviewers to the reviewers list
        int i=0;
        while(s.hasNextLine()){
            String[] reviewerline = s.nextLine().split(" +");

            int cost = Integer.parseInt(reviewerline[0]);
            float Rt_St = Float.parseFloat(reviewerline[1]);
            float Rt_Sf = Float.parseFloat(reviewerline[2]);

            Reviewer reviewer = new Reviewer(cost, Rt_St, Rt_Sf);
            reviewers[i] = reviewer;
            i++;
        }

    }


}






