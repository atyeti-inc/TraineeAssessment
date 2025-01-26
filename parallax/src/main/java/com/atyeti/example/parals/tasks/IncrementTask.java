package com.atyeti.example.parals.tasks;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.LongStream;

public class IncrementTask extends RecursiveAction {
    private int THRESHOLD; // Starting point
    final long[] array;
    final int lo, hi;

    IncrementTask(long[] array, int lo, int hi, int threshold) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
        this.THRESHOLD = threshold;
    }

    protected void compute() {
        if (hi - lo < THRESHOLD) {
            for (int i = lo; i < hi; ++i)
                array[i]++;
        } else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new IncrementTask(array, lo, mid, THRESHOLD),
                    new IncrementTask(array, mid, hi, THRESHOLD));
        }
    }

    public static void main(String[] args) {
        int INITIAL_THRESHOLD = 25; // Starting point
        long size = 100000000L;
        // Example array
        // long[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        System.out.println("Generating an array of size: " + size);
        Instant startrand = Instant.now();
        long[] array = getExampleArray(size);
        System.out.println(
                "Generation time: " + Duration.between(startrand, Instant.now()).toMillis() + " milliseconds\n");

        // Experiment with different THRESHOLD values
        for (int threshold : new int[] {
                INITIAL_THRESHOLD, INITIAL_THRESHOLD * 2, INITIAL_THRESHOLD / 2
        }) {
            System.out.println("Threshold value: " + threshold);
            IncrementTask task = new IncrementTask(array, 0, array.length, threshold);

            // Measure execution time
            Instant start = Instant.now();
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(task);

            Instant end = Instant.now();
            Duration duration = Duration.between(start, end);
            // Print the incremented array
            /*
             * System.out.print("Incremented Array: ");
             * for (long value : array) {
             * System.out.print(value + " ");
             * }
             * System.out.println();
             */
            System.out.println("Execution time: " + duration.toMillis() + " milliseconds");
        }
    }

    private static long[] getExampleArray(long size) {
        // Using IntStream and Random
        long[] randomArray = LongStream.generate(() -> new Random().nextLong())
                .unordered()
                .limit(size)
                .toArray();

        // Print the first 10 elements (for example)
        System.out.println("First 3 elements of the random array of size: "
                + randomArray.length + "\n");
        for (int i = 0; i < 3; i++) {
            System.out.print(randomArray[i] + " ");
        }
        System.out.println();

        return randomArray;
    }
}
