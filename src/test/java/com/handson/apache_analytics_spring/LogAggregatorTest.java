package com.handson.apache_analytics_spring;

import com.handson.apache_analytics_spring.metrics.CountryMetric;
import com.handson.apache_analytics_spring.metrics.LogMetric;
import com.handson.apache_analytics_spring.service.LogAggregator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LogAggregatorTest {

    @Autowired
    private LogAggregator aggregator;

    @MockBean
    private CountryMetric countryMetric;

    @Test
    void testAddEntry() {
        String ip = "180.76.5.53";
        String userAgent = "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)";

        aggregator.addEntry(ip, userAgent);
        verify(countryMetric, times(1)).processLogEntry(ip, userAgent);
    }

    @Test
    void testPrintResults() {
        aggregator.printResults();
        verify(countryMetric, times(1)).printResults();
    }
}
