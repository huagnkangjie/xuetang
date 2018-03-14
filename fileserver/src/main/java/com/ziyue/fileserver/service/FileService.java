package com.ziyue.fileserver.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ziyue.fileserver.util.JacksonUtil;
import com.ziyue.fileserver.util.PropertyUtils;
import com.ziyue.fileserver.util.TimeUtil;


/**
 * 提供文件上传、下载能力
 * @author huangkangjie
 */
@Controller
@RequestMapping
public class FileService {
	
	private static Logger logger = LoggerFactory.getLogger(FileService.class);
	
	 /**
     * 上传临时文件
     * @param request
     * @param response
     * @param token
     */
	@RequestMapping(method = RequestMethod.POST, value = "/uploadTempFile/{userID}/{token}")
    public void uploadTempFile(HttpServletRequest request,HttpServletResponse response,CommonsMultipartResolver multipartResolver,
    		@PathVariable String token, @PathVariable String userID){
    	//构建返回信息
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200); // 设置返回状态为OK
        
        logger.info("上传文件 token : " + token +", userID:" + userID);
    	
        Map<String, Object> rsDataMap = new HashMap<String, Object>();
        //构建返回的数据
        resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
        resultMap.put(Config.DESCRIPTION, "文件上传失败");
        resultMap.put(Config.DATA, rsDataMap);
        
        
    	if(StringUtils.isEmpty(token)){
    		resultMap.put(Config.DESCRIPTION, "token 不能为空");
    		logger.info("上传文件 : token不能为空！");
    		this.responseWrite(response, resultMap);
    		return;
    	}
    	
//    	String jsonToken = jedisPool.getJedis().get(token);
//    	
//    	if(StringUtils.isEmpty(jsonToken)){
//    		resultMap.put("errorCode", "5");
//    		resultMap.put("description", "token已失效");
//    		logger.info("上传文件 : token已失效！");
//    		this.responseWrite(response, resultMap);
//    		return;
//    	}
    	
