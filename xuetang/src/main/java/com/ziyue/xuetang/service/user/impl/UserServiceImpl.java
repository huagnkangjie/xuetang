package com.ziyue.xuetang.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.dao.user.UserDao;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.model.user.UserCollectionsQuestion;
import com.ziyue.xuetang.service.user.UserService;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月6日
 * @version v1.0.
 * 
 */
@Transactional
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<Map<String, Object>> checkUserisRegister(Map<String, Object> map) {
		return userDao.checkUserisRegister(map);
	}

	@Override
	public int getMaxUserId() {
		Map<String, Object> map = userDao.getMaxUserId();
		if(map != null){
			Object maxId = map.get("maxId");
			if(maxId != null){
				return Integer.valueOf(String.valueOf(maxId));
			}
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(UserAccounts record) {
		return userDao.insertSelective(record);
	}

	@Override
	public UserAccounts selectByPrimaryKey(Integer id) {
		return userDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserAccounts record) {
		return userDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public UserAccounts selectUser(Map<String, Object> map) {
		return userDao.selectUser(map);
	}
	
	@Override
	public UserAccounts selectUserByUserId(int userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		return userDao.selectUser(map);
	}

	@Override
	public List<Map<String, Object>> getSelectList() {
		return userDao.getSelectList();
	}

	@Override
	public List<Map<String, Object>> getBoradsList(String selectId) {
		return userDao.getBoradsList(selectId);
	}

	@Override
	public List<Map<String, Object>> getTagsList(String broadsId) {
		return userDao.getTagsList(broadsId);
	}

	@Override
	public UserCollectionsQuestion getCollectionQuestion(UserCollectionsQuestion ucq) {
		return userDao.getCollectionQuestion(ucq);
	}

	@Override
	public boolean isFriend(int req_user_id, int res_user_id) {
		try {
			List<Map<String, Object>> list =userDao.isFriend(req_user_id, res_user_id);
			if(list.size() > 0){
				return true;
			}
		} catch (Exception e) {
			
		}
		
		return false;
	}

	@Override
	public int collectionQuestion(Map<String, Object> map) {
		return userDao.collectionQuestion(map);
	}

	@Override
	public int canselCollectionQuestion(Map<String, Object> map) {
		return userDao.canselCollectionQuestion(map);
	}

	@Override
	public Map<String, Object> getUserBoards(Map<String, Object> map) {
		return userDao.getUserBoards(map);
	}


}
