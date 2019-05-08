package com.yunqi.common.jackons;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class CustomObjectMapper extends ObjectMapper {

	public CustomObjectMapper() {
		this.setDateFormat(new MyDateFormat());
	}
}