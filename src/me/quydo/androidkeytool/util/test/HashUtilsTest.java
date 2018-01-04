package me.quydo.androidkeytool.util.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import me.quydo.androidkeytool.util.HashUtils;

public class HashUtilsTest {

	@Test
	public void testMd5() {
		String input = "123";
		String expected = "202cb962ac59075b964b07152d234b70";
		assertEquals(expected, HashUtils.md5(input));
		assertEquals(expected, HashUtils.md5(input.getBytes()));
	}

	@Test
	public void testMd5Empty() {
		String input = "";
		String expected = "d41d8cd98f00b204e9800998ecf8427e";
		assertEquals(expected, HashUtils.md5(input));
		assertEquals(expected, HashUtils.md5(input.getBytes()));
	}

	@Test
	public void testSha1() {
		String input = "123";
		String expected = "40bd001563085fc35165329ea1ff5c5ecbdbbeef";
		assertEquals(expected, HashUtils.sha1(input));
		assertEquals(expected, HashUtils.sha1(input.getBytes()));
	}

	@Test
	public void testSha1Empty() {
		String input = "";
		String expected = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
		assertEquals(expected, HashUtils.sha1(input));
		assertEquals(expected, HashUtils.sha1(input.getBytes()));
	}

	@Test
	public void testSha256() {
		String input = "123";
		String expected = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
		assertEquals(expected, HashUtils.sha256(input));
		assertEquals(expected, HashUtils.sha256(input.getBytes()));
	}

	@Test
	public void testSha256Empty() {
		String input = "";
		String expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
		assertEquals(expected, HashUtils.sha256(input));
		assertEquals(expected, HashUtils.sha256(input.getBytes()));
	}

}
