package com.ycu.zzzh.visual_impairment_3zh.controller;

import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.weather.WeatherSearcher;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WeatherController
 * @description: 根据前端携带经纬度的请求返回当地城市天气
 * @Date 2022/6/13 20:25
 * @Version 1.0
 **/
@RestController
public class WeatherController {
    @SneakyThrows
    @RequestMapping("weather")
    public JSONObject getWeather(@RequestParam() String longitude, @RequestParam String latitude){
        return WeatherSearcher.getLocation(longitude,latitude);
    }
}
