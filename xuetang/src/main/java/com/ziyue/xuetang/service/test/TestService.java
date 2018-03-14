package com.ziyue.xuetang.service.test;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.model.User;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年10月29日
 * @version v1.0.
 * 
 */
public interface TestService {
	public List<Map<String, Object>> getAcounts(Map<String, Object> map);
	public PageInfo getUsers(User user);
	
	public void insertTest() throws Exception;
	
}
