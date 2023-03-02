package com.example.RestCalculator;

import com.example.RestCalculator.exceptions.FileIsEmptyException;
import com.example.RestCalculator.exceptions.FileNotFoundNumbersException;
import com.example.RestCalculator.service.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CalculatorServiceTest {

    @Autowired
    private CalculatorService calculatorService;

    private static String textFromFile;

    @BeforeAll
    public static void setUp() {
        textFromFile = "test12test test13 11test 1 3 -2 -3 5";
    }

    @Test
    public void getStringFromFile_shouldReturnAllTextFromFile() throws IOException {
        String actual = "test12 test12 test12 test12.";
        String expected = calculatorService.getStringFromFile(new MockMultipartFile("fileForTest", "fileForTest.txt",
                MediaType.TEXT_PLAIN_VALUE, "test12 test12 test12 test12.".getBytes()));
        Assertions.assertEquals(expected.trim(), actual.trim());
    }

    @Test
    public void getStringFromFile_shouldTrowFileNotFoundNumbersException() {
        assertThrows(FileIsEmptyException.class,
                () -> {calculatorService.getStringFromFile(new MockMultipartFile("fileForTest", "fileForTest.txt",
                        MediaType.TEXT_PLAIN_VALUE, "".getBytes()));});
    }

    @Test
    public void getListNumbersFromFile_shouldReturnListNumbers() {
        List<Integer> actual = new ArrayList<>(List.of(12, 13, 11, 1, 3, -2, -3, 5));
        List<Integer> expected = calculatorService.getListNumbersFromFileText(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getListNumbersFromFile_shouldTrowException() {
        String text = "test";
        assertThrows(FileNotFoundNumbersException.class,
                () -> {calculatorService.getListNumbersFromFileText(text);});
    }

    @Test
    public void getDescendingSequence_shouldReturnMaxDescendingSequence() {
        List<List<Integer>> actual = new ArrayList<>(List.of(new ArrayList<>(List.of(13, 11, 1)), new ArrayList<>(List.of(3, -2, -3))));
        List<List<Integer>> expected = calculatorService.getDescendingSequence(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAscendingSequence_shouldReturnMaxAscendingSequence() {
        List<List<Integer>> actual = new ArrayList<>(List.of(new ArrayList<>(List.of(12, 13)), new ArrayList<>(List.of(1, 3)),
                new ArrayList<>(List.of(-3, 5))));
        List<List<Integer>> expected = calculatorService.getAscendingSequence(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getLongestSequences_shouldReturnLongestSequences() {
        List<List<Integer>> test = new ArrayList<>(List.of(new ArrayList<>(List.of(12, 13)), new ArrayList<>(List.of(1, 3)),
                new ArrayList<>(List.of(-3))));
        List<List<Integer>> actual = new ArrayList<>(List.of(new ArrayList<>(List.of(12, 13)), new ArrayList<>(List.of(1, 3))));

        List<List<Integer>> expected = calculatorService.getLongestSequences(test);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMax_shouldReturnMaxValue() throws InterruptedException {
        int actual = 13;
        int expected = calculatorService.getMaxValue(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMin_shouldReturnMinValue() {
        int actual = -3;
        int expected = calculatorService.getMinValue(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getAverage_shouldReturnAverage() {
        double actual = 5.0;
        double expected = calculatorService.getAverage(textFromFile);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getMedian_shouldReturnMedian() {
        double actual = 2.0;
        double expected = calculatorService.getMedian(textFromFile);

        Assertions.assertEquals(expected, actual);
    }
}
