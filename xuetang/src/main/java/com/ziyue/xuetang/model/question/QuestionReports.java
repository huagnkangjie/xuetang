package com.ziyue.xuetang.model.question;
/**
 * @描述：举报
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2017年12月25日
 * @version v1.0.
 * 
 */
public class QuestionReports {

	
	private int id;
	private String reportType;
	private int questionId;
	private int answerId;
	private int userId;
	private String content;

	private String type;
	private int doUserId;
	private int doType;
	private String createTime;
	private String doTime;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDoUserId() {
		return doUserId;
	}
	public void setDoUserId(int doUserId) {
		this.doUserId = doUserId;
	}
	public int getDoType() {
		return doType;
	}
	public void setDoType(int doType) {
		this.doType = doType;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDoTime() {
		return doTime;
	}
	public void setDoTime(String doTime) {
		this.doTime = doTime;
	}
	
	
	
	
	
}
