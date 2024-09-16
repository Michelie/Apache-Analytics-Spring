package com.handson.apache_analytics_spring.metrics;

import com.handson.apache_analytics_spring.service.GeoIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.handson.apache_analytics_spring.utils.Utils.format2Dec;

@Service
public class CountryMetric implements LogMetric {

    @Autowired
    private final GeoIPService geoIPService;
    private final Map<String, Integer> countryCount = new HashMap<>();
    private int totalRequests = 0;

    public CountryMetric(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @Override
    public void processLogEntry(String ip, String userAgentString) {
        totalRequests++;
        String country = geoIPService.getCountryFromIp(ip);
        countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
    }

    @Override
    public List<StatData> getStats() {
        return countryCount.entrySet().stream()
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
        System.out.println("Countries:");
        getStats().forEach(stat -> System.out.printf("%s - %s\n", stat.getTitle(), stat.getAggr()));
    }

    @Override
    public String metricType() {
        return "Country";
    }
}