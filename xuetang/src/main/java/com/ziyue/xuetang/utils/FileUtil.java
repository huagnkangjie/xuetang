package com.ziyue.xuetang.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.ziyue.xuetang.constant.Config;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月28日
 * @version v1.0.
 * 
 */
public class FileUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	
	/**
	 * 拷贝服务器文件
	 * @param firstPath
	 * @param userId
	 * @param tempFileUrl
	 * @return
	 * @throws LogicException
	 */
	public static String copyFile(String firstPath, String userId, String tempFileUrl)
	{
		String fileMangerIP = PropertyUtils.getValue("fileManger.service");
		String result = "";
		String url = "";
		try {
			HttpPost post = new HttpPost(fileMangerIP + "/copyFile/" + firstPath + "/"
					+ userId);
			// 1.放置header信息
			StringBuilder headerMsg = new StringBuilder();
			// 设置AUTH
			headerMsg.append("isAuth=\"true\"");
			// 设置serverID
			headerMsg.append(",serverID=\"bbbbb\"");
			post.setHeader("HOA_auth", headerMsg.toString());
			// 设置参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("tempFileName", getFileNameByURL(tempFileUrl)));
			post.setEntity(new UrlEncodedFormEntity(nvps));
			// 2.调用服务管理
			HttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(post);
			// 3.获取服务管理返回值
			result = EntityUtils.toString(httpResponse.getEntity());
			logger.info("HTTP状态：" + httpResponse.getStatusLine().getStatusCode());
			logger.info("文件copy返回结果：" + result);
			
			String errorCode = JacksonUtil.getJsonValue(result, "errorCode");
			
			if(!StringUtils.isEmpty(errorCode) && Config.ZERO.equals((String)errorCode)){
				Map<String, Object> map = JacksonUtil.jsonToMap(result);
				@SuppressWarnings("unchecked")
				Map<String, String> dataMap = (Map<String, String>) map.get("data");
				url = dataMap.get("url");
				logger.info("文件copy返回地址：" + url);
			}else {
				logger.info("文件出错copy出错");
			}
		} catch (Exception e) {
			logger.error("文件出错copy出错", e);
		}
		return url;

	}
	
	/**
	 * 根据URL获取文件名
	 * @param URL
	 * @return
	 */

	public static String getFileNameByURL(String URL) {
		String fileName = "";
		if (URL.contains("/")) {
			try {
				fileName = URL.substring(URL.lastIndexOf("/") + 1, URL.length());
			} catch (Exception e) {
				logger.error("根据URL获取文件名出错，URL：" + URL, e);
			}
		}
		return fileName;

	}
	
	public static void main(String[] args) {
		copyFile("test", "225", "http://127.0.0.1:8080/files/temp/201712281045210221.xls");
	}
}
