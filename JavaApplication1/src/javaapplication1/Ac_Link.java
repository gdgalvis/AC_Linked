/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author German Galvis
 */
public class Ac_Link {

    /**
     * @param args the command line arguments
     */
    public static long[] times = new long[5];
    public static int[] comps = new int[5];
    public static int cc = 0;

    public static void main(String[] args) {
        int tamaño = (int) Math.pow(2, 5);
        Punto[] x = new Punto[tamaño];
        //El siguiente loop repite el proceso 5 veces para sacr el promedio
        for (int i = 0; i < 5; i++) {
            //Este for se asegura de que no hay coordenas repetidas que generen distancia minima de 0
            for (int j = 0; j < tamaño; j++) {
                int cx = (int) (Math.random() * (101));
                int cy = (int) (Math.random() * (101));
                boolean dup = false;
                for (int z = 0; z < j; z++) {
                    Punto test = x[z];
                    int testx = test.getx();
                    int testy = test.gety();
                    if ((testx == cx) && (testy == cy)) {
                        dup = true;
                        break;
                    }
                }
                if (dup == true) {
                    while (dup == true) {
                        int desdup = (int) (Math.random() * (101));
                        int desdup1 = (int) (Math.random() * (10) + 1);
                        cx = cx - (desdup * desdup1);
                        cy = cy + (desdup * desdup1);
                        boolean dup1 = false;
                        for (int z = 0; z < j; z++) {
                            Punto test = x[z];
                            int testx = test.getx();
                            int testy = test.gety();
                            if ((testx == cx) && (testy == cy)) {
                                dup1 = true;
                                break;
                            }
                        }
                        if (dup1 == false) {
                            dup = false;
                        }
                    }
                    x[j] = new Punto(cx, cy);
                } else {
                    x[j] = new Punto(cx, cy);
                }
            }
            cc = 0;
            //Se toma el tiempo de inicio
            long start = System.nanoTime();
            float[] ans = cercano(x, tamaño);
            long end = System.nanoTime();
            //Se toma el tiempo de final y se calcula el tiempo
            long time = end - start;
            times[i] = time;
            comps[i] = cc;
            int x1 = (int) ans[1];
            int y1 = (int) ans[2];
            int x2 = (int) ans[3];
            int y2 = (int) ans[4];
        }
        long AT = (long) promt(times);
        System.out.println("El tiempo promedio es: " + AT);
        int AC = (int) promc(comps);
        System.out.println("El numero de comparaciones promedio es: " + AC);

    }
    //Calcula de forma interativa la distancia minima

    public static float[] ClosestBrute(int N, Punto P[]) {
        float min = Float.MAX_VALUE;
        float Minactual = 0;
        float[] res = new float[5];
        for (int i = 0; i < N; ++i) {
            for (int j = i + 1; j < N; ++j) {

                Minactual = distancia(P[i], P[j]);
                if (Minactual < min) {
                    cc++;
                    int pix = P[i].getx();
                    int piy = P[i].gety();
                    int pjx = P[j].getx();
                    int pjy = P[j].gety();
                    min = Minactual;
                    res[0] = min;
                    res[1] = pix;
                    res[2] = piy;
                    res[3] = pjx;
                    res[4] = pjy;

                }
            }
        }
        return res;

    }
    //Calcula la disntanca entre dos pares de puntos

    public static float distancia(Punto p1, Punto p2) {
        int p1x = p1.getx();
        int p2x = p1.getx();
        int p1y = p1.gety();
        int p2y = p1.gety();
        double dist = Math.sqrt((p1x - p2x) * (p1x - p2x) + (p1y - p2y) * (p1y - p2y));
        return (float) dist;
    }

    public static float[] stripClosest(Punto[] lado, int tam, float d) {
        float min = d;
        float[] res = new float[5];
        Arrays.sort(lado, 0, tam, new ComparadorY());

        for (int i = 0; i < tam; ++i) {
            for (int j = i + 1; j < tam && (lado[j].gety() - lado[i].gety()) < min; ++j) {
                if (distancia(lado[i], lado[j]) < min) {
                    cc++;
                    int lix = lado[i].getx();
                    int liy = lado[i].gety();
                    int ljx = lado[j].getx();
                    int ljy = lado[j].gety();
                    min = distancia(lado[i], lado[j]);
                    res[0] = min;
                    res[1] = lix;
                    res[2] = liy;
                    res[3] = ljx;
                    res[4] = ljy;
                }
            }
        }

        return res;
    }
//Comparadores de puntos

    static class ComparadorY implements Comparator<Punto> {

        @Override
        public int compare(Punto pointA, Punto pointB) {

            int pAy = pointA.gety();
            int pBy = pointB.gety();
            int x = Integer.compare(pAy, pBy);
            cc++;
            return x;
        }

    }

    static class ComparadorX implements Comparator<Punto> {

        @Override
        public int compare(Punto pointA, Punto pointB) {
            int pAx = pointA.getx();
            int pBx = pointB.getx();
            cc++;
            return Integer.compare(pAx, pBx);
        }

    }
//Funcion Recursiva para encontrar los pares cercanos

    public static float[] cercanoRec(Punto[] P,
            int startIndex,
            int endIndex) {
        float[] res = new float[5];
        float[] d = new float[5];
        if ((endIndex - startIndex) <= 3) {
            cc++;
            return ClosestBrute(endIndex, P);
        }

        int mid = startIndex + (endIndex - startIndex) / 2;
        Punto PuntoMedio = P[mid];

        float[] Izq = cercanoRec(P, startIndex, mid);
        float[] Der = cercanoRec(P, mid, endIndex);

        if (Izq[0] < Der[0]) {
            cc++;
            d[1] = Izq[1];
            d[2] = Izq[2];
            d[3] = Izq[3];
            d[4] = Izq[4];
        } else {
            d[1] = Der[1];
            d[2] = Der[2];
            d[3] = Der[3];
            d[4] = Der[4];
        }
        d[0] = Math.min(Izq[0], Der[0]);

        Punto[] strip = new Punto[endIndex];
        int j = 0;
        for (int i = 0; i < endIndex; i++) {
            if (Math.abs(P[i].getx() - PuntoMedio.getx()) < d[0]) {
                cc++;
                strip[j] = P[i];
                j++;
            }
        }
        float[] ans = stripClosest(strip, j, d[0]);
        if (ans[0] == 0.0) {
            cc++;
            float x = d[0];
            res[0] = x;
            res[1] = d[1];
            res[2] = d[2];
            res[3] = d[3];
            res[4] = d[4];
        } else {
            float x = Math.min(d[0], ans[0]);

            res[0] = x;
            if (d[0] < ans[0]) {
                cc++;
                res[1] = d[1];
                res[2] = d[2];
                res[3] = d[3];
                res[4] = d[4];
            } else {
                res[1] = ans[1];
                res[2] = ans[2];
                res[3] = ans[3];
                res[4] = ans[4];
            }
        }

        return res;

    }

    public static float[] cercano(Punto[] P, int n) {
        Arrays.sort(P, 0, n, new ComparadorX());
        return cercanoRec(P, 0, n);
    }

    public static Punto[] getPoints(Punto[] points) {
        int count = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i] != null) {
                count++;
            }
        }
        Punto[] newPoints = new Punto[count];
        int j = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i] != null) {
                newPoints[j] = points[i];
                j++;
            }
        }
        return newPoints;
    }
//Promedios

    public static double promt(long[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        double sumix = (double) sum;
        return sumix / array.length;
    }

    public static double promc(int[] array) {
        double sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        double sumix = (double) sum;
        return sumix / array.length;
    }
}
