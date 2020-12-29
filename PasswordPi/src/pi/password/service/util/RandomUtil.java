package pi.password.service.util;

public class RandomUtil {
	
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	private static final int SALT_LENGTH = 8;
	
	public String getSalt() {
		StringBuilder ret = new StringBuilder();
		
		for (int i = 0; i < SALT_LENGTH; i++) {
			ret.append(ALPHABET.charAt(((int)(Math.random() * ALPHABET.length()))));
		}
		
		return ret.toString();
	}

}
