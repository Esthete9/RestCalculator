package com.example.RestCalculator.controllers;

import com.example.RestCalculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Api(tags = "Controller API")
@Tag(name = "Controller API", description = "Контроллер калькулятора")
@RequestMapping(value = "/api", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello world!!!";
    }

    @ApiOperation("Возвращает максимальное число в файле")
    @PostMapping("/max")
    public int getMaxNumberInFile(@RequestParam("reader") MultipartFile reader) throws IOException {
        if (reader != null)
            return calculatorService.getMaxValue(getStringFromFile(reader));
        else return 0;

    }

    @ApiOperation("Возвращает минимальное число в файле")
    @PostMapping("/min")
    public int getMinNumberInFile(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getMinValue(getStringFromFile(reader));
    }

    @ApiOperation("Возвращает медиану среди чисел в файле")
    @PostMapping("/median")
    public double getMedian(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getMedian(getStringFromFile(reader));
    }

    @ApiOperation("Возвращает среднее арифметическое значение среди чисел в файле")
    @PostMapping("/average")
    public double getAverage(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getAverage(getStringFromFile(reader));
    }

    @ApiOperation("Возвращает самую длинную убывающую последовательность среди чисел в файле")
    @PostMapping("/ascending")
    public List<Integer> getAscendingSequence(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getAscendingSequence(getStringFromFile(reader));
    }

    @ApiOperation("Возвращает самую длинную возрастающую последовательность среди чисел в файле")
    @PostMapping("/descending")
    public List<Integer> getDescendingSequence(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getDescendingSequence(getStringFromFile(reader));
    }

    private StringBuilder getStringFromFile(MultipartFile reader) throws IOException {
        Path uploadPath = Paths.get("reader.txt");
        StringBuilder stringBuilder = new StringBuilder();
        if (reader != null) {
            if (!Files.exists(uploadPath)) {
                Files.createFile(uploadPath);
            }
            reader.transferTo(uploadPath);
            try (FileReader fileReader = new FileReader(uploadPath.toFile())) {
                int character;
                while ((character = fileReader.read()) != -1) {
                    stringBuilder.append((char) character);
                }
            }
        }
        return stringBuilder;
    }
}
