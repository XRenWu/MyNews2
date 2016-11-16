/**
 * 
 */
package com.l000phone.util;

import com.l000phone.entity.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description：json格式的数据解析工具类<br/>
 * Copyright (c) , 2016, Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:ParseJSONStrUtils.java <br/>
 * Date:2016-9-20
 * 
 * @author 徐文波
 * @version : 1.0
 */
public class ParseJSONStrUtils {

	/**
	 * 解析json格式的数据
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static LinkedList<NewsItem> parseJsonStrToEntiy(String jsonStr) {
		try {
			JSONArray array = new JSONArray(jsonStr);

			LinkedList<NewsItem> entities = new LinkedList<>();
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				
				String name = obj.getString("name");
				String image_url = obj.getString("image_url");
				String title = obj.getString("title");
				long updated_at = obj.getLong("updated_at");
				
				
				//entities.add(new LuYouEntity(name, image_url, title, updated_at));
				
			}
			
			return entities;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
