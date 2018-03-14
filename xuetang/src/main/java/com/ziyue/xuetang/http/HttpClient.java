package com.ziyue.xuetang.http;

import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClient {

    protected static HttpClient client = new HttpClient();

    private static final int OK = 200;// OK: Success!
    private static final int NOT_MODIFIED = 304;// Not Modified: There was no new data to return.
    private static final int BAD_REQUEST = 400;// Bad Request: The request was invalid. An
                                               // accompanying error message will explain why. This
                                               // is the status code will be returned during rate
                                               // limiting.
    private static final int NOT_AUTHORIZED = 401;// Not Authorized: Authentication credentials were
                                                  // missing or incorrect.
    private static final int FORBIDDEN = 403;// Forbidden: The request is understood, but it has
                                             // been refused. An accompanying error message will
                                             // explain why.
    private static final int NOT_FOUND = 404;// Not Found: The URI requested is invalid or the
                                             // resource requested, such as a user, does not exists.
    private static final int NOT_ACCEPTABLE = 406;// Not Acceptable: Returned by the Search API when
                                                  // an invalid format is specified in the request.
    private static final int INTERNAL_SERVER_ERROR = 500;// Internal Server Error: Something is
                                                         // broken. Please post to the group so the
                                                         // Weibo team can investigate.
    private static final int BAD_GATEWAY = 502;// Bad Gateway: Weibo is down or being upgraded.
    private static final int SERVICE_UNAVAILABLE = 503;// Service Unavailable: The Weibo servers are
                                                       // up, but overloaded with requests. Try
                                                       // again later. The search and trend methods
                                                       // use this to indicate when you are being
                                                       // rate limited.



    public HttpClient() {
        this(150, 30000, 30000, 1024 * 1024);
    }

    public HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs, int maxSize) {


        // ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        // LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        // Registry<ConnectionSocketFactory> registry =
        // RegistryBuilder.<ConnectionSocketFactory>create()
        // .register("http", plainsf)
        // // .register("https", sslsf)
        // .build();
        //
        //
        //
        // //创建httpclient连接池
        // PoolingHttpClientConnectionManager httpClientConnectionManager = new
        // PoolingHttpClientConnectionManager();
        // //设置连接池最大数量
        // httpClientConnectionManager.setMaxTotal(50);
        // //设置单个路由最大连接数量
        // httpClientConnectionManager.setDefaultMaxPerRoute(20);

    }

    
    public static HttpClient getHttpClient(){
        return client;
    }

    public String sendPostRequest(String url, String json) throws Exception {
     org.apache.http.client.HttpClient httpClient;
       HttpContext localContext;
       httpClient = new DefaultHttpClient();
       localContext = new BasicHttpContext();

       // 设置连接核心参数
       httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
               HttpVersion.HTTP_1_1);
       httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, HTTP.UTF_8);
       httpClient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
       httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
       
       
        HttpPost httpPost = null;
        httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
 
        // 设置请求报头 请求完毕立马关闭
        httpPost.setHeader("Connection", "close");

        HttpResponse response = null;
        int statusCode = 0;
        try {
            response = httpClient.execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            return    EntityUtils.toString(response.getEntity());  
            
        }  

        catch ( Exception e) {
  
        } finally {
            if (httpClient != null)
                httpClient.getConnectionManager().shutdown();
        }

        if (statusCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else {
            throw new Exception(
                    "http status:" + statusCode + ", deal with request failed! error:" + response);
        }


    }



}
