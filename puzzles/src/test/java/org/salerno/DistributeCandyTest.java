package org.salerno;

import org.junit.jupiter.api.Test;
import org.salerno.puzzles.DistributeCandy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistributeCandyTest {

    @Test
    public void example1() {
        int[] ratings = {1,0,2};
        assertEquals(5, DistributeCandy.solve(ratings));
    }

    @Test
    public void example2() {
        int[] ratings = {1,2,2};
        assertEquals(4, DistributeCandy.solve(ratings));
    }

    @Test
    public void example3() {
        int[] ratings = {1,3,2,2,1};
        assertEquals(7, DistributeCandy.solve(ratings));
    }

    @Test
    public void baseCase() {
        int[] ratings = {1,0,2,2,5,2,8,100,2};
        assertEquals(15, DistributeCandy.solve(ratings));
    }

    @Test
    public void ascendThenDescend() {
        int[] ratings = {1,3,2,1};
        assertEquals(7, DistributeCandy.solve(ratings));
    }

    @Test
    public void ascendingRating() {
        int[] ratings = {1,2,3,4}; // 1, 2, 2, 2
        assertEquals(10, DistributeCandy.solve(ratings));
    }

    @Test
    public void descendingRating() {
        int[] ratings = {4,3,2,1};
        assertEquals(10, DistributeCandy.solve(ratings));
    }

    @Test
    public void equalRating() {
        int[] ratings = {1,1,1,1,1};
        assertEquals(5, DistributeCandy.solve(ratings));
    }

}
