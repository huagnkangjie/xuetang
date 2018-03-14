package com.ziyue.xuetang.service.question.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ziyue.xuetang.common.page.Page;
import com.ziyue.xuetang.dao.question.QuestionInfoDao;
import com.ziyue.xuetang.model.question.QuestionAnswer;
import com.ziyue.xuetang.model.question.QuestionInfo;
import com.ziyue.xuetang.model.question.QuestionReports;
import com.ziyue.xuetang.model.question.QuestionShare;
import com.ziyue.xuetang.model.question.QuestionTags;
import com.ziyue.xuetang.service.question.QuestionInfoService;

/**
 * @描述：
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年11月25日
 * @version v1.0.
 * 
 */
 
@Service
@Transactional
public class QuestionInfoServiceImpl implements QuestionInfoService{

	 @Autowired
	 private QuestionInfoDao dao;
	 
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(QuestionInfo record) {
		return dao.insertSelective(record);
	}

	@Override
	public QuestionInfo selectByPrimaryKey(Integer id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(QuestionInfo record) {
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertTag(QuestionTags tag) {
		return dao.insertTag(tag);
	}

	@Override
	public int inserShareUrl(QuestionShare share) {
		return dao.inserShareUrl(share);
	}

	@Override
	public PageInfo<QuestionInfo> queryQuestionList(Map<String, Object> map, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<QuestionInfo> list = dao.queryQuestionList(map);
		PageInfo<QuestionInfo> pageinfo = new PageInfo<QuestionInfo>(list);
		return pageinfo;
	}

	@Override
	public List<QuestionShare> queryQuestionShareList(int questionId) {
		return dao.queryQuestionShareList(questionId);
	}

	@Override
	public List<QuestionTags> queryQuestionTagsList(int questionId) {
		return dao.queryQuestionTagsList(questionId);
	}

	@Override
	public PageInfo<Map<String,Object>> querQuestionAnswerLevelOne(Map<String, Object> map, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Map<String,Object>> list = dao.querQuestionAnswerLevelOne(map);
		PageInfo<Map<String,Object>> pageinfo = new PageInfo<Map<String,Object>>(list);
		return pageinfo;
	}

	@Override
	public List<Map<String, Object>> querQuestionAnswerLevelTwo(Map<String, Object> map) {
		return dao.querQuestionAnswerLevelTwo(map);
	}

	@Override
	public int insertQuestionAnswer(QuestionAnswer answer) {
		return dao.insertQuestionAnswer(answer);
	}

	@Override
	public int cancleAdopt(int questionId) {
		return dao.cancleAdopt(questionId);
	}

	@Override
	public int reports(QuestionReports reports) {
		return dao.reports(reports);
	}

	@Override
	public void insertQuestionPic(Map<String, Object> picMap) {
		dao.insertQuestionPic(picMap);
	}
	
	@Override
	public void insertQuestionAnswerPic(Map<String, Object> picMap) {
		dao.insertQuestionAnswerPic(picMap);
	}

	@Override
	public List<Map<String, Object>> getQuestionPicList(int questionId) {
		return dao.getQuestionPicList(questionId);
	}

	@Override
	public List<Map<String, Object>> getQuestionAnswerPicList(int questionId) {
		return dao.getQuestionAnswerPicList(questionId);
	}

	@Override
	public PageInfo<QuestionInfo> queryMyAnswerQuestionList(Map<String, Object> map, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<QuestionInfo> list = dao.queryMyAnswerQuestionList(map);
		PageInfo<QuestionInfo> pageinfo = new PageInfo<QuestionInfo>(list);
		return pageinfo;
	}

	@Override
	public PageInfo<QuestionInfo> queryMyCollectionQuestionList(Map<String, Object> map, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<QuestionInfo> list = dao.queryMyCollectionQuestionList(map);
		PageInfo<QuestionInfo> pageinfo = new PageInfo<QuestionInfo>(list);
		return pageinfo;
	}
	
	@Override
	public PageInfo<QuestionInfo> queryReportQuestionList(Map<String, Object> map, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<QuestionInfo> list = dao.queryReportQuestionList(map);
		PageInfo<QuestionInfo> pageinfo = new PageInfo<QuestionInfo>(list);
		return pageinfo;
	}



}
