package me.quydo.androidkeytool.util;

import java.security.MessageDigest;
import java.util.Formatter;

/**
 * 
 * @author quydm
 *
 */
public class HashUtils {

	public static String md5(String message) {
		return hashString(message, "MD5");
	}

	public static String md5(byte[] data) {
		return hashString(data, "MD5");
	}

	public static String sha1(String message) {
		return hashString(message, "SHA-1");
	}

	public static String sha1(byte[] data) {
		return hashString(data, "SHA-1");
	}

	public static String sha256(String message) {
		return hashString(message, "SHA-256");
	}

	public static String sha256(byte[] data) {
		return hashString(data, "SHA-256");
	}

	private static String hashString(String str, String algorithm) {
		return hashString(str.getBytes(), algorithm);
	}

	private static String hashString(byte[] data, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(data);
			return byteToHex(hashedBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String byteToHex(byte[] data) {
		Formatter formatter = new Formatter();
		for (byte b : data)
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
