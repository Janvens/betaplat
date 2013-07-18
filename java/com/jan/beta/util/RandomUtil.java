package com.jan.beta.util;

import java.util.Random;


public class RandomUtil {

	public static String getRandom(int len){
		Random random = new Random();
		int len0 = 9;
		String s1 = "";
		String randStr = "";
		while(true){
			if(len > len0){
				len = len - len0;
				s1 = getRandomPart(random, len0);
				randStr = randStr + s1;
			}else{
				s1 = getRandomPart(random, len);
				randStr = randStr + s1;
				break;
			}
		}
		return randStr;
	}
	
	private static String getRandomPart(Random random, int len){
		String randStr = "";
		if(len > 1){
			int len2 = len - 1;
			String s1 = String.valueOf(random.nextInt(9)+1);
			String num = "1";
			for(int i=0; i<len2; i++){
				num = num + "0";
			}
			String s2 = String.valueOf(random.nextInt(Integer.parseInt(num)));
			while(true){
				int len3 = s2.length();
				if(len3 < len2){
					s2 = "0" + s2;
				}else{
					break;
				}
			}
			randStr = s1 + s2;
		}else if(len == 1){
			int m = random.nextInt(10);
			randStr = String.valueOf(m);
		}
		return randStr;
	}
	
	
	public static void main(String[] arg){
		int m = 0;
		for(int i=0; i<50; i++){
			System.out.println(RandomUtil.getRandom(m));
		}
	}

}
