package org.salerno.puzzles.array;

/**
 * Max Average Subarray
 *
 * <p><b>Prompt</b>:
 * You are given an integer array `nums` consisting of n elements, and an integer k.
 * Find a contiguous subarray whose length is equal to k that has the maximum average value and return this value.
 *
 * <p><b>Approach</b>: Sliding Window
 *
 * <p><b>Time</b>: O(n+k) <b>Space</b>: O(1)
 *
 * <p><a href="https://leetcode.com/problems/maximum-average-subarray-i/">Source</a>
 */
public class MaxAvgSubarray {

    public static double solve(int[] nums, int k) {
        if(k < 1 || nums.length == 0 || k > nums.length) return 0.0;

        double max = Double.NEGATIVE_INFINITY;
        double sum = 0.0;
        for(int i = 0; i+k <= nums.length; i++) {
            if(i == 0) {
                for(int j = 0; j < k; j++) {
                    sum += nums[j];
                }
            } else {
                sum -= nums[i-1];
                sum += nums[i+k-1];
            }
            max = Math.max(max, sum/k);
        }
        return max;
    }

}
