package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static java.lang.Integer.*;

public class Main3 {

    public static void main(String[] args) throws IOException {
        int m; //numero de personas cuya fecha de nacimiento está en el intervalo
        int p; //numero de nombres distintos de las m personas tratadas (tamaño del APL)
        String continuar;

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Nombre del fichero:");
            String nomfich;
            while (true) {
                nomfich = (scanner.nextLine());
                if (nomfich.equals("p.txt")) break;
                System.out.println("No existe ningún fichero con ese nombre.");
            }

            String ruta = "./archivos/" + nomfich;
            Persona[] dat = leeFichero(ruta);

            System.out.println("*** OPCIONES GENERALES ***");
            System.out.println("*** Número de nombres a mostrar: ***");
            int k = parseInt(scanner.nextLine());
            k--;
            System.out.println("*** Nombre del usuario: ***");
            String nombre = scanner.nextLine().toUpperCase();

            System.out.println("*** BUCLE DE CONSULTAS ***");
            System.out.println("Introduce un numero:");
            int numero = parseInt(scanner.nextLine());

            long ti0,ti1;
            ti0 = System.nanoTime();

            String[] filt = filtrado(dat);

            Map <String, Nombre> Persona = insercion(filt);
            p = Persona.size();




            Queue <Nombre> Ranking = extraccion(k,Persona,numero);

            Nombre[] Rank= new Nombre[k];
            /*for(int i=k-1;i>=0;i--){ //Guardamos en un array los elementos de la cola de mayor a menos
                Rank[i] = Ranking.poll();
            }*/

            ti1 = System.nanoTime() - ti0;
            for(int i=0;i<k;i++){ //Imprimimos el array de menor a mayor
                Rank[i] = Ranking.poll();
                System.out.println(i+1+"."+Rank[i].nombre +" " +Rank[i].numero );
            }

            if (Persona.containsKey(nombre)){ //Esto corresponde al seguimiento
                Nombre n = Persona.get(nombre);
                System.out.println(k+1+"."+nombre+ " " + n.numero);
            }

            System.out.println("*** MEDIDAS ***");
            System.out.printf("Tiempo de proceso: %.6f seg.\n", 1e-9 * ti1);
            System.out.println("p = " +p+ " nombres distintos.");
            System.out.println("¿Continuar? [S/N]");
            continuar = scanner.nextLine().toUpperCase();
        } while (continuar.equals("S"));
    }

    public static Persona[] leeFichero(String nomfich) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(nomfich)), 131072);
        int n = parseInt(br.readLine());
        System.out.println("_|____|____|____|____|");
        long t0, t1, t2;
        t0 = System.nanoTime();
        Persona[] dat = new Persona[n];
        for (int i = 0; i < n; i++) {
            dat[i] = new Persona();
        }
        t1 = System.nanoTime() - t0;
        System.out.print("*");
        int lim = n / 20;
        t0 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            dat[i].parse(br.readLine(), br.readLine());
            if (i % lim == lim - 1) {
                System.out.print("*");
            }
        }
        t2 = System.nanoTime() - t0;
        System.out.printf("\nCreacion array: %.5f seg. Lectura fichero: %.5f seg.\n", 1e-9 * t1, 1e-9 * t2);
        System.out.printf("n = %d personas en total.\n\n", dat.length);
        return dat;
    }

    public static String[] filtrado(Persona[] dat) {
        int n= dat.length;
        String[] filt = new String[n];
        int m=0;
        for (Persona persona : dat) { //recorrer array
            filt[m] = persona.nom;
            m++;
        }
        String[] filt2 = new String[m]; //copiamos el string a uno de longitud m
        System.arraycopy(filt, 0, filt2, 0, m);
        filt = filt2;
        return (filt);
    }

    public static Map<String, Nombre> insercion(String[] filt) {
        Map<String,Nombre> Persona = new HashMap<>();
        for (String s : filt) { //recorrer el vector de filtrado
            boolean contains = Persona.containsKey(s);
            if (contains) { //si esta en el set incrementamos el contador
                Nombre num = Persona.get(s);
                num.numero++;
                Persona.replace(s, num);
            } else { //si no está Y SU NUMERO ES MAYOR LO AÑADIMOS
                Nombre N = new Nombre(s, 1);
                Persona.put(s, N);
            }
        }
        return Persona;
    }

    public static Queue <Nombre> extraccion(int k,Map<String,Nombre> Persona,int numero) {
        Queue <Nombre> Ranking = new PriorityQueue<>(k);
        for(String key : Persona.keySet()){
            Nombre A = Persona.get(key);
            int size = Ranking.size();
            if(size < k){
                Ranking.add(A);
            }
            else{
                Nombre B = Ranking.peek();
                if((A.numero<B.numero)&&(A.numero<numero)){
                    Ranking.poll();
                    Ranking.add(A);
                }
            }
        }
        return Ranking;
    }

}//del Main