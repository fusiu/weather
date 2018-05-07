package com.fusiu.weather.report.service;

import com.fusiu.weather.report.vo.Weather;

public interface WeatherReportService {

    Weather getDataByCityId(String cityId);
}
