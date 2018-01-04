package me.quydo.androidkeytool.util.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import me.quydo.androidkeytool.util.StringUtils;

public class StringUtilsTest {

	@Test
	public void testOneCharacter() {
		assertEquals("A", StringUtils.addColonAndUpperCase("a"));
		assertEquals("A", StringUtils.addColonAndUpperCase("A"));
		assertEquals("1", StringUtils.addColonAndUpperCase("1"));
		assertEquals("@", StringUtils.addColonAndUpperCase("@"));
	}

	@Test
	public void testTwoCharacters() {
		assertEquals("AB", StringUtils.addColonAndUpperCase("ab"));
		assertEquals("AB", StringUtils.addColonAndUpperCase("Ab"));
		assertEquals("AB", StringUtils.addColonAndUpperCase("aB"));
		assertEquals("AB", StringUtils.addColonAndUpperCase("AB"));
		assertEquals("1A", StringUtils.addColonAndUpperCase("1a"));
		assertEquals("1A", StringUtils.addColonAndUpperCase("1A"));
		assertEquals("A1", StringUtils.addColonAndUpperCase("a1"));
		assertEquals("A1", StringUtils.addColonAndUpperCase("A1"));
		assertEquals("@#", StringUtils.addColonAndUpperCase("@#"));
	}

	@Test
	public void testThreeCharacters() {
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("abc"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("Abc"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("aBc"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("abC"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("ABc"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("aBC"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("AbC"));
		assertEquals("AB:C", StringUtils.addColonAndUpperCase("ABC"));
		assertEquals("AB:1", StringUtils.addColonAndUpperCase("ab1"));
		assertEquals("AB:@", StringUtils.addColonAndUpperCase("ab@"));
		assertEquals("A@:B", StringUtils.addColonAndUpperCase("a@b"));
	}

	@Test
	public void testFourCharacters() {
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("abcd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("Abcd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("aBcd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("abCd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("abcD"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("ABcd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("aBCd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("abCD"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("AbcD"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("ABCd"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("aBCD"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("AbCD"));
		assertEquals("AB:CD", StringUtils.addColonAndUpperCase("ABCD"));
	}

	@Test
	public void testFiveCharacters() {
		assertEquals("AB:CD:E", StringUtils.addColonAndUpperCase("abcde"));
		assertEquals("AB:CD:E", StringUtils.addColonAndUpperCase("ABCDE"));
	}

	@Test
	public void testEmptyCharacter() {
		assertEquals("", StringUtils.addColonAndUpperCase(""));
	}

}
