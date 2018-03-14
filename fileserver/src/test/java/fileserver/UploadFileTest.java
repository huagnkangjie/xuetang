package fileserver;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年5月12日
 * @version v1.0.
 * 
 */
public class UploadFileTest {

	
	public static void main(String[] args) {
		uploadHoaTepmFile();
	}
	
	public static void uploadHoaTepmFile(){
		try {
			 String entity = "";
//			 HttpPost post = new HttpPost("http://121.196.220.93:8080/fileserver/uploadTempFile/225/c4ca4238a0b923820dcc509a6f75849b");
			 HttpPost post = new HttpPost("http://127.0.0.1:8080/fileserver/uploadTempFile/225/c4ca4238a0b923820dcc509a6f75849b");
			 
			//3.放置header信息
	        StringBuilder headerMsg = new StringBuilder();
	        //设置AUTH
	        headerMsg.append("isAuth=\"true\"");
	        //设置KEY
	        headerMsg.append(",key=\"c57776662ba11b9e855e253f0a2fa25d\"");
	        //设置serverID
	        headerMsg.append(",serverID=\"bbbbb\"");
	        post.setHeader("HOA_auth", headerMsg.toString());
	        
	         MultipartEntity entity1 = new MultipartEntity();
	         // FileBody继承了ContentBody接口，返回的是文件的二进制部分
	         FileBody fileBody = new FileBody(new File("D://test1.jpg"));
//	         FileBody fileBody = new FileBody(new File("D://routerImport1.xls"));
//	         FileBody fileBody = new FileBody(new File("D:/功能权限.xls"),"utf-8");
//	         FileBody fileBody = new FileBody(new File("D:/router_uImage.uImage"));
//	         FileBody fileBody = new FileBody(new File("D:/android.apk"));
	         entity1.addPart("225_201705110951550691_jpg", fileBody);
	         System.out.println("源文件："+fileBody.getFilename());
	         // StringBody也是contentBody接口的继承类，返回一个字节数组的文本部分
	         entity1.addPart("username",  new StringBody("yudeshui"));
	         entity1.addPart("fileNameCH",  new StringBody(fileBody.getFilename(), Charset  
                     .forName("UTF-8")));
//	         entity1.addPart("uploader",  new StringBody("be"));
	         post.setEntity(entity1);
	         // 2.调用服务管理
	         HttpClient client = new DefaultHttpClient();
	         HttpResponse httpResponse = client.execute(post);
	         System.out.println(httpResponse.getStatusLine());
	         // 3.获取服务管理返回值
	         entity = EntityUtils.toString(httpResponse.getEntity());
	         System.out.println(httpResponse.getStatusLine().getStatusCode());
	         System.out.println(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