    	//创建一个通用的多部分解析器  
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    logger.info("上传文件：原文件名：" + myFileName);
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){
                    	try {
                    		
                    		String tempFilePath = PropertyUtils.getValue("temp.file.store.path");
                    		String suffix = getSuffix(myFileName);
                    		if(StringUtils.isEmpty(suffix)){
                    			logger.info("获取文件后缀名出错");
    							resultMap.put(Config.ERROR_CODE, Config.FILE_NAME_FOMMAT_ERROR);
    					        resultMap.put(Config.DESCRIPTION, "获取文件后缀名出错");
    	                		this.responseWrite(response, resultMap);
    	                		return;
                    		}
                    		//开始保存文件
                    		String fileNewName = TimeUtil.format(new Date(), "yyyyMMddHHmmssSSSS") + suffix;
                    		File tempFile = new  File(tempFilePath + fileNewName);
                    		if(!tempFile.exists()){
                    			tempFile.mkdirs();
                    		}
                    		file.transferTo(tempFile);
                    		
                    		String fileDomain = PropertyUtils.getValue("file.domain");
                    		rsDataMap.put("url", fileDomain + "/temp/" + fileNewName);
                    		
                    		resultMap.put(Config.ERROR_CODE, Config.ZERO);
                            resultMap.put(Config.DESCRIPTION, "success");
                            resultMap.put(Config.DATA, rsDataMap);
                            
                            logger.info("上传成功：" + resultMap);
                            
                    		this.responseWrite(response, resultMap);
                    		return;
						} catch (Exception e) {
							logger.error("上传文件失败！", e);
							resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
					        resultMap.put(Config.DESCRIPTION, "文件上传失败");
	                		this.responseWrite(response, resultMap);
	                		return;
						}
                    }else{
                    	resultMap.put(Config.ERROR_CODE, Config.FILE_NOT_EXSIT);
                        resultMap.put(Config.DESCRIPTION, "文件不存在");
                		this.responseWrite(response, resultMap);
                		return;
                    }
                }
            }  
              
        }  
        
        resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
        resultMap.put(Config.DESCRIPTION, "文件上传失败");
		this.responseWrite(response, resultMap);
    }
	
	public void responseWrite(HttpServletResponse response, Map<String, Object> resultMap){
		try {
			response.getWriter().write(JacksonUtil.mapToJson(resultMap));
			response.getWriter().flush();
	        response.getWriter().close();
		} catch (IOException e) {
			logger.error("上传文件出错",e);
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				logger.error("上传文件出错",e);
			}
		}
        //立即返回客户端，并关闭连接
        
    }
    
	public static String getSuffix(String fileName){
		try {
			return fileName.substring(fileName.lastIndexOf("."), fileName.length());
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getSuffix("safasdf..asdf.ppt"));
	}
	
	
	/**
     * 拷贝文件
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.POST, value = "/copyFile/{firstpath}/{userId}")
    public void copyFile(HttpServletRequest request, HttpServletResponse response, 
    		@PathVariable("userId")
    		String userId, @PathVariable("firstpath")
    		String firstpath)
                throws ServletException, IOException{
    	response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> rsDataMap = new HashMap<String, Object>();
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        
        String tempFileName = request.getParameter("tempFileName");
        String fileType = request.getParameter("fileType");
        String tempFilePath = PropertyUtils.getValue("temp.file.store.path");
        
        logger.debug("接收到文件上传请求：userId="+userId+",firstpath="+firstpath + "，文件名：" + tempFileName);
        
        File tempFile = new File(tempFilePath + tempFileName);
        
        if(tempFile == null){
        	resultMap.put(Config.ERROR_CODE, Config.FILE_NOT_EXSIT);
            resultMap.put(Config.DESCRIPTION, "临时文件不存在");
            responseWrite(response, resultMap);
            logger.info("临时文件不存在");
            return;
        }
    	
        //构建返回的数据
        resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
        resultMap.put(Config.DESCRIPTION, "文件上传失败");
        resultMap.put(Config.DATA, rsDataMap);
        
        //判断参数
        if(StringUtils.isEmpty(firstpath) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(tempFileName)){
        	resultMap.put(Config.ERROR_CODE, Config.PARAM_ERROR);
            resultMap.put(Config.DESCRIPTION, "文件上传失败");
            responseWrite(response, resultMap);
            logger.info("文件拷贝参数错误： firstpath = " + firstpath + "; userId = " + userId + "; tempFileName = " + tempFileName);
            return;
        }
    	
        String path = PropertyUtils.getValue("file.store.path");
        //创建存储目录
        File dir = new File(path);
        boolean result = true;
        if (!dir.exists())
        {
            result = dir.mkdirs();
        }
        if (result == true)
        {
            //新文件路径
            String fileFloaderPath = path + firstpath +"/"+ userId +"/";
            File fileFloader = new File(fileFloaderPath);
            if(!fileFloader.exists()){
            	fileFloader.mkdirs();
            }
            String filePath = fileFloaderPath + tempFileName;
            File newFile = new File(filePath);
            //拷贝文件
            FileInputStream in = null;
            FileOutputStream out = null;
            try
            {
            	//解决拷贝文件大小相同
            	in=new FileInputStream(tempFile);
            	out = new FileOutputStream(
            			newFile);
                byte[] bt = new byte[1024];
                int iRead = 0;
                while ((iRead = in.read(bt)) > 0)
                {
                	out.write(bt, 0, iRead); // 向服务端文件写入字节流
                }
                
                out.flush();
                out.close(); // 关闭FileOutputStream对象
                in.close(); // InputStream对象
            }
            catch (IOException e1)
            {
            	logger.info("文件拷贝失败",e1);
                resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
                resultMap.put(Config.DESCRIPTION, "文件拷贝失败");
                responseWrite(response, resultMap);
                return;
            }finally {
            	in.close();
                out.flush();
                out.close();
			}
            String fileDomain = PropertyUtils.getValue("file.domain");
            String url = fileDomain +"/"+ firstpath +"/"+ userId +"/"+ tempFileName;
            
            rsDataMap.put("url", url);
            
            resultMap.put(Config.ERROR_CODE, Config.ZERO);
            resultMap.put(Config.DESCRIPTION, "success");
            resultMap.put(Config.DATA, rsDataMap);
            responseWrite(response, resultMap);
            
            logger.info("文件拷贝成功：" + resultMap);
            return;
            
        }
        else
        {
            logger.info("文件目录创建失败");
            resultMap.put(Config.ERROR_CODE, Config.SERVER_ERROR);
            resultMap.put(Config.DESCRIPTION, "文件目录创建失败");
            responseWrite(response, resultMap);
            return;
        }
    }
	
}
