package com.yf833;
import java.util.ArrayList;


public class Node {

    public ArrayList<Node> children;
    public String decisionstate;
    public String type;
    public boolean prev_recommendation;

    public ArrayList<Reviewer> reviewers_used;
    public double probability;



    public Node(String decisionstate, String type, boolean prev_recommendation, double probability){
        this.decisionstate = decisionstate;
        this.type = type;
        this.prev_recommendation = prev_recommendation;
        this.probability = probability;

        this.children = new ArrayList<>();
        this.reviewers_used = new ArrayList<>();
    }

    public Node(String decisionstate, String type, boolean prev_recommendation, double probability, ArrayList<Reviewer> reviewers_used){
        this.decisionstate = decisionstate;
        this.type = type;
        this.prev_recommendation = prev_recommendation;
        this.probability = probability;

        this.children = new ArrayList<>();
        this.reviewers_used = reviewers_used;
    }




    public String toString(){
        String output = "";
        output += this.decisionstate;

        return output;
    }

}
