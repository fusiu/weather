package com.fusiu.weather.report.service;

import com.fusiu.weather.report.vo.City;

import java.util.List;

public interface CityDataService {

    /**
     * 获取 city 列表
     * @return
     */
    List<City> listCity();
}
