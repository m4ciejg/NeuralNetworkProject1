package com.maciejg.network;

import java.util.ArrayList;

public class Siec {

    Warstwa [] warstwy;
    int liczba_warstw;
    private static final int LICZBA_CYKLI = 12000;
    private static final double EPS = 0.01;

    public Siec(){
        warstwy=null;
    }
    public Siec(int liczba_wejsc,int liczba_warstw,int [] lnww){
        this.liczba_warstw=liczba_warstw;
        warstwy=new Warstwa[liczba_warstw];
        for(int i=0;i<liczba_warstw;i++)
            warstwy[i]=new Warstwa((i==0)?liczba_wejsc:lnww[i-1],lnww[i]);
    }
    public double [] oblicz_wyjscie(double [] wejscia){
        double [] wyjscie=null;
        for(int i=0;i<liczba_warstw;i++)
            wejscia=wyjscie=warstwy[i].oblicz_wyjscie(wejscia);
        return wyjscie;
    }

    public void ucz_z_ciagu(ArrayList<double[]> ciagWejsc, ArrayList<double[]> ciagWynikow){

        for(int cykle = 0; cykle < LICZBA_CYKLI; cykle++){
            int correctAnswerCount = 0;
            for(int nrLitery = 0; nrLitery < ciagWejsc.size(); nrLitery++) {

                double[] output = ciagWejsc.get(nrLitery);
                double[] correctOutput = ciagWynikow.get(nrLitery);
                double delta[] = new double[correctOutput.length];

                // Oblicz warto�ci
                for(int i=0;i<liczba_warstw;i++)
                    output=warstwy[i].oblicz_wyjscie(output);

                // Oblicz bledy
                boolean closeEnough = true;
                for(int i=0; i<output.length; i++)
                    if(Math.abs(delta[i] = (correctOutput[i]-output[i]))> EPS)
                        closeEnough = false;

                if(closeEnough)
                    correctAnswerCount++;

                // Oblicz bledy dla ka�dej poprzedniej warstwy
                for(int i = warstwy.length - 1; i>0; i--) {
                    warstwy[i].SetDeltaInNeurons(delta);
                    delta = warstwy[i].CalculateLowerLayerDelta();
                }
                warstwy[0].SetDeltaInNeurons(delta);

                // Zmie� wagi
                for(int i = 0; i < warstwy.length; i++)
                    warstwy[i].ChangeWeights();
            }

            if(correctAnswerCount == ciagWejsc.size())
                break;

            if(cykle%100 == 0)
            {
                cykle++;
                cykle--;
            }
        }
    }


    public int[] testuj_z_ciagu(ArrayList<double[]> ciagWejsc, ArrayList<double[]> ciagWynikow){
        int [] wyjscie= {1,2};
        int correctAnswerCount = 0;
        for(int nrLitery = 0; nrLitery < ciagWejsc.size(); nrLitery++) {

            double[] output = ciagWejsc.get(nrLitery);
            double[] correctOutput = ciagWynikow.get(nrLitery);
            double delta[] = new double[correctOutput.length];

            // Oblicz warto�ci
            for(int i=0;i<liczba_warstw;i++)
                output=warstwy[i].oblicz_wyjscie(output);

            // Oblicz bledy
            boolean closeEnough = true;
            for(int i=0; i<output.length; i++)
                if(Math.abs(delta[i] = (correctOutput[i]-output[i]))> EPS)
                    closeEnough = false;

            if(closeEnough)
                correctAnswerCount++;
        }

        wyjscie[0]=correctAnswerCount;
        wyjscie[1]=ciagWejsc.size();
        return wyjscie;
    }
}