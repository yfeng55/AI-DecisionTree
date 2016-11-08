package com.yf833;
import java.util.ArrayList;


public class Node {

    public ArrayList<Node> children;
    public String decisionstate;
    public float expected_val;

    public Node(String decisionstate){
        this.decisionstate = decisionstate;
        this.children = new ArrayList<>();
    }



}
