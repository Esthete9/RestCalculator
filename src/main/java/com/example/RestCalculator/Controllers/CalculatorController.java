package com.example.RestCalculator.Controllers;

import com.example.RestCalculator.service.CalculatorService;
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
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping
    public String test() {
        return "Hello world!!!";
    }

    @PostMapping("/max")
    public int getMaxNumberInFile(@RequestParam("reader") MultipartFile reader) throws IOException {
        if (reader != null)
            return calculatorService.getMaxValue(getStringFromFile(reader));
        else return 0;

    }

    @PostMapping("/min")
    public int getMinNumberInFile(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getMinValue(getStringFromFile(reader));
    }

    @PostMapping("/median")
    public double getMedian(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getMedian(getStringFromFile(reader));
    }

    @PostMapping("/average")
    public double getAverage(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getAverage(getStringFromFile(reader));
    }

    @PostMapping("/ascending")
    public List<Integer> getAscendingSequence(@RequestParam("reader") MultipartFile reader) throws IOException {
        return calculatorService.getAscendingSequence(getStringFromFile(reader));
    }

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
