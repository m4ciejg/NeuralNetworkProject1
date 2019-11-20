package com.maciejg.network;

import com.maciejg.network.Neuron;

public class Warstwa {
    Neuron [] neurony;
    int liczba_neuronow;
    double[] previousInput;
    public Warstwa(){
        neurony=null;
        liczba_neuronow=0;
    }
    public Warstwa(int liczba_wejsc,int liczba_neuronow){
        this.liczba_neuronow=liczba_neuronow;
        neurony=new Neuron[liczba_neuronow];
        for(int i=0;i<liczba_neuronow;i++)
            neurony[i]=new Neuron(liczba_wejsc);
    }

    double [] oblicz_wyjscie(double [] wejscia){
        previousInput = wejscia;
        double [] wyjscie=new double[liczba_neuronow];
        for(int i=0;i<liczba_neuronow;i++)
            wyjscie[i]=neurony[i].oblicz_wyjscie(wejscia);
        return wyjscie;
    }

    // TODO: zapami�ta� wyniki, na razie za ka�dym razem, powtarzaj�c, wywo�uj� metod�
    public double[] CalculateLowerLayerDelta() {
        int lowerLayerNeuronCount = neurony[0].GetInputCount();
        double[] delta = new double[lowerLayerNeuronCount];

        for (int i = 0; i < lowerLayerNeuronCount; i++) {
            for (int j = 0; j < neurony.length; j++) {
                delta[i] += neurony[j].GetDeltaMultipliedByNthWeight(i);
            }
        }
        return delta;
    }

    public void SetDeltaInNeurons(double[] delta) {

        for(int i = 0; i<delta.length;i++ ) {
            neurony[i].SetDelta(delta[i]);		}
    }

    public void ChangeWeights() {
        for(int i=0; i<neurony.length; i++)
            neurony[i].ChangeWeights(previousInput);
    }
}