package com.yf833;


public class Reviewer {

    public int id;
    public int cost;
    public double Rt_St; // P(R=T|S=T)
    public double Rt_Sf; // P(R=T|S=F)
    public double Rt;
    public double Rf;


    public Reviewer(int id, int cost, double Rt_St, double Rt_Sf, double S){
        this.id = id;
        this.cost = cost;
        this.Rt_St = Rt_St;
        this.Rt_Sf = Rt_Sf;

        this.Rt = this.Rt_St*S + this.Rt_Sf*(1-S);
        this.Rf = 1.0 - Rt;
    }


    public String toString(){
        String output = "";
        output += "cost: $" + this.cost + "\t";
        output += "P(R=T|S=T): " + this.Rt_St + "\t";
        output += "P(R=T|S=F): " + this.Rt_Sf + "\t";
        return output;
    }

}
