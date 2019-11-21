package com.maciejg.network;

import javax.swing.*;
import java.util.ArrayList;

public class Siec {

    Warstwa [] warstwy;
    int liczba_warstw;
    private static final int LICZBA_CYKLI = 12000;
    private static final double EPS = 0.01;
    public int cykle = 0;

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

        for(cykle = 0; cykle < LICZBA_CYKLI; cykle++){
            int correctAnswerCount = 0;
            for(int nrLitery = 0; nrLitery < ciagWejsc.size(); nrLitery++) {

                double[] output = ciagWejsc.get(nrLitery);
                double[] correctOutput = ciagWynikow.get(nrLitery);
                double delta[] = new double[correctOutput.length];

                for(int i=0;i<liczba_warstw;i++)
                    output=warstwy[i].oblicz_wyjscie(output);

                boolean closeEnough = true;
                for(int i=0; i<output.length; i++)
                    if(Math.abs(delta[i] = (correctOutput[i]-output[i]))> EPS)
                        closeEnough = false;

                if(closeEnough)
                    correctAnswerCount++;

                // Bledy poprzedniej warstwy
                for(int i = warstwy.length - 1; i>0; i--) {
                    warstwy[i].setDeltaInNeurons(delta);
                    delta = warstwy[i].calculateLowerLayerDelta();
                }
                warstwy[0].setDeltaInNeurons(delta);

                // zmiana wag
                for(int i = 0; i < warstwy.length; i++)
                    warstwy[i].changeWeights();
            }



            if(correctAnswerCount == ciagWejsc.size())
                //JOptionPane.showMessageDialog(null, "Uczenie zakonczone");
                break;

            if(cykle%100 == 0)
            {
                cykle++;
                cykle--;
            }

           // System.out.println("Aktualny cykl" + cykle);
        }
        JOptionPane.showMessageDialog(null, "Uczenie zakonczone");
    }


    public int[] testuj_z_ciagu(ArrayList<double[]> ciagWejsc, ArrayList<double[]> ciagWynikow){
        int [] wyjscie= {1,2};
        int correctAnswerCount = 0;
        for(int nrLitery = 0; nrLitery < ciagWejsc.size(); nrLitery++) {

            double[] output = ciagWejsc.get(nrLitery);
            double[] correctOutput = ciagWynikow.get(nrLitery);
            double delta[] = new double[correctOutput.length];

            // Oblicz wartosci
            for(int i=0;i<liczba_warstw;i++)
                output=warstwy[i].oblicz_wyjscie(output);

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