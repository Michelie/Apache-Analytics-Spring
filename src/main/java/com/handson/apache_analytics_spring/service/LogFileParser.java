package com.handson.apache_analytics_spring.service;

import com.handson.apache_analytics_spring.metrics.StatData;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogFileParser {

    private static final String LOG_PATTERN =
            "^(\\S+) \\S+ \\S+ \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\S+) \"(.*?)\" \"(.*?)\".*";

    public Map<String, List<StatData>> processLogFile(Path logFilePath, LogAggregator aggregator) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(logFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, aggregator);
            }
        }
        return aggregator.getMetricStats();
    }

    private void processLine(String logLine, LogAggregator aggregator) {
        Pattern pattern = Pattern.compile(LOG_PATTERN);
        Matcher matcher = pattern.matcher(logLine);
        if (matcher.matches()) {
            String ip = matcher.group(1); // IP address
            String userAgentString = matcher.group(9); // User-agent string
            aggregator.addEntry(ip, userAgentString);
        } else {
            System.err.println("Line doesn't match the expected format: " + logLine);
        }
    }
}
