package com.zgw.base.util;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class JsonUtils {

	public static <T> T resultData(String key, String jsonString, Class<T> cls) {
		return JSON.parseObject(readjsonString(key, jsonString), cls);
	}

	public static <T> T resultData(String jsonString, Class<T> cls) {
		if (jsonString == null || "".equals(jsonString) ) {
			return null;
		}
		return JSONObject.parseObject(jsonString, cls);
	}

	public static <T> List<T> getList(String jsonString, Class<T> clszz) {
		return JSONArray.parseArray(jsonString, clszz);
	}

	public static <T> List<T> getList(String jsonString, Class<T> cls, String key) {
		return JSONArray.parseArray(readjsonString(key, jsonString), cls);
	}

	public static String readjsonString(String key, String jsonStringString) {
		return JSONObject.parseObject(jsonStringString).getString(key);
	}
	public static <T> Map<String, List<T>> getKeyMap(String successResult, Class<T> clszz) {
		Map<String, List<T>> map = new HashMap<String, List<T>>();
		try {
			JSONObject userJsonObj = JSONObject.parseObject(successResult);
			Set<String> setString=userJsonObj.keySet();
			for(String key:setString){
				map.put(key, getList(successResult, clszz, key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public static <T> List<T> getListFromKey(String successResult, Class<T> clszz) {
		List<T> list=new ArrayList<T>();
		try {
			JSONObject userJsonObj = JSONObject.parseObject(successResult);
			Set<String> setString=userJsonObj.keySet();
			for(String key:setString){
				list.addAll(getList(successResult, clszz, key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取嵌套的list
	 * @param successResult
	 * @param clszz
	 * @return
	 */
	public static <T> List<List<T>> getExpandList(String successResult, Class<T> clszz) {
		List<List<T>> list=new ArrayList<List<T>>();
		try {
			JSONArray array = JSONArray.parseArray(successResult);
			if (array != null) {
				for (int i = 0; i < array.size(); i++) {
					list.add(getList(array.getString(i), clszz));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static <T> List<T> getListFromKeyMap(String successResult, Class<T> clszz){
		List<T> list=new ArrayList<T>();
		 Map<String, List<T>> map=getKeyMap(successResult,clszz);
		 if(map!=null){
			 for(Map.Entry<String, List<T>> mapSet:map.entrySet()){
				 list.addAll(mapSet.getValue());
			 }
		 }
		 return list;
	}
	
	public static <T> List<T> paserAllFriends(String ectityResult, Class<T> clszz){
		List<T> allListUser=new ArrayList<T>();
		try {
			JSONObject jsonObject=JSONObject.parseObject(ectityResult);
			Set<String> key=jsonObject.keySet();
			for(String resultKey:key){
				List<T> listUser=getListFromKeyMap(readjsonString(resultKey, ectityResult), clszz);
				allListUser.addAll(listUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allListUser;
	}
	public static <T> Map<String, T> getMap(String jsonString, Class<T> clszz, String... keys) {
		Map<String, T> map = new HashMap<String, T>();
		try {
			JSONObject jObject = JSONObject.parseObject(jsonString);
			for (String key : keys) {
				map.put(key, jObject.getObject(key, clszz));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static <T> LinkedHashMap<String, T> getMapFromKey(String jsonString, Class<T> clszz, String... keys ) {
		LinkedHashMap<String, T> map = new LinkedHashMap<String, T>();
		try {
			if(TextUtils.isEmpty(jsonString)) return map;
			JSONArray jsonArray = JSONObject.parseArray(jsonString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jObject = jsonArray.getJSONObject(i);
				for (String key : keys) {
					if (jObject.containsKey(key)) {
						map.put(key, resultData(jObject.getString(key), clszz));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 
	 * @param jsonString
	 * @param clszz
	 * @param lotnoMap
	 * @return
	 */
	public static <T> Map<String, T> getMap(String jsonString, Class<T> clszz, Map<String, String> lotnoMap) {
		Map<String, T> map = new LinkedHashMap<String, T>();
		JSONArray jsonArray = JSONObject.parseArray(jsonString);
		if(jsonArray == null) {
			return null;
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jObject = jsonArray.getJSONObject(i);
			for (String lotno : lotnoMap.keySet()) {
				if (jObject.containsKey(lotno)) {
					map.put(lotno, resultData(jObject.getString(lotno), clszz));
					break;
				}
			}
		}
		
		return map;
	}


	public static Map<String, String> getKeyMap(String successResult) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject userJsonObj = JSONObject.parseObject(successResult);
			Set<String> setString=userJsonObj.keySet();
			for(String key : setString){
//				String[] strings = null;
//				if (userJsonObj.get(key) != null) {
//					strings = userJsonObj.get(key).toString().split(",");
//				}
				String values = userJsonObj.get(key).toString();
				map.put(key, values);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
