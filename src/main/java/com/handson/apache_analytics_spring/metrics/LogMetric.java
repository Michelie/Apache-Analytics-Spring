package com.handson.apache_analytics_spring.metrics;

import java.util.List;

public interface LogMetric {
    String metricType();
    void processLogEntry(String ip, String userAgentString);
    void printResults();
    List<StatData> getStats();
}