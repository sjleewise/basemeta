package kr.wise.commons;

/**
 * 키 생성용 Singleton 클래스<br>
 * 오라클 시퀀스 대체용 클래스로 주로 PK 생성시 사용된다.<br>
 * System.currentTimeMillis()를 기반으로 생성하므로 현재 13자리 값이 리턴된다.<br>
 * @author 김용훈
 */
public class UniqueKeyGenerator {

	private static UniqueKeyGenerator instance = new UniqueKeyGenerator();
	private long prevKey = 0L;
	
	private UniqueKeyGenerator() {
	}
	
	private static final UniqueKeyGenerator getInstance() {
		if(instance == null) {
			instance = new UniqueKeyGenerator();
		}
		return instance;
	}
	
	public static String getKey() {
		return String.valueOf(getInstance().getNextKey());
	}
	
	private long getNextKey() {
		long currTime = System.currentTimeMillis();
		if(prevKey >= currTime) {
			prevKey++;
		} else {
			prevKey = currTime;
		}
		return prevKey;
	}
	
	public static void main(String [] args) {
		System.out.println(UniqueKeyGenerator.getKey());
		System.out.println(UniqueKeyGenerator.getKey());
		System.out.println(UniqueKeyGenerator.getKey());
		System.out.println(UniqueKeyGenerator.getKey());
		System.out.println(UniqueKeyGenerator.getKey());
	}
}
