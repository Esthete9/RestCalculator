package com.example.RestCalculator.service;

import com.example.RestCalculator.exceptions.FileIsEmptyException;
import com.example.RestCalculator.exceptions.FileNotFoundNumbersException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

@Service("CalculatorService")
public class CalculatorService {

    @Value("${FileNotFoundNumbersException}")
    private String fileNotFoundNumbersExceptionMsg;

    @Value("${FileIsEmptyExceptionMessage}")
    private String fileIsEmptyExceptionMsg;

    @Cacheable("getStringFromFile")
    public StringBuilder getStringFromFile(MultipartFile file) throws IOException {
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
            return textFromFile;
    }

    @Cacheable("getStringFromByteArr")
    public StringBuilder getStringFromByteArr(byte[] bytes) {
        return new StringBuilder(new String(bytes, StandardCharsets.UTF_8));
    }

    @Cacheable("getListNumbersFromFile")
    public List<Integer> getListNumbersFromFile(StringBuilder textFromFile) {
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
    @Cacheable("getMaxValue")
    public int getMaxValue(StringBuilder stringBuilder)  {
        return getListNumbersFromFile(stringBuilder).stream().max(Comparator.naturalOrder()).get();
    }
    @Cacheable("getMinValue")
    public int getMinValue(StringBuilder stringBuilder) {
        return getListNumbersFromFile(stringBuilder).stream().min(Comparator.naturalOrder()).get();
    }
    @Cacheable("getMedian")
    public double getMedian(StringBuilder stringBuilder) {
        double median;
        List<Integer> numbers = getListNumbersFromFile(stringBuilder);
        if (numbers.size() % 2 == 0)
            median = (numbers.get(numbers.size() / 2) + numbers.get((numbers.size() / 2) - 1)) / 2;
            else
                median = numbers.get(numbers.size() / 2);
            return median;
    }

    @Cacheable("getAverage")
    public double getAverage(StringBuilder stringBuilder) {
        double sum = 0;
        for (var val : getListNumbersFromFile(stringBuilder)) {
            if (val < 0)
                sum += Math.abs(val);
            else
                sum += val;
        }
        return sum / getListNumbersFromFile(stringBuilder).size();
    }

    @Cacheable("getDescendingSequence")
    public List<Integer> getDescendingSequence(StringBuilder stringBuilder) {
        List<Integer> numbers = getListNumbersFromFile(stringBuilder);

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

    @Cacheable("getAscendingSequence")
    public List<Integer> getAscendingSequence(StringBuilder stringBuilder) {
        List<Integer> numbers = getListNumbersFromFile(stringBuilder);

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
