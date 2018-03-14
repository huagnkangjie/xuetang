package com.ziyue.xuetang.presentation.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import com.ziyue.xuetang.common.message.Messages;
import com.ziyue.xuetang.constant.Status;
import com.ziyue.xuetang.exception.ValidateException;

public class BaseParamAction extends BaseAction {

	public void checkPlatform(HttpServletRequest req) {

		String platform = req.getParameter("platform");

		if (StringUtils.isEmpty(platform)) {

			throw new ValidateException(Status.R_10013, Messages.getString(Status.R_10013 + "","platform"));

		}

		platform = platform.toLowerCase();
		if (!(platform.equals("android") || platform.equals("ios"))) {
			throw new ValidateException(Status.R_20002, Messages.getString(Status.R_20002 + "", platform));

		}

	}

}
