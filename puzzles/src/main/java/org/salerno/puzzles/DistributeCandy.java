package org.salerno.puzzles;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Candy Distribution
 *
 * <p><b>Prompt</b>:
 * There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 * <p>
 * You are giving candies to these children subjected to the following requirements:
 * <p>
 * - Each child must have at least one candy.
 * <p>
 * - Children with a higher rating get more candies than their neighbors.
 * <p>
 * Return the minimum number of candies you need to have to distribute the candies to the children.
 *
 * <p><b>Approach</b>:
 *
 * <p><b>Time</b>:  <b>Space</b>:
 *
 * <p><a href="https://leetcode.com/problems/candy">Source</a>
 */
public class DistributeCandy {

    public static int solve(int[] ratings) {
        final int n = ratings.length;
        if(n == 0) return 0;

        final int[] candy = new int[n];
        Arrays.fill(candy, 1);

        for(int i = 1; i < n; i++) {
            if(ratings[i] > ratings[i-1]) {
                candy[i] = candy[i-1] + 1;
            }
        }
        for(int i = n - 2; i >= 0; i--) {
            if(ratings[i] > ratings[i+1]) {
                candy[i] = Math.max(candy[i], candy[i+1]+1) ;
            }
        }
        return Arrays.stream(candy).sum();
    }

}

// 1,2,1 = 4
// 1,0,2 = 5
// 1,2,2 = 4
// 1,1,1 = 3