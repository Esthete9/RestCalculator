package com.example.RestCalculator.controllers;

import com.example.RestCalculator.exceptions.FileErrorResponse;
import com.example.RestCalculator.exceptions.FileIsEmptyException;
import com.example.RestCalculator.exceptions.FileNotFoundNumbersException;
import com.example.RestCalculator.exceptions.NullPointerFileException;
import com.example.RestCalculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Api(tags = "Controller API")
@Tag(name = "Controller API", description = "Контроллер калькулятора")
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello world!!!";
    }

    @ApiOperation("Возвращает максимальное число в файле или файле бинарного вида")
    @PostMapping("/max")
    public int getMaxNumberInFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getMaxValue(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает минимальное число в файле или файле бинарного вида")
    @PostMapping("/min")
    public int getMinNumberInFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getMinValue(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает медиану среди чисел в файле или файле бинарного вида")
    @PostMapping("/median")
    public double getMedian(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getMedian(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает среднее арифметическое значение среди чисел в файле или файле бинарного вида")
    @PostMapping("/average")
    public double getAverage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getAverage(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает самую длинную убывающую последовательность среди чисел в файле или файле бинарного вида")
    @PostMapping("/ascending")
    public List<Integer> getAscendingSequence(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getAscendingSequence(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает самую длинную возрастающую последовательность среди чисел в файле или файле бинарного вида")
    @PostMapping("/descending")
    public List<Integer> getDescendingSequence(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) return calculatorService.getDescendingSequence(getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает максимальное число файле бинарного вида")
    @PostMapping("/binaryMax")
    public int getMaxNumberInFile(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getMaxValue(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает минимальное число в файле бинарного вида")
    @PostMapping("/binaryMin")
    public int getMinNumberInFile(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getMinValue(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает медиану среди чисел в файле бинарного вида")
    @PostMapping("/binaryMedian")
    public double getMedian(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getMedian(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает среднее арифметическое значение среди чисел в файле бинарного вида")
    @PostMapping("/binaryAverage")
    public double getAverage(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getAverage(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает самую длинную убывающую последовательность среди чисел в файле бинарного вида")
    @PostMapping("/binaryAscending")
    public List<Integer> getAscendingSequence(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getAscendingSequence(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает самую длинную возрастающую последовательность среди чисел в файле бинарного вида")
    @PostMapping("/binaryDescending")
    public List<Integer> getDescendingSequence(@RequestBody byte[] bytes) {
        if (bytes != null) return calculatorService.getDescendingSequence(getStringFromByteArr(bytes));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ExceptionHandler
    private ResponseEntity<FileErrorResponse> handleException(NullPointerFileException e) {
        FileErrorResponse response = new FileErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<FileErrorResponse> handleException(FileIsEmptyException e) {
        FileErrorResponse response = new FileErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<FileErrorResponse> handleException(FileNotFoundNumbersException e) {
        FileErrorResponse response = new FileErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private StringBuilder getStringFromByteArr(byte[] bytes) {
        return new StringBuilder(new String(bytes, StandardCharsets.UTF_8));
    }

    private StringBuilder getStringFromFile(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("file.txt");
        StringBuilder textFromFile = new StringBuilder();
        if (file != null) {
            if (!Files.exists(uploadPath)) {
                Files.createFile(uploadPath);
            }
            file.transferTo(uploadPath);
            try (FileReader fileReader = new FileReader(uploadPath.toFile())) {
                int character;
                while ((character = fileReader.read()) != -1) {
                    textFromFile.append((char) character);
                }
            }
        }
        if (textFromFile.toString().isEmpty() || textFromFile.toString().isBlank())
            throw new FileIsEmptyException("Отправлен пустой файл!");
        else
            return textFromFile;
    }
}
