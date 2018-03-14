package com.ziyue.xuetang.constant;

/**
 *  
 * 
 
 *
 */
public enum PlatformType {

	web(0),

	android(1),

	ios(2);

	private int value;

	private PlatformType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
