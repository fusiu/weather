package com.fusiu.weather.report.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusiu.weather.report.service.WeatherDataService;
import com.fusiu.weather.report.vo.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 * WeatherDataService 实现类
 *
 */
@Service
public class WeatherDataServiceImpl implements WeatherDataService {
    private static final String WEATHER_URI = "http://wthrcdn.etouch.cn/weather_mini?";

    private static final long TIME_OUT = 1800; //

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WeatherResponse getDataByCityId(String cityId) {
        String uri = WEATHER_URI + "citykey=" + cityId;
        return this.doGetWeather(uri);
    }

    @Override
    public WeatherResponse getDataByCityName(String cityName) {
        String uri = WEATHER_URI + "city=" + cityName;
        return this.doGetWeather(uri);
    }

    @Override
    public void syncDataByCityId(String cityId) {

        String uri = WEATHER_URI+"citykey="+cityId;
        saveWeatherData(uri);
    }

    private void saveWeatherData(String uri) {
        String key = uri;
        String strBody = null;
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;

        // 先查缓存，缓存有的取缓存中的数据
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
        if (forEntity.getStatusCodeValue() == 200) {
            strBody = forEntity.getBody();
        }
        // 将数据写入缓存中
        System.out.println("=========== 将数据写入缓存 ==============");
        ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
    }

    /**
     * 方法抽取，
     *
     * @param uri
     * @return
     */
    private WeatherResponse doGetWeather(String uri) {

        String key = uri;
        String strBody = null;
        // ObjectMapper 可以实现将 JSON 格式的数据转换成 bean
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse weatherResponse = null;

        // 先查缓存，缓存有的取缓存中的数据
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        if (stringRedisTemplate.hasKey(uri)) {
            System.out.println("========== 缓存中有数据，直接获取 ===========");
            strBody = ops.get(key);
        } else {
            System.out.println("============  没有缓存数据 ================");
            // 缓存没有，在调用服务接口来获取
            // 获取天气数据
            ResponseEntity<String> forEntity = restTemplate.getForEntity(uri, String.class);
            if (forEntity.getStatusCodeValue() == 200) {
                strBody = forEntity.getBody();
            }
            // 将数据写入缓存中
            System.out.println("=========== 将数据写入缓存 ==============");
            ops.set(key, strBody, TIME_OUT, TimeUnit.SECONDS);
        }
        try {
            // 将天气数据从 ResponseEntity 对象转换成 WeatherResponse 对象
            weatherResponse = mapper.readValue(strBody, WeatherResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherResponse;
    }
}
