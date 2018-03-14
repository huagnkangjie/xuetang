package com.ziyue.xuetang.utils.mail;

import java.io.Serializable;

/**
 * 
 * @author huangkangjie
 *
 */
public class MailVo {
	
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	private String receiver;

	private String subject;

	private String content;

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
