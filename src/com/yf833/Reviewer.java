package com.yf833;


public class Reviewer {

    public int id;
    public int cost;
    public float Rt_St; // P(R=T|S=T)
    public float Rt_Sf; // P(R=T|S=F)


    public Reviewer(int id, int cost, float Rt_St, float Rt_Sf){
        this.id = id;
        this.cost = cost;
        this.Rt_St = Rt_St;
        this.Rt_Sf = Rt_Sf;
    }


    public String toString(){
        String output = "";
        output += "cost: $" + this.cost + "\t";
        output += "P(R=T|S=T): " + this.Rt_St + "\t";
        output += "P(R=T|S=F): " + this.Rt_Sf + "\t";
        return output;
    }


}
