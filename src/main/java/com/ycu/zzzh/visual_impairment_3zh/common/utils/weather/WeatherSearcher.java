package com.ycu.zzzh.visual_impairment_3zh.common.utils.weather;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycu.zzzh.visual_impairment_3zh.common.utils.HttpClientUtil;


/**
 * 根据经纬度查找所在城市天气
 */
public final class WeatherSearcher {
	/**
	 * 根据经纬度获取当地天气
	 * @param lon 经度
	 * @param lat 纬度
	 */
	public static JSONObject getLocation(String lon,String lat){
		String location = lat+","+lon;
		String baiduAK = "j8zgDgVBLdyQhpUPTIWBDsfYxnzYuEUr";
		String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak="+baiduAK+"&output=json&coordtype=wgs84ll&location="+location;
		try {
			String s = HttpClientUtil.doGet(url);
			JSONObject object = JSONObject.parseObject(s);
			String status = object.get("status").toString();
			if ("0".equals(status)){
				JSONObject result = (JSONObject) object.get("result");
				JSONObject addressComponent = (JSONObject) result.get("addressComponent");
				String city = (String) addressComponent.get("city");
				return getWeather(city);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *  根据城市参数返回城市天气
	 * @param city 城市
	 */
	private static JSONObject getWeather(String city){
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
				return jsonObject;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
