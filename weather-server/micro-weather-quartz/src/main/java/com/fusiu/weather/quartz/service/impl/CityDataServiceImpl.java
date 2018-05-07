package com.fusiu.weather.report.service.impl;

import com.fusiu.weather.report.service.CityDataService;
import com.fusiu.weather.report.utils.XmlBuilder;
import com.fusiu.weather.report.vo.City;
import com.fusiu.weather.report.vo.CityList;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CityDataServiceImpl implements CityDataService{
    @Override
    public List<City> listCity() {
        // 读取 XML 文件
        Resource resource = new ClassPathResource("citylist.xml");
        List<City> list= null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(),"utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null){
                sb.append(line);
            }

            // XML 转为 Java 对象
            CityList cityList = (CityList) XmlBuilder.xmlStrToObject(CityList.class,sb.toString());
            if (cityList != null){
                list = cityList.getCityList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
