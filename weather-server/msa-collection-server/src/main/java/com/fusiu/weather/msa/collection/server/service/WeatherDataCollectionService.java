package com.fusiu.weather.msa.collection.server.service;

/**
 * 天气采集接口
 */
public interface WeatherDataCollectionService {

    void syncDataByCityId(String cityId);
}
