package com.yunqi.common.json;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class JsonPojo extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public JsonPojo(Object obj) {
		this(obj, true);
	}

	public JsonPojo(Object obj, boolean outputNullAsEmpty) {
		super();
		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(
					obj.getClass()).getPropertyDescriptors()) {
				if (pd.getReadMethod() == null || "class".equals(pd.getName())) {
					continue;
				}
				String name = pd.getName();
				Object value = pd.getReadMethod().invoke(obj);
				if (outputNullAsEmpty && value == null) {
					value = new String("");
				}
				if (name != null && value != null) {
					super.put(pd.getName(), value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ext(String key, String val) {
		if (StringUtils.isEmpty(key)) {
			return;
		}
		if (this.containsKey(key)) {
			super.remove(key);
		}
		if (val == null) {
			val = "";
		}
		super.put(key, val);
	}

}
