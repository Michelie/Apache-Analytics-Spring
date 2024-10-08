package com.handson.apache_analytics_spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handson.apache_analytics_spring.metrics.LogMetric;
import com.handson.apache_analytics_spring.metrics.StatData;
import com.handson.apache_analytics_spring.service.LogAggregator;
import com.handson.apache_analytics_spring.service.LogFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private LogAggregator logAggregator;

    @Autowired
    private LogFileParser logFileParser;
    @Value("${log.file.directory}")
    private String logFileDirectory;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadLogFile(@RequestParam("file") MultipartFile file) {
        try {
            Path logDirectory = Path.of(logFileDirectory);
            if (!Files.exists(logDirectory)) {
                Files.createDirectories(logDirectory);
            }
            Path logFilePath = logDirectory.resolve(file.getOriginalFilename());
            Files.write(logFilePath, file.getBytes());
            logFileParser.processLogFile(logFilePath, logAggregator);
            return ResponseEntity.status(HttpStatus.OK).body("Log file processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process log file.");
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, List<StatData>>> getAllStats() {
        Map<String, List<StatData>> stats = logAggregator.getMetricStats();

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/stats/{type}")
    public ResponseEntity<List<StatData>> getStatsByType(@PathVariable String type) {
        List<StatData> stats  = logAggregator.getMetricStatsByType(type);
        if (!stats .isEmpty()) {
            logAggregator.printResultsByType(type);
        }

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/print")
    public ResponseEntity<String> printStatsToConsole() {
        logAggregator.printResults();
        return ResponseEntity.status(HttpStatus.OK).body("Stats printed to console.");
    }
}