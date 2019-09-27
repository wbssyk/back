package com.demo.back.controller.browser.widgetupgrade.request;

import com.demo.back.common.page.PageRequest;

@SuppressWarnings("serial")
public class WidgetUpgradeRequest extends PageRequest{

	//控件升级包名
	private String widgetName;
	//应用类型 1：key驱动，2：签名控件
	private String appType;

	public String getWidgetName() {
		return widgetName;
	}

	public void setWidgetName(String widgetName) {
		this.widgetName = widgetName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
}
