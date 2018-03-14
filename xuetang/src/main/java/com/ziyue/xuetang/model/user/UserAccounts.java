package com.ziyue.xuetang.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserAccounts {
    private Integer id;

    private Integer userId;

    private String password;

    private String nikeName;

    private String authCode;

    private String headUrl;

    private String role;

    private String status;

    private String favSectionId;

    private String contrbution;

    private String contrQuo;

    private String wx;
    
    private String qq;

    private String alipay;

    private String phone;

    private String email;

    private String school;

    private String company;

    private String sex;

    private String allScore;

    private String usableScore;

    private String freezeScore;

    private String rewardScore;

    private String payScore;

    private String withdrawalsScore;

    private String createTime;

    private String lastLoginIp;

    private String lastLoginTime;

    private String updateTime;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName == null ? null : nikeName.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl == null ? null : headUrl.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFavSectionId() {
        return favSectionId;
    }

    public void setFavSectionId(String favSectionId) {
        this.favSectionId = favSectionId == null ? null : favSectionId.trim();
    }

    public String getContrbution() {
        return contrbution;
    }

    public void setContrbution(String contrbution) {
        this.contrbution = contrbution == null ? null : contrbution.trim();
    }

    public String getContrQuo() {
        return contrQuo;
    }

    public void setContrQuo(String contrQuo) {
        this.contrQuo = contrQuo == null ? null : contrQuo.trim();
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx == null ? null : wx.trim();
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay == null ? null : alipay.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAllScore() {
        return allScore;
    }

    public void setAllScore(String allScore) {
        this.allScore = allScore == null ? null : allScore.trim();
    }

    public String getUsableScore() {
        return usableScore;
    }

    public void setUsableScore(String usableScore) {
        this.usableScore = usableScore == null ? null : usableScore.trim();
    }

    public String getFreezeScore() {
        return freezeScore;
    }

    public void setFreezeScore(String freezeScore) {
        this.freezeScore = freezeScore == null ? null : freezeScore.trim();
    }

    public String getRewardScore() {
        return rewardScore;
    }

    public void setRewardScore(String rewardScore) {
        this.rewardScore = rewardScore == null ? null : rewardScore.trim();
    }

    public String getPayScore() {
        return payScore;
    }

    public void setPayScore(String payScore) {
        this.payScore = payScore == null ? null : payScore.trim();
    }

    public String getWithdrawalsScore() {
        return withdrawalsScore;
    }

    public void setWithdrawalsScore(String withdrawalsScore) {
        this.withdrawalsScore = withdrawalsScore == null ? null : withdrawalsScore.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime == null ? null : lastLoginTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
}