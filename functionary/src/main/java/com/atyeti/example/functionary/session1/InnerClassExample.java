package com.atyeti.example.functionary.session1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InnerClassExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Inner anonymous class implementing Runnable
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed using an inner anonymous class.");
            }
        });

        executor.shutdown();
    }
}
