package com.ziyue.xuetang.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * json工具类
 * 
 * @author  songhuabiao
 * @version  [版本号, 2014年5月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JacksonUtil
{
    private static ObjectMapper objectMapper = null;
    
    /**
     * json转obj
     * <一句话功能简述>
     * <功能详细描述>
     * @param json
     * @param cls
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException [参数说明]
     * 
     * @return Object [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object jsonToObj(String json, Class cls)
            throws InstantiationException, IllegalAccessException,
            JsonParseException, JsonMappingException, IOException
    {
        objectMapper = new ObjectMapper();
        Object obj = objectMapper.readValue(json, cls);
        return obj;
    }
    
    /**
     * obj转json 忽略null
     * <一句话功能简述>
     * <功能详细描述>
     * @param obj
     * @return
     * @throws IOException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    public static String objToJsonAlways(Object obj) throws IOException
    {
        objectMapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gener;
        gener = new JsonFactory().createJsonGenerator(sw);
        objectMapper.setSerializationInclusion(Include.ALWAYS);
        objectMapper.writeValue(gener, obj);
        gener.close();
        String json = sw.toString();
        return json;
    }
    /**
     * obj转json 
     * <一句话功能简述>
     * <功能详细描述>
     * @param obj
     * @return
     * @throws IOException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    public static String objToJson(Object obj) throws IOException
    {
    	objectMapper = new ObjectMapper();
    	StringWriter sw = new StringWriter();
    	JsonGenerator gener;
    	gener = new JsonFactory().createJsonGenerator(sw);
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	objectMapper.writeValue(gener, obj);
    	gener.close();
    	String json = sw.toString();
    	return json;
    }
    
    /**
     * map转化为json
     * <功能详细描述>
     * @param map
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public static String mapToJson(Map map)
    {
        String json = "{\"errorCode\":1,\"description\":\"服务器内部异常\"}";
        objectMapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        JsonGenerator gener;
        try
        {
            gener = new JsonFactory().createJsonGenerator(sw);
            objectMapper.writeValue(gener, map);
            gener.close();
            json = sw.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return json;
    }
    
    /**
     * json转化为map
     * @param json
     * @return [参数说明]
     * 
     * @return Map<String,Object> [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "unchecked" })
    public static Map<String, Object> jsonToMap(String json)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            map = mapper.readValue(json, Map.class);
            
            for (String s : map.keySet())
            {
                String value = map.get(s)+"";
                if(!value.equals("null")){
                    String type = map.get(s).getClass().getSimpleName();
                    //将integer类型的转化为String
                    if (type.equals("Integer"))
                    {
                        map.put(s, ((Integer) map.get(s)).toString());
                    }
                    //将long类型的转化为String
                    if (type.equals("Long"))
                    {
                        map.put(s, ((Long) map.get(s)).toString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            map = new HashMap<String, Object>();
            map.put("jsonError", "消息格式错误");
        }
        return map;
    }
    
    /**
     * 往json字串里添加属性
     * @param jsonStr
     * @param params
     * @param values
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    static Logger logger = Logger.getLogger(JacksonUtil.class);
    
    public static String putJson(String jsonStr, String[] params,
            String[] values)
    {
        if (jsonStr.equals(""))
            jsonStr = "{}";
        try
        {
            JSONObject obj = JSONObject.fromObject(jsonStr);
            if (params != null)
            {
                if (params.length != values.length)
                {
                    obj.put("errorCode", 01);
                    obj.put("description", "服务器内部错误");
                    return obj.toString();
                }
                for (int i = 0; i < params.length; i++)
                {
                    if (obj.containsKey(params[i]))//如果存在属性，则不覆盖
                        continue;
                    if (params[i].equals("version")
                            || params[i].equals("errorCode"))
                    {
                        int val = 0;
                        try
                        {
                            //兼容16进制字符串
                            if (values[i].indexOf("0x") > -1)
                            {
                                val = Integer.parseInt(values[i].replace("0x",
                                        ""), 16);
                            }
                            else
                            {
                                val = Integer.parseInt(values[i]);
                            }
                            obj.put(params[i], val);
                        }
                        catch (Exception e)
                        {
                            obj.put(params[i], values[i]);
                        }
                    }
                    else
                    {
                        obj.put(params[i], values[i]);
                    }
                }
            }
            return obj.toString();
        }
        catch (Exception e)
        {
            logger.error("参数json为：" + jsonStr + "params.length="
                    + params.length + "||value.length=" + values.length);
            return "{\"errorCode\"：2,\"description\"：\"数据格式错误\"}";
        }
    }
    
    /**
     * 往json字串里删除属性
     * <功能详细描述>
     * @param jsonStr
     * @param params
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String removeJson(String jsonStr, String... params)
    {
        if (jsonStr.equals(""))
            jsonStr = "{}";
        try
        {
            JSONObject obj = JSONObject.fromObject(jsonStr);
            for (int i = 0; i < params.length; i++)
            {
                obj.remove(params[i]);
            }
            return obj.toString();
        }
        catch (Exception e)
        {
            return "{\"errorCode\"：2,\"description\"：\"数据格式错误\"}";
        }
    }
    
    /**
     * 获取单个json属性的值
     * <功能详细描述>
     * @param jsonStr
     * @param param
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getJsonValue(String jsonStr, String param)
    {
        if (jsonStr.equals(""))
            jsonStr = "{}";
        String value = "";
        try
        {
            JSONObject obj = JSONObject.fromObject(jsonStr);
            value = obj.get(param).toString();
        }
        catch (Exception e)
        {
            //TODO
        }
        return value;
    }
    
    //////////////////////////////////////////////////////
    //////////////// 新添加方法     /////////////////////////////
    
    public static String object2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double || obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	public static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(bean));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String list2json(List<?> list) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String set2json(Set<?> set) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将Map与Bean匹配的属性传入Bean中
	 * @param map 传入参数集合
	 * @param beanName	要转化的JavaBean名称
	 * @return Object
	 */
	public static Object mapToBean(Map<String, Object> map, Class cls) {
		try {
			//获取Bean中所有属性
			Field[] f = cls.getDeclaredFields();
			//创建Bean实例
			Object obj = cls.newInstance();
			//属性匹配
			for (Field field : f) {
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Entry) it.next();
					String key = (String) entry.getKey();
					String str = field.getName();
					if (key.equals(str)) {
						str = str.substring(0, 1).toUpperCase() + str.substring(1);
						//获取set方法
						Method method = cls.getMethod("set" + str, new Class[]{field.getType()});
						//执行set方法
						method.invoke(obj, entry.getValue());
					}
				}
			}
			return obj;
		} catch (Exception e) {
			logger.error("mapToBean转换失败", e);
		}
		return null;
	}
    
    public static void main(String[] args)
    {
        System.out.println(removeJson("{\"name\":\"shb\",\"age\":\"21\"}",
                "name",
                "age"));
        //        System.out.println(getJsonValue("{\"name\":\"shb\",\"age\":\"21\"}","name"));
        //        \"name\":\"shb\",\"age\":\"21\"}
        //        String initjson = putJson("",new String[] { "errorCode",
        //                "description", "msgType", "version", "msgSeq" },
        //                new String[] { "02", "服务器内部错误", "COMMON", "1", "1" });
        //        System.out.println(initjson);
    }
}
