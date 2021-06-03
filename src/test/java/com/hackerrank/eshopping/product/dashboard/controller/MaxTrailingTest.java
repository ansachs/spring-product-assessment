package com.hackerrank.eshopping.product.dashboard.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class MaxTrailingTest {

    @Test
    public void doStuff() {
        List<Integer> integers = Arrays.asList(
                7,
                2,
                3,
                10,
                2,
                4,
                8,
                1
        );
        int i = maxTrailing(integers);
        System.out.println(i);
    }

    public static int maxTrailing(List<Integer> levels) {
        // Write your code here
        if (levels.size() == 0) return -1;

        int min = levels.get(0);
        int maxDiff = -1;
        for (int level: levels) {
            int currentDiff = level - min;
            if (level < min) {
                min = level;
            } else if (currentDiff > 0 && currentDiff > maxDiff) {
                maxDiff = currentDiff;
            }
        }
        return maxDiff;
    }
}
