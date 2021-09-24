package Homework12;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){

        int SIZE = 10000000;
        int HALF = SIZE / 2;
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1);

        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        method1(arr);
        method2(arr, a1, a2, HALF);
    }

    static void method1(float[] arr){

        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время цикла расчета для полного массива = " + (System.currentTimeMillis() - a));
    }

    static void method2(float[] arr, float[] a1, float[] a2, int HALF){
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        System.out.println("Время разбивки массива на 2 массива = " + (System.currentTimeMillis() - a));
        Thread t1 = new Thread(()-> {
            long b = System.currentTimeMillis();
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Время цикла расчета для массива а1 = " + (System.currentTimeMillis() - b));
        });

        Thread t2 = new Thread(()-> {
            long c = System.currentTimeMillis();
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Время цикла расчета для массива а2 = " + (System.currentTimeMillis() - c));
        });


        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long d = System.currentTimeMillis();
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Время склейки массива в один = " + (System.currentTimeMillis() - d));
    }
}