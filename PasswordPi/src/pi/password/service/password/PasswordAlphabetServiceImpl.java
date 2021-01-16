package pi.password.service.password;

import com.jdi.ConfigService;

import pi.password.config.RuntimeConfig;

public class PasswordAlphabetServiceImpl implements PasswordAlphabetService {
	
	private final ConfigService CONFIG;
	private final char [] PASSWORD_ALPHABET;
	
	public PasswordAlphabetServiceImpl(ConfigService config) {
		CONFIG = config;
		PASSWORD_ALPHABET = CONFIG.get(RuntimeConfig.PASSWORD_ALPHABET.getKey()).orElse("").toCharArray();
	}

	@Override
	public char getPrevChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = PASSWORD_ALPHABET[PASSWORD_ALPHABET.length - 1];
		} else {
			ret = PASSWORD_ALPHABET[((--charOrderInAlphabet) + PASSWORD_ALPHABET.length) % PASSWORD_ALPHABET.length];
		}
		
		return ret;
	}

	@Override	
	public char getNextChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = PASSWORD_ALPHABET[0];
		} else {
			ret = PASSWORD_ALPHABET[++charOrderInAlphabet % PASSWORD_ALPHABET.length];
		}
		
		return ret;
	}
	
	private int getCharOrderInAlphabet(char currentChar) {
		int charOrderInAlphabet = 0;
		while (charOrderInAlphabet < PASSWORD_ALPHABET.length && PASSWORD_ALPHABET[charOrderInAlphabet] != currentChar)
			charOrderInAlphabet++;
		
		if (charOrderInAlphabet == PASSWORD_ALPHABET.length) {
			charOrderInAlphabet = -1;
		}
		return charOrderInAlphabet;
	}

	@Override
	public char[] getPasswordAlphabet() {
		return PASSWORD_ALPHABET;
	}

}
