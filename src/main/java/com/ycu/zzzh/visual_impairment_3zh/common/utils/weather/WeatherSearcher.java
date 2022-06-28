package com.ycu.zzzh.visual_impairment_3zh.common.utils.weather;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.HttpClientUtil;


/**
 * 根据城市查找所在城市天气
 */
public final class WeatherSearcher {

	/**
	 *  根据城市参数返回城市天气
	 * @param city 城市
	 */
	public static JSONObject getWeather(String city){
		String url = "http://wthrcdn.etouch.cn/weather_mini?city="+city;
		try {
			String s = HttpClientUtil.doGet(url);
			JSONObject object = JSONObject.parseObject(s);
			String status = object.get("status").toString();
			if ("1000".equals(status)){
				JSONObject data = (JSONObject) object.get("data");
				String city2 = data.getString("city");
				JSONArray forecast = data.getJSONArray("forecast");
				JSONObject jsonObject = (JSONObject) forecast.get(0);
				jsonObject.put("city",city2);
				String high = jsonObject.getString("high");
				String low = jsonObject.getString("low");
				String[] h = high.split(" ");
				String[] l = low.split(" ");
				jsonObject.remove("low");
				jsonObject.remove("high");
				jsonObject.put("high",h[1]);
				jsonObject.put("low",l[1]);
				return jsonObject;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
