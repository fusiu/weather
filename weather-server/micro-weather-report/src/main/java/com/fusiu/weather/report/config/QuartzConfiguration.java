package com.fusiu.weather.report.config;

import com.fusiu.weather.report.job.WeatherDataSyncJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz Configuration
 */
@Configuration
public class QuartzConfiguration {
    // 拉取天气的时间间隔
    private static final int TIME = 1800;

    // JobDetail 用来定义一个特定的 job
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
                .withIntervalInSeconds(TIME).repeatForever();
        return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobJobDetail())
                .withIdentity("weatherDataSyncTrigger")
                .withSchedule(scheduleBuilder).build();
    }
}
