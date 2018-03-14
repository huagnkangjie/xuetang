package com.ziyue.xuetang.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanMapUtil {
	public static <T> T Map2Bean(Class<T> type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {

		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		T obj = type.newInstance();

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		for (int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (!(map.containsKey(propertyName)))
				continue;
			try {
				Object value = map.get(propertyName);
				BeanUtils.setProperty(obj, propertyName, value);
			} catch (Exception e) {
			}
		}
		return obj;
	}

	public static Map bean2Map(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; ++i) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!(propertyName.equals("class"))) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null)
					returnMap.put(propertyName, result);
				else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	public static class Test {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {

			return super.toString();
		}

	}

	public static void main(String[] args) {

		Map<String, Object> map = new HashMap<>();

		map.put("name", "22");

		try {

			Test t = Map2Bean(Test.class, map);

			System.out.println(t.getName());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}