package org.salerno.puzzles.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxAvgSubarrayTest {

    @Test
    void example1() {
        final int[] nums = new int[]{1, 12, -5, -6, 50, 3};
        final int k = 4;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(12.75, result);
    }

    @Test
    void example2() {
        final int[] nums = new int[]{5};
        final int k = 1;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(5.0, result);
    }

    @Test
    void baseCase() {
        final int[] nums = new int[]{1, 2, 3};
        final int k = 2;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(2.5, result);
    }

    @Test
    void kIsZero() {
        final int[] nums = new int[]{1, 2};
        final int k = 0;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(0.0, result);
    }

    @Test
    void kIsGreaterThanNumsLength() {
        final int[] nums = new int[]{1, 2};
        final int k = 5;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(0.0, result);
    }

    @Test
    void kIsNegative() {
        final int[] nums = new int[]{1, 2};
        final int k = -1;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(0.0, result);
    }

    @Test
    void numsIsEmpty() {
        final int[] nums = new int[]{};
        final int k = 1;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(0.0, result);
    }

    @Test
    void numsLengthIsK() {
        final int[] nums = new int[]{1, 2};
        final int k = 2;
        final double result = MaxAvgSubarray.solve(nums, k);
        assertEquals(1.5, result);
    }

}
