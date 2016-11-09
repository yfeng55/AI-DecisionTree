package com.yf833;


import java.util.HashMap;

public class Util {

    // given P(A), P(B), P(B|A), return P(A|B)
    public static double a_b(double A, double B, double B_A){
        return (B_A * A)/B;
    }


    // given a hashmap of reviewers and their previous reommendations (true/false)
    // return the conditional probability that a reviewer recommends true/false
    public static double R_R(HashMap<Reviewer, Boolean> prevreviews, Reviewer currentreviewer, double S){

        double F = 1 - S;
        double result = 0.0;


        //iterate through all reviewers in the HashMap
        for(Reviewer prevreviewer : prevreviews.keySet()){

            //compute P(R | S,Ri) for each past reviewer and multiply it by P(S | Ri)
            double R_SandRi = prevreviewer.Rt_St;
            double S_Ri = a_b(S, prevreviewer.Rt, prevreviewer.Rt_St);

            //compute P(R | F,Ri) for each past reviewer and multiply it by P(F | Ri)
            double R_FandRi = prevreviewer.Rt_Sf;
            double F_Ri = a_b(F, prevreviewer.Rt, prevreviewer.Rt_Sf);

            result += (R_SandRi * S_Ri + R_FandRi * F_Ri);
        }


        return result;
    }


}
