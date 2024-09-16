package com.handson.apache_analytics_spring;

import com.handson.apache_analytics_spring.metrics.BrowserMetric;
import com.handson.apache_analytics_spring.metrics.CountryMetric;
import com.handson.apache_analytics_spring.metrics.OsMetric;
import com.handson.apache_analytics_spring.metrics.StatData;
import com.handson.apache_analytics_spring.service.GeoIPService;
import com.handson.apache_analytics_spring.service.LogAggregator;
import com.handson.apache_analytics_spring.service.LogFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.handson.apache_analytics_spring.metrics.StatData.StatDataBuilder.aStatData;
import static com.handson.apache_analytics_spring.metrics.LogMetric.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LogFileParserTest {

    @Autowired
    private LogFileParser logFileParser;

    @Autowired
    private LogAggregator aggregator;

    @Autowired
    private GeoIPService geoIPService;

    private Path logFilePath;

    @BeforeEach
    void setUp() throws IOException {
        logFilePath = Paths.get("src/test/resources/testLogs.log");
        aggregator = new LogAggregator(new CountryMetric(geoIPService), new OsMetric(), new BrowserMetric());
    }


    @Test
    void testCountryMetric() throws IOException {
        logFileParser.processLogFile(logFilePath, aggregator);

        assertEquals( aggregator.getMetricStatsByType(METRIC_TYPE_COUNTRY),
                List.of(
                        aStatData().title("Norway").aggr("33.33%").build(),
                        aStatData().title("United States").aggr("33.33%").build(),
                        StatData.StatDataBuilder.aStatData().title("Brazil").aggr("33.33%").build()
                ));
    }

    @Test
    void testOsMetric() throws IOException {
        logFileParser.processLogFile(logFilePath, aggregator);

        assertEquals(aggregator.getMetricStatsByType(METRIC_TYPE_OS),
                List.of(
                        aStatData().title("Windows Vista").aggr("33.33%").build(),
                        aStatData().title("iOS 6 (iPad)").aggr("33.33%").build(),
                        aStatData().title("Windows 98").aggr("33.33%").build()
                ));
    }

    @Test
    void testBrowserMetric() throws IOException {
        logFileParser.processLogFile(logFilePath, aggregator);

        assertEquals(aggregator.getMetricStatsByType(METRIC_TYPE_BROWSER),
                List.of(
                        aStatData().title("Internet Explorer 7").aggr("33.33%").build(),
                        aStatData().title("Internet Explorer 6").aggr("33.33%").build(),
                        aStatData().title("Mobile Safari").aggr("33.33%").build()
                ));
    }
}