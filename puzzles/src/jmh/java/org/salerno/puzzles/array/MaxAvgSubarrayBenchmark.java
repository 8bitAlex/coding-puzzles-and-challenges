package org.salerno.puzzles.array;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MaxAvgSubarrayBenchmark {

    @State(Scope.Thread)
    public static class BenchmarkState {

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

    }
    
    @Benchmark
    public void maxAvgSubarray(BenchmarkState state, Blackhole bh) {
        double result = MaxAvgSubarray.solve(state.nums, state.k);
        bh.consume(result);
    }
    
    @Benchmark
    public void baseline(BenchmarkState state, Blackhole bh) {
        // Baseline to measure overhead
        bh.consume(state.nums.length);
    }

}
