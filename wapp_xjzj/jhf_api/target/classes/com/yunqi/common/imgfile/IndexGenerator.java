package com.yunqi.common.imgfile;

public class IndexGenerator {

	private static int index = 1;

	public static String getIndexStr() {
		index++;
		if (index >= 1000) {
			index = 100;
		}
		if (index < 100) {
			index = 100;
		}
		return String.valueOf(index);
	}

	public static int getIndex() {
		index++;
		if (index >= 1000) {
			index = 100;
		}
		if (index < 100) {
			index = 100;
		}
		return index;
	}

}
