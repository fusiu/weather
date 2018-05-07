package com.fusiu.weather.report.service.impl;

import com.fusiu.weather.report.service.WeatherDataService;
import com.fusiu.weather.report.service.WeatherReportService;
import com.fusiu.weather.report.vo.Weather;
import com.fusiu.weather.report.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherReportServiceImpl implements WeatherReportService{

    @Autowired
    private WeatherDataService weatherDataService;
    @Override
    public Weather getDataByCityId(String cityId) {
        WeatherResponse dataByCityId = weatherDataService.getDataByCityId(cityId);
        return dataByCityId.getData();
    }
}
