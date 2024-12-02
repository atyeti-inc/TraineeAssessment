package com.atyeti.example.functionary.session1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InnerClassExample {
    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running a task.");
            }
        };
        new Thread(task).start();

        Runnable task1 = () -> System.out.println("Running a task with a lambda!");
        new Thread(task1).start();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Inner anonymous class implementing Runnable
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed using an inner anonymous class with executor service.");
            }
        });

        executor.shutdown();
    }
}
