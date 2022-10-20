package com.company;

public class Persona {
    int nac;
    int fac;
    int gen;
    String nom;

    public void parse(String lin1, String lin2) {
        nac = (lin1.charAt(0)-48)*10000+
                (lin1.charAt(1)-48)*1000+
                (lin1.charAt(2)-48)*100+
                (lin1.charAt(3)-48)*10+
                (lin1.charAt(4)-48);
        fac = (lin1.charAt(6)-48)*10000+
                (lin1.charAt(7)-48)*1000+
                (lin1.charAt(8)-48)*100+
                (lin1.charAt(9)-48)*10+
                (lin1.charAt(10)-48);
        gen = lin1.charAt(12)-48;
        nom = lin2;
    }
}
