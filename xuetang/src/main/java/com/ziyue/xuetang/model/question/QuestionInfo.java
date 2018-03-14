package com.ziyue.xuetang.model.question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class QuestionInfo {
    private Integer id;

    private Integer userId;

    private Integer boardId;

    private String tags;

    private String type;

    private String onStat;

    private String title;

    private String content;

    private String markdownCont;

    private Integer rewardCount;

    private Integer answerCount;

    private Integer someoneAnswerCount;

    private String pictures;

    private String collectionCount;

    private String isDelete;

    private String reportType;

    private String status;

    private Integer adoptUserId;

    private Integer adoptAnswerId;

    private String fristAnswerTime;

    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOnStat() {
        return onStat;
    }

    public void setOnStat(String onStat) {
        this.onStat = onStat == null ? null : onStat.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMarkdownCont() {
        return markdownCont;
    }

    public void setMarkdownCont(String markdownCont) {
        this.markdownCont = markdownCont == null ? null : markdownCont.trim();
    }

    public Integer getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(Integer rewardCount) {
        this.rewardCount = rewardCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getSomeoneAnswerCount() {
        return someoneAnswerCount;
    }

    public void setSomeoneAnswerCount(Integer someoneAnswerCount) {
        this.someoneAnswerCount = someoneAnswerCount;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
    }

    public String getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        this.collectionCount = collectionCount == null ? null : collectionCount.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType == null ? null : reportType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getAdoptUserId() {
        return adoptUserId;
    }

    public void setAdoptUserId(Integer adoptUserId) {
        this.adoptUserId = adoptUserId;
    }

    public Integer getAdoptAnswerId() {
        return adoptAnswerId;
    }

    public void setAdoptAnswerId(Integer adoptAnswerId) {
        this.adoptAnswerId = adoptAnswerId;
    }

    public String getFristAnswerTime() {
        return fristAnswerTime;
    }

    public void setFristAnswerTime(String fristAnswerTime) {
        this.fristAnswerTime = fristAnswerTime == null ? null : fristAnswerTime.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}