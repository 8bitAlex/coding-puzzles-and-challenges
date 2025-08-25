package jmh.java.org.salerno.puzzles.array;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.salerno.puzzles.array.MaxAvgSubarray;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class MaxAvgSubarrayBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int arraySize;
    
    @Param({"10", "100", "1000"})
    private int k;
    
    private int[] nums;
    
    @Setup
    public void setup() {
        nums = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            nums[i] = (int) (Math.random() * 1000);
        }
    }
    
    @Benchmark
    public void maxAvgSubarray(Blackhole bh) {
        double result = MaxAvgSubarray.solve(nums, k);
        bh.consume(result);
    }
    
    @Benchmark
    public void baseline(Blackhole bh) {
        // Baseline to measure overhead
        bh.consume(nums.length);
    }
}
