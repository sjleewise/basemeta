package kr.wise.commons.util;

import java.util.Random;

public class RandomTextGenerator {

	private static final String [] KEY_CHARS = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};
	
	public static String get(int limit) {
		String str = "";
		Random random = new Random();
		int n = 0;
		for(int i=0; i<limit; i++) {
			n = random.nextInt(KEY_CHARS.length);
			str += KEY_CHARS [n];
		}
		return str;
	}
	
	public static void main(String [] args) {
		System.out.println(get(10));
	}
	
}
