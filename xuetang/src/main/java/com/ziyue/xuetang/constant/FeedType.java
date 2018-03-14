package com.ziyue.xuetang.constant;

public class FeedType {
	public final static int COMPANY_CREATE_LIVE_TRAILER = 100;// 发布企业宣讲会预告(100
	public final static int COMPANY_CREATE_VIDEO = 120;// 发布企业点播(120
	public final static int COMPANY_CREATE_LIVE = 125;// 发布企业直播(125

	public final static int COMPANY_CREATE_POSITION = 130;// 发布企业职位(130

	public final static int SCHOOL_CREATE_LIVE_TRAILER = 140;// 发布院校预告(140
	public final static int SCHOOL_CREATE_VIDEO = 150;// 发布院校点播(150
	public final static int SCHOOL_CREATE_LIVE = 155; // 发布院校直播(155

	public final static int RESUME_BASIC = 160; // 学生基本信息(160
	public final static int RESUME_CONTACT = 170; // 学生联系信息(170
	public final static int RESUME_EDUCATION = 180;// 教育背景(180
	public final static int RESUME_CAREER = 190; // 职业背景(190
	public final static int RESUME_JOB_INTEREST = 200; // 求职意向-感兴趣职位(200
	public final static int RESUME_JOB_AREA = 210;// 求职意向-意向工作地区(210
	public final static int RESUME_JOB_DOMAIN = 220; // 求职意向-意向工作领域(220

	public final static int RESUME_EMPLOYMENT_TYPE = 230; // 聘用类型(230
	public final static int RESUME_LETTER = 240; // 求职信(240
	public final static int RESUME_KEYWORD = 250; // 个人关键词(250
	public final static int RESUME_LANGUAGE = 260; // 语言能力(260

	public enum ResourceTable {

		position_info("position_info"), live_info("live_info"), resume_opt_log("resume_opt_log"), resume_info(
				"resume_info"), resume_like_job_area("resume_like_job_area"), resume_like_job_type(
						"resume_like_job_type"), resume_hire_type("resume_hire_type"), resume_language(
								"resume_language"), resume_education_background(
										"resume_education_background"), resume_job_target_background(
												"resume_job_target_background"), resume_like_industry(
														"resume_like_industry"), resume_like_job_position(
																"resume_like_job_position");

		private String value;

		ResourceTable(String value) {
			this.value = value;

		}

		public String getValue() {
			return value;
		}

	}

}
