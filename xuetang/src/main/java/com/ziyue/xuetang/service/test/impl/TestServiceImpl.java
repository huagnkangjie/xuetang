package com.ziyue.xuetang.service.test.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.dao.test.TestDao;
import com.ziyue.xuetang.model.User;
import com.ziyue.xuetang.service.test.TestService;

/**
 * @鎻忚堪锛�
 *
 * @author 浣滆�� : huang_kangjie
 * @date 鍒涘缓鏃堕棿锛�2017骞�10鏈�29鏃�
 * @version v1.0.
 * 
 */

@Transactional
@Service
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestDao testDao;

	@Override
	public List<Map<String, Object>> getAcounts(Map<String, Object> map) {
		PageHelper.startPage(1, 2);
		List<Map<String, Object>> list = testDao.getAcounts(map);
		PageInfo<Map<String, Object>> pageinfo = new PageInfo<>(list);
		System.out.println(pageinfo);
		
		System.out.println(">>>>>>>>>>>>>>>>>"+pageinfo.getTotal());
		
		return testDao.getAcounts(map);
	}

	@Override
	public PageInfo getUsers(User user) {
		return testDao.getUsers(user);
	}

	@Override
	public void insertTest() throws Exception {
		testDao.insertt_common_boards();
		testDao.insertt_common_sections();
		
	}

}
