package com.fusiu.weather.basic.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusiu.weather.basic.service.WeatherDataService;
import com.fusiu.weather.basic.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * WeatherDataService 实现类
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI+"citykey="+cityId;
        return this.doGetWeather(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI+"city="+cityName;
        return this.doGetWeather(uri);
    }

    /**
     * 方法抽取，用于从天气接口获取数据
     * @param uri
     * @return
     */
    private WeatherResponse doGetWeather(String uri){

         // 获取天气数据
        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        // 将天气数据从 ResponseEntity 对象转换成 WeatherResponse 对象
        WeatherResponse weatherResponse = null;
        ObjectMapper mapper = new ObjectMapper();
        String strBody = null;
        if (forEntity.getStatusCodeValue() == 200){
            strBody = forEntity.getBody();
        }
        try {
            weatherResponse = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(weatherResponse.getData());
        return weatherResponse;
    }
}
