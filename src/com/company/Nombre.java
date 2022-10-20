package com.company;

public class Nombre implements Comparable<Nombre>{
    String nombre;
    int numero;
    public Nombre(String nombre,int numero){
        this.nombre=nombre;
        this.numero=numero;
    }
    public int getNumero(){
        return numero;
    }
    public int compareTo(Nombre nombre) {
        if (this.numero == nombre.getNumero()) {
            return 0;
        } else if (this.numero < nombre.getNumero()) {
            return 1;
        } else {
            return -1;
        }
    }
}
