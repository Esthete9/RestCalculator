package com.example.RestCalculator.controllers;

import com.example.RestCalculator.exceptions.FileErrorResponse;
import com.example.RestCalculator.exceptions.NullPointerFileException;
import com.example.RestCalculator.service.CalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Api(tags = "Controller API")
@Tag(name = "Controller API", description = "Контроллер калькулятора")
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Value("${NullPointerFileException}")
    private String nullPointerFileExceptionMsg;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @ApiOperation("Возвращает максимальное число в файле или файле бинарного вида")
    @PostMapping("/max")
    public int getMaxNumberInFile(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getMaxValue(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException("Файл не был доставлен!");
    }

    @ApiOperation("Возвращает минимальное число в файле или файле бинарного вида")
    @PostMapping("/min")
    public int getMinNumberInFile(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getMinValue(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает медиану среди чисел в файле или файле бинарного вида")
    @PostMapping("/median")
    public double getMedian(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getMedian(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает среднее арифметическое значение среди чисел в файле или файле бинарного вида")
    @PostMapping("/average")
    public double getAverage(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getAverage(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает самую длинную убывающую последовательность среди чисел в файле или файле бинарного вида")
    @PostMapping("/ascending")
    public List<Integer> getAscendingSequence(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getAscendingSequence(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает самую длинную возрастающую последовательность среди чисел в файле или файле бинарного вида")
    @PostMapping("/descending")
    public List<Integer> getDescendingSequence(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        if (file != null) return calculatorService.getDescendingSequence(calculatorService.getStringFromFile(file));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает максимальное число файле бинарного вида")
    @PostMapping("/binaryMax")
    public int getMaxNumberInFile(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getMaxValue(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает минимальное число в файле бинарного вида")
    @PostMapping("/binaryMin")
    public int getMinNumberInFile(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getMinValue(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает медиану среди чисел в файле бинарного вида")
    @PostMapping("/binaryMedian")
    public double getMedian(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getMedian(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает среднее арифметическое значение среди чисел в файле бинарного вида")
    @PostMapping("/binaryAverage")
    public double getAverage(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getAverage(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает самую длинную убывающую последовательность среди чисел в файле бинарного вида")
    @PostMapping("/binaryAscending")
    public List<Integer> getAscendingSequence(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getAscendingSequence(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ApiOperation("Возвращает самую длинную возрастающую последовательность среди чисел в файле бинарного вида")
    @PostMapping("/binaryDescending")
    public List<Integer> getDescendingSequence(@RequestBody byte[] bytes) throws InterruptedException {
        if (bytes != null) return calculatorService.getDescendingSequence(calculatorService.getStringFromByteArr(bytes));
        else throw new NullPointerFileException(nullPointerFileExceptionMsg);
    }

    @ExceptionHandler
    private ResponseEntity<FileErrorResponse> handleException(RuntimeException e) {
        FileErrorResponse response = new FileErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
