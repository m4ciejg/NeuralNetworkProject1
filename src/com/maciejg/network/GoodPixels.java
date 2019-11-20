package com.maciejg.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodPixels {

    private List<Integer> goodZ = new ArrayList<>();
    private List<Integer> goodS = new ArrayList<>();
    private List<Integer> good2 = new ArrayList<>();

    public void initGoodArraysRandomly() {
        good2 = new ArrayList<>();
        goodS = new ArrayList<>();
        goodZ = new ArrayList<>();

        for(int i = 0; i < 784; i++) {
            goodZ.add(0);
            goodS.add(0);
            good2.add(0);
        }
    }

    public void setGoodZ(List<Integer> goodZ) {
        this.goodZ = goodZ;
    }

    public void setGoodS(List<Integer> goodS) {
        this.goodS = goodS;
    }

    public void setGood2(List<Integer> good2) {
        this.good2 = good2;
    }

    public List<Integer> getGoodZ() {
        return goodZ;
    }

    public List<Integer> getGoodS() {
        return goodS;
    }

    public List<Integer> getGood2() {
        return good2;
    }
}
