package com.yf833;

import java.util.ArrayList;

public class Util {

    // given a list of reviewers and their previous recommendations (true/false)
    // return the conditional probability that the book will succeed: P(S|R1,..Rn)
    public static double S_R(ArrayList<Reviewer> reviewers, double R_Rprev, double S, char sf){

        if(reviewers.size() <= 1){
            R_Rprev = 1.0;
        }


        double numerator=1;
        double denominator=1;

        //compute the numerator: P(R1|S) * P(R2|S) * .. P(Rn|S) * P(S)
        if(sf == 'S'){
            numerator = S;
            for(Reviewer r : reviewers){
                if(r.review == true){
                    numerator *= r.Rt_St;
                }else{
                    numerator *= (1 - r.Rt_St);
                }
            }
        }
        else{
            numerator = 1-S;
            for(Reviewer r : reviewers){
                if(r.review == true){
                    numerator *= r.Rt_Sf;
                }else{
                    numerator *= (1 - r.Rt_Sf);
                }
            }
        }

        //compute the denominator
        Reviewer firstreviewer = reviewers.get(0);
        if(firstreviewer.review == true){
            denominator = firstreviewer.Rt * R_Rprev;
        }else{
            denominator = firstreviewer.Rf * R_Rprev;
        }

        return numerator / denominator;
    }



    // given a list of reviewers and their previous reommendations (true/false)
    // return the conditional probability that a reviewer recommends true/false: P(Rn|R1,..Rn-1)
    public static double R_R(ArrayList<Reviewer> prevreviews, Reviewer currentreviewer, double R_Rprev, double S){

        // get the P(Rn|S,R1,..Rn-1) and P(Rn|F,R1,..Rn-1) probabilities
        double R_SandRi = currentreviewer.Rt_St;
        double R_FandRi = currentreviewer.Rt_Sf;

        // compute P(S|R1,R2,..Rn) and P(F|R1,R2,..Rn)
        double S_Ri = S_R(prevreviews, R_Rprev, S, 'S');
        double F_Ri = S_R(prevreviews, R_Rprev, S, 'F');

        return R_SandRi*S_Ri + R_FandRi*F_Ri;
    }


    // given P(A), P(B), P(B|A), return P(A|B)
    public static double a_b(double A, double B, double B_A){
        return (B_A * A)/B;
    }

    // return the expected value of 2 values and their corresponding probabilities
    public static double expectedVal(double a_prob, double a_val, double b_prob, double b_val){
        return (a_prob * a_val) + (b_prob * b_val);
    }



}



