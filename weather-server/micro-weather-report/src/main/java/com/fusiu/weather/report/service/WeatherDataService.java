package com.fusiu.weather.report.service;


import com.fusiu.weather.report.vo.WeatherResponse;

/**
 * 天气数据接口
 */
public interface WeatherDataService {

    /**
     * 根据城市 ID 查询天气数据
     * @param cityId
     * @return
     */
    WeatherResponse getDataByCityId(String cityId);

    /**
     * 根据城市名称查询天气数据
     * @param cityName
     * @return
     */
    WeatherResponse getDataByCityName(String cityName);

    /**
     * 根据城市 ID 同步天气
     * @param cityId
     */
    void syncDataByCityId(String cityId);
}
