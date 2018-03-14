package com.ziyue.xuetang.out.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @描述：用于输出个人信息
 *
 * @author 作者 : huang_kangjie
 * @date 创建时间：2018年1月10日
 * @version v1.0.
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class OutUserInfo {
	
	String logo = "";
	String role = "0";			//0普通	1管理员
	String friendShip = "0";	//是否好友
	String contrbution = "0"; 	//贡献值
	String rewardScore = "0";	//
	String payScore = "0";
	private String allScore = "0";	//总共学币

    private String usableScore = "0";	//可用学币

    private String freezeScore = "0";	//冻结学币

    private String withdrawalsScore = "0"; //提现学币

    private Integer userId;

    private String nickName;

    @JsonProperty(value = "logo")
    private String headUrl;

    private String contrQuo;

    private String wx;

    private String alipay;

    private String phone;

    private String email;

    private String school;

    private String company;

    private String sex;

    private String createTime;

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFriendShip() {
		return friendShip;
	}

	public void setFriendShip(String friendShip) {
		this.friendShip = friendShip;
	}

	public String getContrbution() {
		return contrbution;
	}

	public void setContrbution(String contrbution) {
		this.contrbution = contrbution;
	}

	public String getRewardScore() {
		return rewardScore;
	}

	public void setRewardScore(String rewardScore) {
		this.rewardScore = rewardScore;
	}

	public String getPayScore() {
		return payScore;
	}

	public void setPayScore(String payScore) {
		this.payScore = payScore;
	}

	public String getAllScore() {
		return allScore;
	}

	public void setAllScore(String allScore) {
		this.allScore = allScore;
	}

	public String getUsableScore() {
		return usableScore;
	}

	public void setUsableScore(String usableScore) {
		this.usableScore = usableScore;
	}

	public String getFreezeScore() {
		return freezeScore;
	}

	public void setFreezeScore(String freezeScore) {
		this.freezeScore = freezeScore;
	}

	public String getWithdrawalsScore() {
		return withdrawalsScore;
	}

	public void setWithdrawalsScore(String withdrawalsScore) {
		this.withdrawalsScore = withdrawalsScore;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getContrQuo() {
		return contrQuo;
	}

	public void setContrQuo(String contrQuo) {
		this.contrQuo = contrQuo;
	}

	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
