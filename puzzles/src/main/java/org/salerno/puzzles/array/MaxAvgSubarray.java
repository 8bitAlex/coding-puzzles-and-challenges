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

        int sum = 0;
        for(int i = 0; i < k; i++) {
            sum += nums[i];
        }
        
        int max = sum;
        for(int i = k; i < nums.length; i++) {
            sum = sum - nums[i-k] + nums[i];
            max = Math.max(max, sum);
        }
        
        return (double) max / k;
    }

}
