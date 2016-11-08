package com.yf833;
import java.util.ArrayList;


public class Node {

    public ArrayList<Node> children;
    public String decisionstate;
    public String type;
    public boolean prev_recommendation;
    public float expected_val;

    public Node(String decisionstate, String type, boolean prev_recommendation){
        this.decisionstate = decisionstate;
        this.children = new ArrayList<>();
        this.type = type;
        this.prev_recommendation = prev_recommendation;
    }

    public String toString(){
        String output = "";
        output += this.decisionstate;

        return output;
    }

}
