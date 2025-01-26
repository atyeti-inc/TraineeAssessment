package com.atyeti.example.parals.tasks;

import java.util.concurrent.RecursiveAction;

public class ASum extends RecursiveAction {
    int[] A; // input array
    int LO, HI; // subrange
    int SUM; // return value

    public ASum (int low, int high) {
        LO = low;
        HI = high;
    }

    @Override
    protected void compute() {
        SUM = 0;
        for (int i = LO; i <= HI; i++)
            SUM += A[i];
    }

}
