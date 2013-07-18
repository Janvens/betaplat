package com.jan.beta.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * provides centralized classloader lookup.
 */
public class ClassLoaderUtil extends ClassLoader {

	static String dirs[] = Env.getClassPath();
	
	public static ClassLoader getClassLoader() {
		return ClassLoaderUtil.class.getClassLoader();
	}

	public static InputStream getStream(String resource) {
		return getClassLoader().getResourceAsStream(resource);
	}

//	public static Properties getProperties(String resource) throws Exception {
//		Properties properties = new Properties();
//		try {
//			properties.load(getStream(resource));
//		} catch (IOException e) {
//			throw new Exception("couldn't load properties file '" + resource
//					+ "'", e);
//		}
//		return properties;
//	}
//
	public Class loadClassFromRepository(String className)
			throws ClassNotFoundException {
		String classFileName;
		Class clazz;
		int i;
		byte[] classBytes = null;
		classFileName = (new StringBuilder()).append(
				className.replace('.', File.separatorChar)).append(".class")
				.toString();
		clazz = null;
		i = 0;
		while (i < dirs.length) {
			File file;
			InputStream is;
			file = new File((new StringBuilder()).append(dirs[i]).append(
					File.separator).append(classFileName).toString());
			is = null;
			if (!file.exists()) {
				i++;
				continue;
			} else {
				try {
					is = new FileInputStream(file);
					if (is == null)
						break; /* Loop/switch isn't completed */
					classBytes = new byte[is.available()];
					is.read(classBytes);
					clazz = defineClass(className, classBytes, 0,
							classBytes.length);
					if (is != null) {
						try {
							is.close();
							break; /* Loop/switch isn't completed */
						} catch (IOException e) {
							System.out
									.println("SillyLoader: close input stream error");
							e.printStackTrace();
						}
					}
				} catch (FileNotFoundException ex) {
					System.out.println("SillyLoader: reading class file error");
					ex.printStackTrace();
				} catch (IOException e) {
					if (is != null) {
						try {
							is.close();
							break; /* Loop/switch isn't completed */
						} catch (IOException e1) {
							System.out
									.println("SillyLoader: close input stream error");
							e1.printStackTrace();
						}
					}
				}
			}
			i++;
		}
		if (clazz == null)
			clazz = findSystemClass(className);
		if (clazz == null)
			throw new ClassNotFoundException(className);
		else
			return clazz;
	}
}
