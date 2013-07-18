package com.jan.beta.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	private static Log _log = LogFactory.getLog(FileUtil.class);

	/**
	 * 检测文件是否存在
	 * @param String filePathAndName 文件地址
	 * @return boolean true存在
	 */
	public static boolean isExist(String filePathAndName) {
		File myFilePath = new File(filePathAndName);
		// 判断文件是否存在
		if(myFilePath.exists() ) {
			_log.error(filePathAndName + " the file is exist!");
			return true;
		}
		_log.error(filePathAndName + " the file not exist!");
		return false;
	}

	public static void WriteFile(String fileName, byte data[]) {
		try {
			File f = new File(fileName);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException filenotfoundexception) {
		} catch (IOException ioexception) {
		}
	}

	public static byte[] getFileContent(String fileName) {
		byte data[] = (byte[]) null;
		FileInputStream fis = null;
		try {
			File f = new File(fileName);
			data = new byte[(int) f.length()];
			fis = new FileInputStream(f);
			fis.read(data);
			fis.close();
		} catch (IOException e) {
			return null;
		}
		return data;
	}

	public static void main(String[] args) {
		FileUtil.isExist("c:/fqf.txt");
	}
}
