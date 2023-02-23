package com.example.RestCalculator.Controllers;

import com.example.RestCalculator.service.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
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
    public int calculation(@RequestParam("reader") MultipartFile reader) throws IOException {
        calculatorService.getListNumbersFromFile(getStringFromFile(reader));
        return 1;
    }

    @PostMapping("/min")
    public int calculationa(@RequestParam("reader") MultipartFile reader) throws IOException {
        return 1;
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
