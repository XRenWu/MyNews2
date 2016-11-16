package com.l000phone.util;

/**
 * Created by Administrator on 2016/10/28.
 */

public class StringUtils {
	public static String CutStringGetImgUrl(String str){
		int start = str.indexOf("src");
		String substring = str.substring(start + 5);
		int end = substring.indexOf("><");
		String substring1 = substring.substring(0, end - 1);
		return substring1;
	}
}
