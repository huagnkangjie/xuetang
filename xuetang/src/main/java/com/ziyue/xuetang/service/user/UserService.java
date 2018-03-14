package com.ziyue.xuetang.service.user;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.user.UserAccounts;
import com.ziyue.xuetang.model.user.UserCollectionsQuestion;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月6日
 * @version v1.0.
 * 
 */
public interface UserService {
	
	public int deleteByPrimaryKey(Integer id);

	public int insertSelective(UserAccounts record);

	public UserAccounts selectByPrimaryKey(Integer id);

	public int updateByPrimaryKeySelective(UserAccounts record);

	/**
	 * 校验用户是否存在
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> checkUserisRegister(Map<String, Object> map);
	
	
	/**
	 * 获取用户最大的id
	 * @return
	 */
	public int getMaxUserId();
	
	
	/**
	 * 条件查询用户基本信息
	 * @param map
	 * @return
	 */
	public UserAccounts selectUser(Map<String, Object> map);
	
	/**
	 * 根据用户id获取基本信息
	 * @param map
	 * @return
	 */
	public UserAccounts selectUserByUserId(int userId);
	
	/**
	 * 获取领域list
	 * @return
	 */
	public List<Map<String, Object>> getSelectList();
	
	/**
	 * 获取板块列表
	 * @return
	 */
	public List<Map<String, Object>> getBoradsList(String selectId);
	
	/**
	 * 获取标签列表
	 * @return
	 */
	public List<Map<String, Object>> getTagsList(String broadsId);
	
	/**
	 * 获取用户收藏问题信息
	 * @param userId
	 * @param questionId
	 * @return
	 */
	public UserCollectionsQuestion getCollectionQuestion(UserCollectionsQuestion ucq);
	
	/**
	 * 判断用户是不是好友
	 * @param req_user_id 发起添加的好友
	 * @param res_user_id 接受添加的好友
	 * @return
	 */
	public boolean isFriend(int req_user_id, int res_user_id);
	
	
	/**
	 * 收藏问题
	 * @param map
	 * @return
	 */
	public int collectionQuestion(Map<String, Object> map);
	
	/**
	 * 取消收藏问题
	 * @param map
	 * @return
	 */
	public int canselCollectionQuestion(Map<String, Object> map);
	
	/**
	 * 获取用户的绑定板块
	 * @param map
	 * @return
	 */
	public Map<String, Object> getUserBoards(Map<String, Object> map);
	
	
}
