package com.example.RestCalculator;

import com.example.RestCalculator.exceptions.FileNotFoundNumbersException;
import com.example.RestCalculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    private static StringBuilder textFromFile;
    private static List<Integer> actual;

    @BeforeAll
    public static void setUp() {
        textFromFile = new StringBuilder("test12test test13 11test 1 3 -2");
    }

    @Test
    public void getListNumbersFromFile_shouldReturnListNumbers() {
        List<Integer> actual = new ArrayList<>(List.of(12, 13, 11, 1, 3, -2));
        List<Integer> expected = calculatorService.getListNumbersFromFile(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getListNumbersFromFile_shouldTrowException() {
        StringBuilder text = new StringBuilder("test");
        assertThrows(FileNotFoundNumbersException.class,
                () -> {calculatorService.getListNumbersFromFile(text);});
    }

    @Test
    public void getDescendingSequence_shouldReturnMaxDescendingSequence() {
        List<Integer> actual = new ArrayList<>(List.of(13, 11, 1));
        List<Integer> expected = calculatorService.getDescendingSequence(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAscendingSequence_shouldReturnMaxAscendingSequence() {
        List<Integer> actual = new ArrayList<>(List.of(12, 13));
        List<Integer> expected = calculatorService.getAscendingSequence(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMax_shouldReturnMaxValue() {
        int actual = 13;
        int expected = calculatorService.getMaxValue(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMin_shouldReturnMinValue() {
        int actual = -2;
        int expected = calculatorService.getMinValue(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAverage_shouldReturnAverage() {
        double actual = 7.0;
        double expected = calculatorService.getAverage(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMedian_shouldReturnMedian() {
        double actual = 6.0;
        double expected = calculatorService.getMedian(textFromFile);

        Assertions.assertEquals(expected, actual);
    }
}
