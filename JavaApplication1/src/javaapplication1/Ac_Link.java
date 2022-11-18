/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Random;

/**
 *
 * @author German Galvis & Aiker
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
        LinkedList LPunto = new LinkedList();
        Random ran = new Random();
        //El siguiente loop repite el proceso 5 veces para sacr el promedio
        for (int i = 0; i < 5; i++) {
            //Este for se asegura de que no hay coordenas repetidas que generen distancia minima de 0
            for (int j = 0; j < tamaño; j++) {

                int cx = ran.nextInt();
                int cy = ran.nextInt();
                int inc = 0;
                boolean unic = true;
                if (LPunto.head == null) {
                    Punto test = new Punto(cx, cy);
                    LPunto.insert(LPunto, test);
                } else {
                    while (unic) {
                        cx = cx + inc;
                        cy = cy + inc;
                        Punto test = new Punto(cx, cy);
                        unic = LPunto.exist(LPunto.head, test);
                        inc++;
                        if (unic == false) {
                            LPunto.insert(LPunto, test);
                        }
                    }
                }
            }
            cc = 0;
            //Se toma el tiempo de inicio
            long start = System.nanoTime();
            float[] ans = cercano(LPunto, tamaño);
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

    public static float[] ClosestBrute(int N, LinkedList.Node head) {
        float min = Float.MAX_VALUE;
        float Minactual = 0;
        float[] res = new float[5];
        LinkedList.Node i = head;
        LinkedList.Node j = head.next;
        while (i.next != null) {
            while (j.next != null) {
                Minactual = distancia(i.data, j.data);
                if (Minactual < min) {
                    cc++;
                    int pix = i.data.getx();
                    int piy = i.data.gety();
                    int pjx = j.data.getx();
                    int pjy = j.data.gety();
                    min = Minactual;
                    res[0] = min;
                    res[1] = pix;
                    res[2] = piy;
                    res[3] = pjx;
                    res[4] = pjy;

                }

                j = j.next;
            }
            i = i.next;
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

    public static float[] stripClosest(LinkedList list, int tam, float d) {
        float min = d;
        float[] res = new float[5];
        list.sortList();
        LinkedList.Node i = list.head;
        while (i != null) {
            LinkedList.Node j = i.next;
            while (j != null && (j.data.gety() - i.data.gety() < min)) {
                cc++;
                int lix = i.data.getx();
                int liy = i.data.gety();
                int ljx = j.data.getx();
                int ljy = j.data.getx();
                min = distancia(i.data, j.data);
                res[0] = min;
                res[1] = lix;
                res[2] = liy;
                res[3] = ljx;
                res[4] = ljy;
                j = j.next;
            }
            i = i.next;
        }

        return res;
    }
//Comparadores de puntos


//Funcion Recursiva para encontrar los pares cercanos

    public static float[] cercanoRec(LinkedList list,
            int startIndex,
            int endIndex) {
        float[] res = new float[5];
        float[] d = new float[5];
        LinkedList.Node head = list.head;
        if ((endIndex - startIndex) <= 3) {
            cc++;
            return ClosestBrute(endIndex, head);
        }

        int mid = startIndex + (endIndex - startIndex) / 2;
        Punto PuntoMedio = middleNode(head);

        float[] Izq = cercanoRec(list, startIndex, mid);
        float[] Der = cercanoRec(list, mid, endIndex);

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

        //Punto[] strip = new Punto[endIndex];
        LinkedList strip = new LinkedList();

        LinkedList.Node i = head;
        LinkedList.Node j = head.next;
        int cont = 0;
        while (i != null) {
            while (j != null) {
                if (Math.abs(i.data.getx() - PuntoMedio.getx()) < d[0]) {
                    cc++;

                    strip.insert(strip, i.data);
                    
                    cont++;
                }
                j = j.next;

            }
            i = i.next;
        }
        float[] ans = stripClosest(strip, cont, d[0]);
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

    public static float[] cercano(LinkedList list, int n) {
        list.sortList();
        return cercanoRec(list, 0, n);
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

    static Punto middleNode(LinkedList.Node head) {
        int l = 0;
        LinkedList.Node p = head;
        while (p != null) {
            p = p.next;
            l = l + 1;
        }
        p = head;
        int c = 0;
        while (c < l / 2) {
            p = p.next;
            c = c + 1;
        }
        return p.data;
    }
}
