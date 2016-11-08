package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
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

        Node root = buildTree();

        System.out.println();

    }


    public static Node buildTree(){

        //build root node:
        Node root = new Node("root");
        root.children.add(new Node("reject"));
        root.children.add(new Node("publish"));

        //create a set of reviewers
        HashSet<Reviewer> reviewer_set = new HashSet<>();
        for(Reviewer r : reviewers){
            reviewer_set.add(r);
        }

        //for each reviewer left in the set of reviewers, build a subtree
        for(Reviewer r : reviewer_set){

            HashSet<Reviewer> new_reviewerset = new HashSet<>(reviewer_set);
            new_reviewerset.remove(r);

            Node child = buildSubtree(new_reviewerset, new Node("R" + r.id));
            root.children.add(child);
        }


        return root;
    }


    public static Node buildSubtree(HashSet<Reviewer> reviewer_set, Node current){

        //base case:
        if(reviewer_set.isEmpty()){
            return current;
        }

        //recursive case:
        for(Reviewer r : reviewer_set){
            HashSet<Reviewer> new_reviewerset = new HashSet<>(reviewer_set);
            new_reviewerset.remove(r);


            Node child = buildSubtree(new_reviewerset, new Node("R" + r.id));
            current.children.add(child);
        }

        return current;

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

            Reviewer reviewer = new Reviewer(i, cost, Rt_St, Rt_Sf);
            reviewers[i] = reviewer;
            i++;
        }

    }


}






