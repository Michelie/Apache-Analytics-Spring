package com.handson.apache_analytics_spring.metrics;


import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.handson.apache_analytics_spring.utils.Utils.format2Dec;

@Service
public class OsMetric implements LogMetric {
    private final Map<String, Integer> osCount = new HashMap<>();
    private int totalRequests = 0;

    @Override
    public void processLogEntry(String ip, String userAgentString) {
        totalRequests++;
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        String os = userAgent.getOperatingSystem().getName() != null ? userAgent.getOperatingSystem().getName() : "Unknown";
        osCount.put(os, osCount.getOrDefault(os, 0) + 1);
    }

    @Override
    public List<StatData> getStats() {
        return osCount.entrySet().stream()
                .map(entry -> StatData.StatDataBuilder.aStatData()
                        .title(entry.getKey())
                        .aggr(format2Dec((entry.getValue() * 100.0) / totalRequests))
                        .build())
                .sorted((a, b) -> Double.compare(Double.parseDouble(b.getAggr().replace("%", "")),
                        Double.parseDouble(a.getAggr().replace("%", ""))))
                .collect(Collectors.toList());
    }

    @Override
    public void printResults() {
        System.out.println("Operating Systems:");
        getStats().forEach(stat -> System.out.printf("%s - %s\n", stat.getTitle(), stat.getAggr()));
    }

    @Override
    public String metricType() {
        return "Os";
    }
}