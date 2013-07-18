package com.jan.beta.util;

import java.io.IOException;

public class SerialUtil {

	public static String getSerial() {
		return getSerialFromFile("PM", 28);
	}
	/**
	 * 自动生成商户编号
	 * @return
	 */
	public static String getMIDSerial() {
		return "B" + getSerialFromFile("MID", 15);
	}

	public static String getSerialFromFile(String serialName, int length) {
		long serialNo = Serial.next(serialName);
		String serialNoStr = String.valueOf(serialNo);
		return formatSerial(serialNoStr, length);
	}

	static String formatSerial(String serialNo, int length) {
		Integer prefixNum = length - serialNo.length();
		if (prefixNum < 0)
			throw new IllegalStateException();
		StringBuffer prefixString = new StringBuffer();
		for (int i = 0; i < prefixNum; i++) {
			prefixString.append("0");
		}
		return prefixString.toString() + serialNo;
	}

	public static void main(String args[]) throws IOException {
//		System.out.println(getSerial());
		System.out.println(getMIDSerial());
	}
}
