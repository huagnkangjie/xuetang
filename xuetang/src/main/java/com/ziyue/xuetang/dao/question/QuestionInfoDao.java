package com.ziyue.xuetang.dao.question;

import java.util.List;
import java.util.Map;

import com.ziyue.xuetang.model.question.QuestionAnswer;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionReports;
import com.ziyue.xuetang.model.question.QuestionShare;
import com.ziyue.xuetang.model.question.QuestionTags;

public interface QuestionInfoDao {
	
    int deleteByPrimaryKey(Integer id);

    int insertSelective(QuestionInfo record);

    QuestionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionInfo record);
    
    /**
     * 插入标签
     * @param tags
     * @return
     */
    int insertTag(QuestionTags tag);
    
    /**
     * 插入分享url
     * @param share
     * @return
     */
    int inserShareUrl(QuestionShare share);
    
    /**
     * 条件查询问题列表 - 首页
     * @return
     */
    List<QuestionInfo> queryQuestionList(Map<String, Object> map);
    
    /**
     * 查询分享url的列表
     * @param questionId
     * @return
     */
    List<QuestionShare> queryQuestionShareList(int questionId);
    
    /**
     * 查询标签列表
     * @param questionId
     * @return
     */
    List<QuestionTags> queryQuestionTagsList(int questionId);
    
    /**
     * 获取问题的回答一级回复列表
     * @param map
     * @return
     */
    List<Map<String,Object>> querQuestionAnswerLevelOne(Map<String,Object> map);
    
    /**
     * 获取问题的回答二级回复列表
     * @param map
     * @return
     */
    List<Map<String,Object>> querQuestionAnswerLevelTwo(Map<String,Object> map);
    
    /**
     * 添加评论
     * @param answer
     * @return
     */
    int insertQuestionAnswer(QuestionAnswer answer);
    
    /**
     * 取消采纳
     * @param questionId
     * @return
     */
    int cancleAdopt(int questionId);
    
    /**
     * 举报问题、或者举报评论
     * @param reports
     * @return
     */
    int reports(QuestionReports reports);
    
    /**
     * 创建问题时，插入图片
     * @param picMap
     */
    void insertQuestionPic(Map<String,Object> picMap);
    
    /**
     * 回复问题时，插入图片
     * @param picMap
     */
    void insertQuestionAnswerPic(Map<String,Object> picMap);
    
    /**
     * 获取首页问题列表图片
     * @param questionId
     * @return
     */
    List<Map<String, Object>> getQuestionPicList(int questionId);
    
    /**
     * 获取一级回复图片列表
     * @param questionId
     * @return
     */
    List<Map<String, Object>> getQuestionAnswerPicList(int questionId);
    
    /**
     * 个人中心问题列表 - 我参与回答的
     * @return
     */
    List<QuestionInfo> queryMyAnswerQuestionList(Map<String, Object> map);
    
    /**
     * 个人中心问题列表 - 我收藏的问题列表
     * @return
     */
    List<QuestionInfo> queryMyCollectionQuestionList(Map<String, Object> map);
    
    /**
     * 个人中心问题列表 - 自己（管理员）所属领域，被举报的内容
     * @return
     */
    List<QuestionInfo> queryReportQuestionList(Map<String, Object> map);
    
    
    
}