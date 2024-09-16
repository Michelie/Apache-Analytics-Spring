package com.handson.apache_analytics_spring.metrics;

import org.springframework.stereotype.Service;
import eu.bitwalker.useragentutils.UserAgent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.handson.apache_analytics_spring.utils.Utils.format2Dec;

@Service
public class BrowserMetric implements LogMetric {
    private final Map<String, Integer> browserCount = new HashMap<>();
    private int totalRequests = 0;

    @Override
    public void processLogEntry(String ip, String userAgentString) {
        totalRequests++;
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        String browser = userAgent.getBrowser().getName() != null ? userAgent.getBrowser().getName() : "Unknown";
        browserCount.put(browser, browserCount.getOrDefault(browser, 0) + 1);
    }

    @Override
    public List<StatData> getStats() {
        return browserCount.entrySet().stream()
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
        System.out.println("Browsers:");
        getStats().forEach(stat -> System.out.printf("%s - %s\n", stat.getTitle(), stat.getAggr()));
    }

    @Override
    public String metricType() {
        return "Browser";
    }
}