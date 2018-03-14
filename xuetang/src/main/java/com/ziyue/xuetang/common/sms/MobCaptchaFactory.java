package com.ziyue.xuetang.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.alibaba.fastjson.JSON;

public class MobCaptchaFactory {

	private static MobCaptchaFactory instance;
	private String appKey = "e87a7d8f209f";

	HostnameVerifier hv = new HostnameVerifier() {
		@Override
		public boolean verify(String urlHostName, SSLSession session) {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			return true;
		}
	};

	public enum TemplateCode {

		T_Invite("10658164");

		private String value;

		private TemplateCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	public static MobCaptchaFactory instance() {
		if (instance == null) {
			instance = new MobCaptchaFactory();
		}

		return instance;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	// public String sendGet(String url, String param) {
	// String result = "";
	// BufferedReader in = null;
	// try {
	// String urlNameString = url + "?" + param;
	// URL realUrl = new URL(urlNameString);
	// // 打开和URL之间的连接
	// URLConnection connection = realUrl.openConnection();
	// // 设置通用的请求属性
	// connection.setRequestProperty("accept", "*/*");
	// connection.setRequestProperty("connection", "Keep-Alive");
	// connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;
	// MSIE 6.0; Windows NT 5.1;SV1)");
	// // 建立实际的连接
	// connection.connect();
	// // 获取所有响应头字段
	// Map<String, List<String>> map = connection.getHeaderFields();
	// // 遍历所有的响应头字段
	// for (String key : map.keySet()) {
	// System.out.println(key + "--->" + map.get(key));
	// }
	// // 定义 BufferedReader输入流来读取URL的响应
	// in = new BufferedReader(new
	// InputStreamReader(connection.getInputStream()));
	// String line;
	// while ((line = in.readLine()) != null) {
	// result += line;
	// }
	// } catch (Exception e) {
	// System.out.println("发送GET请求出现异常！" + e);
	// e.printStackTrace();
	// }
	// // 使用finally块来关闭输入流
	// finally {
	// try {
	// if (in != null) {
	// in.close();
	// }
	// } catch (Exception e2) {
	// e2.printStackTrace();
	// }
	// }
	// return result;
	// }

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	private String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		HttpsURLConnection conn;
		try {
			URL realUrl = new URL(url);

			trustAllHttpsCertificates(); // 忽略ssl验证

			HttpsURLConnection.setDefaultHostnameVerifier(hv);

			// 打开和URL之间的连接
			conn = (HttpsURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(1000 * 60);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
			return null;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return result;
	}

	/**
	 * 校验
	 * 
	 * @param phone
	 * @param code
	 * @return
	 */
	public String checkCaptcha(String phone, String code) {

		String url = "https://webapi.sms.mob.com/sms/checkcode";

		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("appkey=" + appKey);

		paramBuffer.append("&zone=86");
		paramBuffer.append("&phone=" + phone);
		paramBuffer.append("&code=" + code);

		String param = paramBuffer.toString().trim();
		System.out.println(param);
		String strReturn = sendPost(url, param);

		if (strReturn != null && !strReturn.equals("")) {

			Map<String, Object> map = (Map<String, Object>) JSON.parse(strReturn);

			Object obj = map.get("status");

			return obj.toString();

		} else {
			return null;
		}

	}

	/***
	 * 
	 * 发送验证码
	 * 
	 * @param phone
	 * @return 200 发送成功
	 */
	public String sendCaptcha(String phone) {

		String url = "https://webapi.sms.mob.com/sms/sendmsg";
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("appkey=" + appKey);

		paramBuffer.append("&zone=86");
		
		paramBuffer.append("&phone=" + phone);

		String param = paramBuffer.toString().trim();

		String strReturn = sendPost(url, param);

		if (strReturn != null && !strReturn.equals("")) {

			Map<String, Object> map = (Map<String, Object>) JSON.parse(strReturn);

			return map.get("status").toString();

		} else {
			return null;
		}

	}

	/**
	 * 发送模版消息
	 * 
	 * @param phone
	 * @param tcode
	 * @param pmap
	 * @return
	 */
	public String sendMsg(String phone, TemplateCode tcode, Map<String, Object> pmap) {

		String templateCode = tcode.getValue();

		String url = "https://web.sms.mob.com/custom/msg";

		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("appKey=" + appKey);
		paramBuffer.append("&templateCode=" + templateCode);
		paramBuffer.append("&zone=86");
		paramBuffer.append("&phone=" + phone);

		if (pmap != null && pmap.size() > 0) {

			for (Map.Entry<String, Object> m : pmap.entrySet()) {
				paramBuffer.append("&" + m.getKey() + "=" + m.getValue());
			}

		}

		String param = paramBuffer.toString().trim();
		System.out.println(param);
		String strReturn = sendPost(url, param);

		if (strReturn != null && !strReturn.equals("")) {

			Map<String, Object> map = (Map<String, Object>) JSON.parse(strReturn);

			Object obj = map.get("status");

			return obj.toString();

		} else {
			return null;
		}

	}

	public static void main(String[] args) {

		// Map map = new HashMap();
		// map.put("name", "123");
		// map.put("company", "小米科技");
		// map.put("content", "面试邀请");

		// System.out.println(MobCaptchaFactory.instance().sendMsg("15283771727",
		// TemplateCode.T_Invite, map));

		 System.out.println(MobCaptchaFactory.instance().sendCaptcha("18780139108"));

		//System.out.println(MobCaptchaFactory.instance().checkCaptcha("18875047277", "5737"));

		// System.out.println(MobCaptchaFactory.instance().sendPost("https://web.sms.mob.com/custom/msg",
		// "appKey=xxxx&templateCode=e87a7d8f209f&zone=86&phone=15283771727&name=test"));

		// https://web.sms.mob.com/custom/msg?appKey=xxxx&templateCode=4270530&zone=86&phone=18610239262&name=test&address=test

		// System.out.println(MobCaptchaFactory.instance().sendCaptcha("15902313445"));
		// System.out.println(MobCaptchaFactory.instance().checkCaptcha("15283771727",
		// "1234"));

		// 发送 GET 请求
		// String
		// s=HttpRequestUtil.sendGet("http://https://webapi.sms.mob.com/sms/sendmsg",
		// "key=123&v=456");
		// System.out.println(s);

	}

	private void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
			return true;
		}

		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}

}
