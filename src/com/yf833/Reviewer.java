package com.yf833;


public class Reviewer {

    public int id;
    public int cost;
    public double Rt_St; // P(R=T|S=T)
    public double Rt_Sf; // P(R=T|S=F)


    public Reviewer(int id, int cost, double Rt_St, double Rt_Sf){
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


    // compute P(R=T)
    public double getRt(double S){
        return this.Rt_St*S + this.Rt_Sf*(1-S);
    }
    // compute P(R=F)
    public double getRf(double S){
        return 1 - (this.Rt_St*S + this.Rt_Sf*(1-S));
    }

}
