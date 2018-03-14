package com.ziyue.xuetang.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.WebRequest;

import com.ziyue.xuetang.common.RequestMap;
import com.ziyue.xuetang.constant.Resource;

public class DataUtil {

	/**
	 * 把js对象转成map
	 * 
	 * @param obj
	 * @return
	 */
	public static RequestMap<String, Object> convert2Map(Object obj) {
		RequestMap<String, Object> result = new RequestMap<String, Object>();
		
		if(obj==null){
		return 	result;
		}

		if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Iterator<?> iter = map.keySet().iterator();

			while (iter.hasNext()) {
				String key = String.valueOf(iter.next());
				Object value = map.get(key);

				if (value == null||Resource.SCRIPT_VALUE_NULL.equals(value.toString().toLowerCase())) {

					result.put(key, "");
					continue;
				}

				if (value instanceof String) {
					String val = String.valueOf(map.get(key));

					if (StringUtils.isNotEmpty(val) && !Resource.SCRIPT_VALUE_NULL.equals(val.toLowerCase())) {
						result.put(key, map.get(key));
					}

				} else if (value instanceof HashMap) {
					result.put(key, value);
				} else if (value instanceof Integer || value instanceof Boolean || value instanceof Double||value instanceof Float
						|| value instanceof Date || value instanceof Long || value instanceof Short
						|| value instanceof Byte || value instanceof Enum) {
					result.put(key, value);

				} else if (value instanceof List) {
					result.put(key, value);

				} else {
					Map<String, Object> rt = (Map<String, Object>) JsonUtil.objToMap(value);
					result.put(key, rt);
				}
			}
		} else {
			result.putAll((Map<String, Object>) JsonUtil.objToMap(obj));
		}
		return result;
	}

	/**
	 * 转换请求并剔除公用参
	 * 
	 * @param webRequest
	 * @return
	 */
	public static RequestMap<String, Object> convertWebRequest2Map(WebRequest webRequest) {
		RequestMap<String, Object> request = new RequestMap<String, Object>();
		Iterator<String> iter = webRequest.getParameterNames();
		while (iter.hasNext()) {
			String name = iter.next();
			String value = webRequest.getParameter(name);
			// if (Resource.RESP_CALLBACK.equalsIgnoreCase(name)) {
			// continue;
			// }
			/*
			 * else if(Resource.REQ_ACCESS_TOKEN.equalsIgnoreCase(name)){
			 * continue; }
			 */
			if (StringUtils.isNotBlank(value)) {
				request.put(name, value);
			}
		}
		return request;
	}

	/**
	 * 剔除公用参数
	 * 
	 * @param request
	 * @return
	 */
	public static RequestMap<String, Object> convertWebRequest(Map<String, Object> request) {
		RequestMap<String, Object> requestMap = new RequestMap<String, Object>();
		requestMap.putAll(request);
		return requestMap;
	}

	public static Map<String, Object> hiddenRequestField(Map<String, Object> map, List<String> hiddenFields) {
		if (hiddenFields != null && map != null) {
			for (String field : hiddenFields) {
				if (map.containsKey(field)) {
					map.remove(field);
				}
			}
		}
		return map;
	}

}
