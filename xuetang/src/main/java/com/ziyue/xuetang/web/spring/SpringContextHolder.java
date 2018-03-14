 
package com.ziyue.xuetang.web.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * <p>
 * Spring 工具类 ，获取Spring容器中的上下文信息
 * </p>
 * <p>
 *  使用的时候需要注入 ：
 *  <br>
 *  <!-- 获取ApplicationContext上下文 -->
 *	<bean id="springContextHolder" class="com.kaipin.api.web.spring.SpringContextHolder" />
 * </p>
 
 */
public class SpringContextHolder implements ApplicationContextAware {
@Autowired
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() throws Exception {
		checkApplicationContext();
		return context;
	}

	public static <T> T getBean(Class<T> clazz) throws Exception {
		checkApplicationContext();
		return context.getBean(clazz);
	}
	
	
	
//	public static  String getMessage(String key,Object[] args) throws Exception {
//		checkApplicationContext();
//
//		return getMessage(key, args, Locale.CHINA);
//	}
//	
//	public static  String getMessage(String key,Object[] args, Locale l) throws Exception {
//		checkApplicationContext();
//
//		
//		if(l==null){
//			throw new Exception("Locale is null");
//		}
//		return context.getMessage(key, args, l);
//	}
//	
	
	public static <T> T getBean(String beanName,Class<T> clazz) throws Exception {
		checkApplicationContext();
		return context.getBean(beanName,clazz);
	}
	

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
 
		context = ac;
	}

 
	
	private static void checkApplicationContext() throws Exception {
		if (context == null) {
			throw new  Exception("applicaitonContext未注入,请定义SpringContextHolder");
		}
	}

}
