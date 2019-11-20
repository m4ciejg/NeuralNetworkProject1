package com.maciejg.network;

import java.util.Random;


public class Neuron {
    private double [] wagi;
    private double previousSum=0;
    private double previousValue=0;
    private int liczba_wejsc;
    private static double ETA = 0.1;

    private double delta = 0;

    public Neuron(){
        liczba_wejsc=0;
        wagi=null;
    }
    public Neuron(int liczba_wejsc){
        this.liczba_wejsc=liczba_wejsc;
        wagi=new double[liczba_wejsc+1];
        generuj();
    }
    private void generuj() {
        Random r=new Random();
        for(int i=0;i<=liczba_wejsc;i++)
            //wagi[i]=(r.nextDouble()-0.5)*2.0*10;//do ogladania
            wagi[i]=(r.nextDouble()-0.5)*2.0*0.01;//do projektu
    }

    public int GetInputCount() {
        return liczba_wejsc;
    }

    public double oblicz_wyjscie(double [] wejscia){

        double fi=wagi[0];
        for(int i=1;i<=liczba_wejsc;i++)
            fi+=wagi[i]*wejscia[i-1];

        previousSum = fi;
        previousValue = f(fi);

        return previousValue;
    }

    public void SetDelta(double delta) {
        this.delta = delta;
    }

    public void SetDelta(double[] previousLayerProducts) {
        delta = 0.0;

        for (double product : previousLayerProducts) {
            delta += product;
        }
    }

    public double GetDeltaMultipliedByNthWeight(int n) {
        return delta*wagi[n];
    }

    public void ChangeWeights(double[] input) {

        wagi[0] = ETA*delta*f_poch(previousSum);
        for(int i=1;i<wagi.length;i++)
            wagi[i] += ETA*delta*f_poch(previousSum)*input[i-1];

        delta = 0.0;
        previousSum=0;
        previousValue=0;
    }

    private double f(double x) {
        return 1.0/(1.0+Math.exp(-x));
    }

    private double f_poch(double x) {
        return f(x)*(1.0-f(x));
    }
}
