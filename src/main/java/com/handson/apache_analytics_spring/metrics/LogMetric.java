package com.handson.apache_analytics_spring.metrics;

import java.util.List;

public interface LogMetric {
    public static final String METRIC_TYPE_COUNTRY = "Country";
    public static final String METRIC_TYPE_OS = "Os";
    public static final String METRIC_TYPE_BROWSER = "Browser";

    String metricType();
    void processLogEntry(String ip, String userAgentString);
    void printResults();
    List<StatData> getStats();
}