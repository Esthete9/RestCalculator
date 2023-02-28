package com.example.RestCalculator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface CalculatorService {
    public String getStringFromFile(MultipartFile file) throws IOException;
    public String getStringFromByteArr(byte[] bytes);
    public List<Integer> getListNumbersFromFileText(String textFromFile);
    public int getMaxValue(String text);
    public int getMinValue(String text);
    public double getMedian(String text);
    public double getAverage(String text);
    public List<Integer> getDescendingSequence(String text);
    public List<Integer> getAscendingSequence(String text);
}
