package com.ziyue.xuetang.presentation.action;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ziyue.xuetang.constant.RspCode;
import com.ziyue.xuetang.exception.ValidateException;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月22日
 * @version v1.0.
 * 
 */

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
　　　　　　　　 以及springmvc自定义异常等，如下：
SpringMVC自定义异常对应的status code  
           Exception                       HTTP Status Code  
ConversionNotSupportedException         500 (Internal Server Error)
HttpMessageNotWritableException         500 (Internal Server Error)
HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
NoSuchRequestHandlingMethodException    404 (Not Found) 
TypeMismatchException                   400 (Bad Request)
HttpMessageNotReadableException         400 (Bad Request)
MissingServletRequestParameterException 400 (Bad Request)
 *
 */
@ControllerAdvice
public class RestExceptionHandler{
	
	private static Logger logger = Logger.getLogger(RestExceptionHandler.class);
	
	//自定义异常
	@ExceptionHandler(ValidateException.class)  
	@ResponseBody  
	public String ValidateException(ValidateException e) {  
		logger.error("ValidateException code = " + e.getCode() + ", msg = " + e.getMessage());
		if(!StringUtils.isEmpty(e.getMessage())){
			RspCode.CODE_PARAM.setMsg(e.getMessage());
		}
		if(!StringUtils.isEmpty(e.getCode())){
			RspCode.CODE_PARAM.setCode(e.getCode());
		}
		
		return ReturnFormat.retParam(RspCode.CODE_PARAM, null);
	}  
	
    //运行时异常
    @ExceptionHandler(RuntimeException.class)  
    @ResponseBody  
    public String runtimeExceptionHandler(RuntimeException runtimeException) {  
    	logger.error("服务器内部错误", runtimeException);
        return ReturnFormat.retParam(1000, null);
    }  

    //空指针异常
    @ExceptionHandler(NullPointerException.class)  
    @ResponseBody  
    public String nullPointerExceptionHandler(NullPointerException ex) {  
    	logger.error("",ex);
        return ReturnFormat.retParam(1001, null);
    }   
    //类型转换异常
    @ExceptionHandler(ClassCastException.class)  
    @ResponseBody  
    public String classCastExceptionHandler(ClassCastException ex) {  
    	logger.error("", ex);
        return ReturnFormat.retParam(1002, null);  
    }  

    //IO异常
    @ExceptionHandler(IOException.class)  
    @ResponseBody  
    public String iOExceptionHandler(IOException ex) {  
        logger.error("", ex);
        return ReturnFormat.retParam(1003, null); 
    }  
    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)  
    @ResponseBody  
    public String noSuchMethodExceptionHandler(NoSuchMethodException ex) {  
        logger.error("", ex);
        return ReturnFormat.retParam(1004, null);
    }  

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)  
    @ResponseBody  
    public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {  
        logger.error("",ex);
        return ReturnFormat.retParam(1005, null);
    }
    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public String requestNotReadable(HttpMessageNotReadableException ex){
        logger.error("http 400..requestNotReadable");
        logger.error(ex);
        return ReturnFormat.retParam(400, null);
    }
    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public String requestTypeMismatch(TypeMismatchException ex){
        logger.error("http 400..TypeMismatchException");
        logger.error(ex);
        return ReturnFormat.retParam(400, null);
    }
    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public String requestMissingServletRequest(MissingServletRequestParameterException ex){
        logger.error("http 400..MissingServletRequest");
        logger.error(ex);
        return ReturnFormat.retParam(400, null);
    }
    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public String request405(){
    	logger.error("http 405");
        return ReturnFormat.retParam(405, null);
    }
    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public String request406(){
    	logger.error("http 406");
        return ReturnFormat.retParam(406, null);
    }
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
    @ResponseBody
    public String server500(RuntimeException runtimeException){
    	logger.error("http 500", runtimeException);
        return ReturnFormat.retParam(406, null);
    }
}
