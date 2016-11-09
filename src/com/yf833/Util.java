package com.yf833;


public class Util {

    // given P(A), P(B), P(B|A), return P(A|B)
    public static double a_b(double A, double B, double B_A){
        return (B_A * A)/B;
    }





}
