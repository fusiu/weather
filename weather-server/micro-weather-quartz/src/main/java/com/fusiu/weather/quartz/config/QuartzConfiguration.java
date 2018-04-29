package com.fusiu.weather.quartz.config;

import com.fusiu.weather.quartz.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Configuration
 */
@Configuration
public class QuartzConfiguration {

    // JobDetail
    @Bean
    public JobDetail weatherDataSyncJobJobDetail(){
        return JobBuilder.newJob(WeatherDataSyncJob.class)
                // 可以自定义一个名字
                .withIdentity("weatherDataSyncJob")
                .storeDurably().build();
    }
    // Trigger 用于触发的 Bean
    @Bean
    public Trigger weatherDataSyncJobTrigger(){

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(2).repeatForever();
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
