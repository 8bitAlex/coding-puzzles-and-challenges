package test.java.org.salerno.puzzles.array;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Random;
import org.salerno.puzzles.array.MaxAvgSubarray;

public class MaxAvgSubarrayBenchmark {
    
    private int[] smallArray;
    private int[] mediumArray;
    private int[] largeArray;
    private int[] hugeArray;
    
    @BeforeEach
    void setUp() {
        Random random = new Random(42); // Fixed seed for reproducibility
        
        smallArray = new int[1000];
        mediumArray = new int[10000];
        largeArray = new int[100000];
        hugeArray = new int[1000000];
        
        for (int i = 0; i < smallArray.length; i++) {
            smallArray[i] = random.nextInt(1000);
        }
        for (int i = 0; i < mediumArray.length; i++) {
            mediumArray[i] = random.nextInt(1000);
        }
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(1000);
        }
        for (int i = 0; i < hugeArray.length; i++) {
            hugeArray[i] = random.nextInt(1000);
        }
    }
    
    @Test
    void benchmarkSmallArray() {
        benchmark("Small Array (1K)", smallArray, 10);
        benchmark("Small Array (1K)", smallArray, 100);
    }
    
    @Test
    void benchmarkMediumArray() {
        benchmark("Medium Array (10K)", mediumArray, 10);
        benchmark("Medium Array (10K)", mediumArray, 100);
        benchmark("Medium Array (10K)", mediumArray, 1000);
    }
    
    @Test
    void benchmarkLargeArray() {
        benchmark("Large Array (100K)", largeArray, 10);
        benchmark("Large Array (100K)", largeArray, 100);
        benchmark("Large Array (100K)", largeArray, 1000);
    }
    
    @Test
    void benchmarkHugeArray() {
        benchmark("Huge Array (1M)", hugeArray, 10);
        benchmark("Huge Array (1M)", hugeArray, 100);
        benchmark("Huge Array (1M)", hugeArray, 1000);
    }
    
    private void benchmark(String name, int[] array, int k) {
        // Warmup
        for (int i = 0; i < 1000; i++) {
            MaxAvgSubarray.solve(array, k);
        }
        
        // Benchmark
        int iterations = 1000;
        long startTime = System.nanoTime();
        
        for (int i = 0; i < iterations; i++) {
            MaxAvgSubarray.solve(array, k);
        }
        
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double avgTime = (double) totalTime / iterations;
        
        System.out.printf("%s | k=%d | Avg: %.2f ns | Total: %.2f ms%n", 
                         name, k, avgTime, totalTime / 1_000_000.0);
    }
}
