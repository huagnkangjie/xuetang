package com.ziyue.xuetang.dao.user;

import java.util.List;
import java.util.Map;

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
public interface UserDao {
	
	int deleteByPrimaryKey(Integer id);

    int insertSelective(UserAccounts record);

    UserAccounts selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAccounts record);

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
	public Map<String, Object> getMaxUserId();
	
	/**
	 * 条件查询用户基本信息
	 * @param map
	 * @return
	 */
	public UserAccounts selectUser(Map<String, Object> map);
	
	
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
	 * 用户收藏问题
	 * @param userId
	 * @param questionId
	 * @return
	 */
	public UserCollectionsQuestion getCollectionQuestion(UserCollectionsQuestion ucq);
	
	/**
	 * 判断是不是好友
	 * @param req_user_id
	 * @param res_user_id
	 * @return
	 */
	public List<Map<String,Object>> isFriend(int req_user_id, int res_user_id);
	
	/**
	 * 收藏问题
	 * @param map
	 * @return
	 */
	int collectionQuestion(Map<String, Object> map);
	
	/**
	 * 取消收藏问题
	 * @param map
	 * @return
	 */
	int canselCollectionQuestion(Map<String, Object> map);
	
	/**
	 * 获取用户的绑定板块
	 * @param map
	 * @return
	 */
	Map<String, Object> getUserBoards(Map<String, Object> map);
	
}
