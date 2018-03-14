package com.ziyue.xuetang.constant;

public class Constants {

	public static String SYSTEM_CONFIG = "/config/system.properties";

	public static String INTERVIEW_TITLE = "title";
	public static String INTERVIEW_CONTENT = "content";
	

	
	/**
	 * 全文检索索引路径
	 * 
	 */
	//public static final String LUCENE_PATH = "/WEB-INF/lucene";
	//职位
	public static final String LUCENE_POSITION_PATH = "/position";
	
	//宣讲会相关
	public static final String LUCENE_LIVE_PATH = "/live";
	
	//公司
	public static final String LUCENE_COMPANY_PATH = "/company";
	
	
	
	
	
	
	public static final String CHINA_CODE="489";
	
  
	public class XinGe {
		public static final String XINGE_URL = "xinge_url";

	}

	public class MessageType {

	}
	
	
	
	

	public static final String PREFIX_ENT = "ent";

	public static final String PREFIX_STU = "stu";
	
//	public static final String TOKEN_PREFIX_ENT = "ent:";
//
//	public static final String TOKEN_PREFIX_STU = "stu:";
//	public static final String TOKEN_PREFIX_UNI = "uni:";
	
	
	
	
	public static final String TOKEN_PREFIX_ENT = "";

	public static final String TOKEN_PREFIX_STU = "";
	public static final String TOKEN_PREFIX_UNI = "";
	
	
	
	
	
	

	public static final int EXPIRES_DAY = 7;

	public static final int EXPIRES_DAY_TIME = 604800;

	public static final String DEFAULT_RESUME_NAME = "简历_";

	//

	public static final String page = "page";

	public static final String count = "count";

	public static final String TOTAL = "total";

	public static final int FOLLOW_STATUS_Y = 1;

	public static final int FOLLOW_STATUS_N = 0;
	
	
	
	
	public static final int MSG_READED = 1;

	public static final int MSG_UN_READ= 0;
	
	
	public static final int TYPE_VIEW_1= 1;

	public static final int TYPE_EXAM_2= 2;
	
	
	public static final String  UID="uid";

	public enum ProcessStatus {

		// 流程状态 0投递 1查看 2筛选通过 3筛选不通过 4邀请 5接受 6拒绝 7通过 8不通过 9 offer

		Process0(0), Process1(1), Process2(2), Process3(3), Process4(4), Process5(5), Process6(6), Process7(
				7), Process8(8), Process9(9),Process10(10),Process11(11),

		// 101-面试，2-面试提醒,3-offer,4-宣讲会
				MsgType101(101), MsgType2(2), MsgType3(3), MsgType4(4);

		private int value;

		private ProcessStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	public enum ConfigClass {

		// 行业分类 1
		// 职业分类 2
		// 薪资范围 3
		// 公司类型 4
		// 教育类型 5
		// 工作经验 6
		// 公司大小 7

		Class1("1"), Class2("2"), Class3("3"), Class4("4"), Class5("5"), Class6("6"), Class7("7");

		private String value;

		private ConfigClass(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}
}
