package com.example.RestCalculator;

import com.example.RestCalculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    @Test
    public void getDescendingSequence_shouldReturnMaxDescendingSequence() {
        StringBuilder stringBuilder = new StringBuilder("12 3 2 4 3 2 14 11 10 2 1");
        List<Integer> actual = new ArrayList<>(List.of(14, 11, 10, 2, 1));
        List<Integer> expected = calculatorService.getDescendingSequence(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAscendingSequence_shouldReturnMaxAscendingSequence() {
        StringBuilder stringBuilder = new StringBuilder("12 13 15 1 2 3 4 5 6 3 2 4 3 2 14 11 10 2 1");
        List<Integer> actual = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> expected = calculatorService.getAscendingSequence(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMax_shouldReturnMaxValue() {
        StringBuilder stringBuilder = new StringBuilder("12 13 15 1 2 3 4 5 6 3 2 4 3 2 14 11 10 2 1");
        int actual = 15;
        int expected = calculatorService.getMaxValue(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMin_shouldReturnMinValue() {
        StringBuilder stringBuilder = new StringBuilder("12 -13 15 1 2 3 4 5 6 3 2 4 3 2 14 11 10 2 1");
        int actual = -13;
        int expected = calculatorService.getMinValue(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAverage_shouldReturnAverage() {
        StringBuilder stringBuilder = new StringBuilder("-10 11 1 -2 4 9 5");
        double actual = 6.0;
        double expected = calculatorService.getAverage(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMedian_shouldReturnMedian() {
        StringBuilder stringBuilder = new StringBuilder("12 13 11 1 2 2");
        double actual = 6.0;
        double expected = calculatorService.getMedian(stringBuilder);

        Assertions.assertEquals(expected, actual);
    }

}
