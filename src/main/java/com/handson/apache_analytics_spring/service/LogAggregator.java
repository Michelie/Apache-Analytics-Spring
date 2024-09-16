package com.handson.apache_analytics_spring.service;

import com.handson.apache_analytics_spring.metrics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogAggregator {

    private final List<LogMetric> metrics = new ArrayList<>();

    public LogAggregator(CountryMetric countryMetric, OsMetric osMetric, BrowserMetric browserMetric) {
        // Add the metrics to the aggregator
        metrics.add(countryMetric);
        metrics.add(osMetric);
        metrics.add(browserMetric);
    }

    public void addMetric(LogMetric metric) {
        metrics.add(metric);
    }

    public void addEntry(String ip, String userAgentString) {
        for (LogMetric metric : metrics) {
            metric.processLogEntry(ip, userAgentString);
        }
    }

    public Map<String, List<StatData>> getMetricStats() {
        Map<String, List<StatData>> allStats = new HashMap<>();
        for (LogMetric metric : metrics) {
            allStats.put(metric.metricType(), metric.getStats());
        }
        return allStats;
    }

    public List<StatData> getMetricStatsByType(String type) {
        for (LogMetric metric : metrics) {
            if (metric.metricType().equals(type)) {
                return metric.getStats();
            }
        }
        return new ArrayList<>();
    }

    public void printResultsByType(String type) {
        for (LogMetric metric : metrics) {
            if (metric.metricType().equalsIgnoreCase(type)) {
                metric.printResults();
                break;
            }
        }
    }
    public void printResults() {
        for (LogMetric metric : metrics) {
            metric.printResults();
        }
    }
}