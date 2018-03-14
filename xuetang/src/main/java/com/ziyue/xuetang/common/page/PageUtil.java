package com.ziyue.xuetang.common.page;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * @描述：数据分页工具
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月5日
 * @version v1.0.
 * 
 */
public class PageUtil {
	
	private static Logger logger = Logger.getLogger(PageUtil.class);

	
	public static int getDefualtPageNo(Map<String, Object> map){
		
		if(!StringUtils.isEmpty(map.get("pageNo"))){
			try {
				return Integer.valueOf(String.valueOf(map.get("pageNo")));
			} catch (Exception e) {
				logger.error("获取pageNo出错", e);
			}
		}
		return 1;
	}
	
	public static int getDefualtPageSize(Map<String, Object> map){
		
		if(!StringUtils.isEmpty(map.get("pageSize"))){
			try {
				return Integer.valueOf(String.valueOf(map.get("pageSize")));
			} catch (Exception e) {
				logger.error("获取pageSize出错", e);
			}
		}
		return 10;
	}
}
