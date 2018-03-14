package com.ziyue.xuetang.service.question;

import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.model.question.QuestionAnswer;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionReports;
import com.ziyue.xuetang.model.question.QuestionShare;
import com.ziyue.xuetang.model.question.QuestionTags;

import java.util.List;
import java.util.Map;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月25日
 * @version v1.0.
 * 
 */
public interface QuestionInfoService {

	
	public int deleteByPrimaryKey(Integer id);

	public int insertSelective(QuestionInfo record);

	public QuestionInfo selectByPrimaryKey(Integer id);

	public int updateByPrimaryKeySelective(QuestionInfo record);
	
	/**
     * 插入标签
     * @param tag
     * @return
     */
	public int insertTag(QuestionTags tag);
    
    /**
     * 插入分享url
     * @param share
     * @return
     */
	public int inserShareUrl(QuestionShare share);
	
	/**
     * 条件查询问题列表
     * @return
     */
	public PageInfo<QuestionInfo> queryQuestionList(Map<String, Object> map, int pageNo, int pageSize);
	
	
	/**
     * 查询分享url的列表
     * @param questionId
     * @return
     */
	public List<QuestionShare> queryQuestionShareList(int questionId);
    
    /**
     * 查询标签列表
     * @param questionId
     * @return
     */
	public List<QuestionTags> queryQuestionTagsList(int questionId);
	
	
	/**
     * 获取问题的回答一级回复列表
     * @param map
     * @return
     */
	public PageInfo<Map<String,Object>> querQuestionAnswerLevelOne(Map<String,Object> map, int pageNo, int pageSize);
	
	/**
     * 获取问题的回答二级回复列表
     * @param map
     * @return
     */
	public List<Map<String,Object>> querQuestionAnswerLevelTwo(Map<String,Object> map);
	
	/**
     * 添加评论
     * @param answer
     * @return
     */
	public int insertQuestionAnswer(QuestionAnswer answer);
	
	
	/**
     * 取消采纳
     * @param questionId
     * @return
     */
	public int cancleAdopt(int questionId);
	
	
	
	/**
     * 举报问题、或者举报评论
     * @param reports
     * @return
     */
	public int reports(QuestionReports reports);
	
	/**
	 * 创建问题时，添加图片
	 * @param picMap
	 */
	public void insertQuestionPic(Map<String,Object> picMap);
	
	/**
	 * 回复问题时，添加图片
	 * @param picMap
	 */
	public void insertQuestionAnswerPic(Map<String,Object> picMap);
	
	/**
     * 获取首页问题列表图片
     * @param questionId
     * @return
     */
	public List<Map<String, Object>> getQuestionPicList(int questionId);
	
	/**
     * 获取一级回复图片列表
     * @param questionId
     * @return
     */
	public List<Map<String, Object>> getQuestionAnswerPicList(int questionId);
	
	/**
     * 个人中心问题列表  -- 我参与回答的问题列表
     * @return
     */
	public PageInfo<QuestionInfo> queryMyAnswerQuestionList(Map<String, Object> map, int pageNo, int pageSize);
	
	/**
	 * 个人中心问题列表  -- 我收藏的问题列表
	 * @return
	 */
	public PageInfo<QuestionInfo> queryMyCollectionQuestionList(Map<String, Object> map, int pageNo, int pageSize);
	
	/**
	 * 个人中心问题列表 - 自己（管理员）所属领域，被举报的内容
	 * @return
	 */
	public PageInfo<QuestionInfo> queryReportQuestionList(Map<String, Object> map, int pageNo, int pageSize);

	
	
	
	


}
