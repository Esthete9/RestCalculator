package com.example.RestCalculator.service.impl;

import com.example.RestCalculator.exceptions.FileIsEmptyException;
import com.example.RestCalculator.exceptions.FileNotFoundNumbersException;
import com.example.RestCalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Value("${FileNotFoundNumbersException}")
    private String fileNotFoundNumbersExceptionMsg;

    @Value("${FileIsEmptyExceptionMessage}")
    private String fileIsEmptyExceptionMsg;

    @Override
    public String getStringFromFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("file.txt");
        StringBuilder textFromFile = new StringBuilder();
        if (file != null) {
            if (!Files.exists(uploadPath)) {
                Files.createFile(uploadPath);
            }
            file.transferTo(uploadPath);
            List<String> lines = Files.readAllLines(uploadPath);
            for (var line : lines) {
                textFromFile.append(line + "\n");
            }
        }
        if (textFromFile.toString().isEmpty() || textFromFile.toString().isBlank())
            throw new FileIsEmptyException(fileIsEmptyExceptionMsg);
        else
            return textFromFile.toString();
    }

    @Override
    public String getStringFromByteArr(byte[] bytes) {
        return new StringBuilder(new String(bytes, StandardCharsets.UTF_8)).toString();
    }

    @Override
    public List<Integer> getListNumbersFromFileText(String textFromFile) {
        Pattern pattern = Pattern.compile("-\\d+|\\d+");
        Matcher matcher = pattern.matcher(textFromFile);
        StringBuilder res = new StringBuilder();

        while (matcher.find()) {
            res.append(matcher.group() + " ");
        }

        if (res.toString().isEmpty()) {
            throw new FileNotFoundNumbersException(fileNotFoundNumbersExceptionMsg);
        }

        String[] strArr = res.toString().split(" ");

        List<Integer> integerList = new ArrayList<>();
        for (var str : strArr) {
            integerList.add(Integer.parseInt(str));
        }

        return integerList;
    }

    @Override
    public int getMaxValue(String text) {
        return getListNumbersFromFileText(text).stream().max(Comparator.naturalOrder()).get();
    }

    @Override
    public int getMinValue(String text) {
        return getListNumbersFromFileText(text).stream().min(Comparator.naturalOrder()).get();
    }

    @Override
    public double getMedian(String text) {
        double median;
        List<Integer> numbers = getListNumbersFromFileText(text);
        if (numbers.size() % 2 == 0)
            median = (numbers.get(numbers.size() / 2) + numbers.get((numbers.size() / 2) - 1)) / 2;
        else
            median = numbers.get(numbers.size() / 2);
        return median;
    }

    @Override
    public double getAverage(String text) {
        double sum = 0;
        for (var val : getListNumbersFromFileText(text)) {
            if (val < 0)
                sum += Math.abs(val);
            else
                sum += val;
        }
        return sum / getListNumbersFromFileText(text).size();
    }

    @Override
    public List<Integer> getDescendingSequence(String text) {
        List<Integer> numbers = getListNumbersFromFileText(text);

        int currentCount = 0;
        int nextCount = 0;
        int startIndex = 0;
        int endIndex = 0;
        int tempStartInd;

        for (int i = 0; i < numbers.size() - 1; i++) {
            tempStartInd = i;
            while (numbers.get(i) > numbers.get(i + 1)) {
                nextCount++;
                i++;
                if (numbers.size() - 1 == i) {
                    break;
                }
            }
            if (nextCount > currentCount) {
                startIndex = tempStartInd;
                currentCount = nextCount;
                endIndex = i;
            }
            nextCount = 0;
        }

        return getSequence(startIndex, endIndex, numbers);
    }

    @Override
    public List<Integer> getAscendingSequence(String text) {
        List<Integer> numbers = getListNumbersFromFileText(text);

        int currentCount = 0;
        int nextCount = 0;
        int startIndex = 0;
        int endIndex = 0;
        int tempStartInd;

        for (int i = 0; i < numbers.size() - 1; i++) {
            tempStartInd = i;
            while (numbers.get(i) < numbers.get(i + 1)) {
                nextCount++;
                i++;
                if (numbers.size() - 1 == i) {
                    break;
                }
            }
            if (nextCount > currentCount) {
                startIndex = tempStartInd;
                currentCount = nextCount;
                endIndex = i;
            }
            nextCount = 0;
        }

        return getSequence(startIndex, endIndex, numbers);
    }

    private List<Integer> getSequence(int startIndex, int endIndex, List<Integer> numbers) {
        List<Integer> descendingSequence = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            descendingSequence.add(numbers.get(i));
        }
        return descendingSequence;
    }
}
