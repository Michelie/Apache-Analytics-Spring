package com.handson.apache_analytics_spring.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;

@Service
public class GeoIPService {
    private final DatabaseReader dbReader;

    public GeoIPService(@Value("${geoip.db.path}") Resource dbResource) throws IOException {
        this.dbReader = new DatabaseReader.Builder(dbResource.getFile()).build();
    }

    public String getCountryFromIp(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse response = dbReader.country(ipAddress);
            return response.getCountry().getName();
        } catch (AddressNotFoundException e) {
            return "Unknown";
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
