package com.demo.back.controller.browser.widgetkey.request;

import com.demo.back.common.page.PageRequest;

public class WidgetKeyRequest extends PageRequest {


	private String keyName;

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
