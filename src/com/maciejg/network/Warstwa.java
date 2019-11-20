package com.maciejg.network;

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
    public double[] calculateLowerLayerDelta() {
        int lowerLayerNeuronCount = neurony[0].getInputCount();
        double[] delta = new double[lowerLayerNeuronCount];

        for (int i = 0; i < lowerLayerNeuronCount; i++) {
            for (int j = 0; j < neurony.length; j++) {
                delta[i] += neurony[j].getDeltaMultipliedByNthWeight(i);
            }
        }
        return delta;
    }

    public void setDeltaInNeurons(double[] delta) {

        for(int i = 0; i<delta.length;i++ ) {
            neurony[i].setDelta(delta[i]);		}
    }

    public void changeWeights() {
        for(int i=0; i<neurony.length; i++)
            neurony[i].changeWeights(previousInput);
    }
}