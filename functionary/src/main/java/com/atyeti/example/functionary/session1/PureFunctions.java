package com.atyeti.example.functionary.session1;

//import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PureFunctions {

    int multiplier = 2; // External state

    public int impureSquare(int x) {
        return x * multiplier; // Relies on external state
    }

    // pure function f(x) = x * x
    public static int square(int x) {
        return x * x;
    }

    // f(x,y) = x+y
    public static int add(int x, int y) {
        return x + y; // Pure and predictable
    }

    /*
     * Below, the function is impure because it produces a side effect (logging).
     * While logging is often necessary, it makes testing harder,
     * as the function's behavior isn’t entirely self-contained.
     */
    public static void logFullName(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        System.out.println("Logging: " + fullName); // Side effect: Logging to console
    }

    /*
     * Composability:
     * 
     * Pure functions can be combined to create more complex behaviors,
     * much like composing mathematical functions.
     * Example: 𝑔(𝑥)=𝑥+1
     */
    public static int increment(int x) {
        return x + 1;
    }

    // f'(x) = x^2 + x + 1
    // f'(x) = f(x) + g(x)
    public static int compute(int x) {
        return square(x) + increment(x);
    }

    /*
     * Discount calculation example
     */
    // Pure version
    public static double calculateDiscount(double price, double discountRate) {
        return price * (1 - discountRate);
    }

    private double globalRate = 0.1; // External state

    // Impure version
    public double calculateDiscount(double price) {
        return price * (1 - globalRate);
    }

    // Impure function: Writes to a database
    public void saveUser(String username) {
        // database.save(username); // External state modification
    }

    // Pure function: Immutable transformation
    public String formatUserName(String username) {
        return username.trim().toLowerCase(); // No external dependencies
    }

    public static void main(String[] args) {
        System.out.println(square(2));
        System.out.println(square(3));
        System.out.println(add(3, 5)); // Outputs: 8
        System.out.println(add(10, -3)); // Outputs: 7

        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5)
                .filter(n -> {
                    System.out.println("Filtering: " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("Mapping: " + n);
                    return n * n;
                });

        // The "Filtering" and "Mapping" operations execute only when forEach is called.
        System.out.println("Starting evaluation...");
        stream.forEach(System.out::println);

        // Pure functions are inherently thread-safe because they don’t rely on shared
        // state.
        IntStream.range(1, 5)
                .parallel()
                .map(x -> square(x))
                .forEach(System.out::println);

        /*
         * map(x -> x * x) is a pure function.
         * It’s composed declaratively into the Stream pipeline.
         */
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> squares = numbers.stream()
                .map(x -> x * x) // Pure mapping function
                .collect(Collectors.toList());

        System.out.println(squares); // Outputs: [1, 4, 9, 16, 25]

        // lambdas in sorting, filtering large datasets
        List<Integer> numbers1 = Arrays.asList(3, 1, 4, 1, 5, 9);
        numbers1.stream()
                .sorted()
                .distinct()
                .forEach(System.out::println); // Outputs: 1, 3, 4, 5, 9

        // Key addition:
        System.out.println("Pipeline is defined but not executed until terminal operation.");
        // stream.forEach(System.out::println); // Triggers execution

        // Declarative programming and immutability in Streams
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> result = names.stream()
                .filter(name -> name.startsWith("A")) // Declarative filtering
                .map(String::toUpperCase) // Immutable transformation
                .collect(Collectors.toList()); // Collect to new list

        result.stream().forEach(System.out::println);
        // benefits like parallelism with parallelStream and thread-safety via
        // immutability
        List<String> words = Arrays.asList("Java", "Functional", "Programming");
        words.parallelStream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // Generics and Type Safety with Lambdas
        Function<String, Integer> lengthFunction = str -> str.length();
        ToIntFunction<String> lengthIntFunction = str -> str.length();
        System.out.println(lengthFunction.apply("Functional Programming")); // Output: 20
        System.out.println(lengthIntFunction.applyAsInt("Functional Programming")); // Output: 20

    }
}