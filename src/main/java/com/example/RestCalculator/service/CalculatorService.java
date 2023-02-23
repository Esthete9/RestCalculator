package com.example.RestCalculator.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("CalculatorService")
public class CalculatorService {

    public CalculatorService() {
    }

    public List<Integer> getListNumbersFromFile(StringBuilder stringBuilder) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(stringBuilder);
        String res = "";

        while (matcher.find()) {
            res += matcher.group() + " ";
        }
        String[] strArr = res.split(" ");

        List<Integer> integerList = new ArrayList<>();

        for (var str : strArr) {
           integerList.add(Integer.parseInt(str));
        }
        return integerList;
    }

    public int getMaxValue(StringBuilder stringBuilder) {
        return getListNumbersFromFile(stringBuilder).stream().max(Comparator.naturalOrder()).get();
    }

    public int getMinValue(StringBuilder stringBuilder) {
        return getListNumbersFromFile(stringBuilder).stream().min(Comparator.naturalOrder()).get();
    }

    public int getMedian(StringBuilder stringBuilder) {
        int median = 0;
        List<Integer> numbers = getListNumbersFromFile(stringBuilder);
        if (numbers.size() % 2 == 0)
            median = (numbers.get(numbers.size() / 2) + numbers.get((numbers.size() / 2) - 1)) / numbers.size();
            else
                median = numbers.get(numbers.size() / 2);
            return median;
    }

    public int getAverage(StringBuilder stringBuilder) {
        int sum = 0;
        for (var val : getListNumbersFromFile(stringBuilder))
            sum += val;
        return sum / getListNumbersFromFile(stringBuilder).size();
    }

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
