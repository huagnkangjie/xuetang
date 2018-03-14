package com.ziyue.xuetang.presentation.action;
/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月22日
 * @version v1.0.
 * 
 */
public class BaseController {
	
	protected String retContent(int status,Object data) {
        return ReturnFormat.retParam(status, data);
    }

}
