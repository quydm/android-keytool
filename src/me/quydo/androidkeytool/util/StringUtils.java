package me.quydo.androidkeytool.util;

/**
 * 
 * @author quydm
 *
 */
public class StringUtils {

	public static String addColonAndUpperCase(String str) {
		String[] result = str.split("(?<=\\G.{2})");
		StringBuilder sb = new StringBuilder();
		int length = result.length - 1;
		for (int i = 0; i < length; i++)
			sb.append(result[i]).append(":");
		sb.append(result[length]);
		return sb.toString().toUpperCase();
	}

}
