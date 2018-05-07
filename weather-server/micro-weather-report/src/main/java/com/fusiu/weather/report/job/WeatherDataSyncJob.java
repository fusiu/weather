package com.fusiu.weather.report.job;

import com.fusiu.weather.report.service.CityDataService;
import com.fusiu.weather.report.service.WeatherDataService;
import com.fusiu.weather.report.vo.City;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 天气数据同步
 */
public class WeatherDataSyncJob extends QuartzJobBean{

    @Autowired
    private CityDataService cityDataService;

    @Autowired
    private WeatherDataService weatherDataService;

    /**
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("======== 拉取天气信息 =============");
        // 获取城市 ID 列表
        List<City> cityList = null;
        cityList = cityDataService.listCity();
        // 遍历城市 ID ，获取天气
        if (cityList != null){
            for (City city : cityList){
                String cityId = city.getCityId();
                System.out.println("========== "+cityId+" ==================");
                weatherDataService.syncDataByCityId(cityId);
            }
        }
    }
}
