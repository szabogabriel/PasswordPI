package pi.password.service.alphabet;

import com.jdi.ConfigService;

import pi.password.config.RuntimeConfig;

public class AlphabetServiceImpl implements AlphabetService {
	
	private final ConfigService CONFIG;
	private final char [] PASSWORD_ALPHABET;
	private final char [] REGULAR_ALPHABET;
	
	public AlphabetServiceImpl(ConfigService config) {
		CONFIG = config;
		PASSWORD_ALPHABET = CONFIG.get(RuntimeConfig.ALPHABET_PASSWORD.getKey()).orElse("").toCharArray();
		REGULAR_ALPHABET = CONFIG.get(RuntimeConfig.ALPHABET_REGULAR.getKey()).orElse("").toCharArray();
	}

	@Override
	public char getPasswordPrevChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(PASSWORD_ALPHABET, currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = PASSWORD_ALPHABET[PASSWORD_ALPHABET.length - 1];
		} else {
			ret = PASSWORD_ALPHABET[((--charOrderInAlphabet) + PASSWORD_ALPHABET.length) % PASSWORD_ALPHABET.length];
		}
		
		return ret;
	}

	@Override	
	public char getPasswordNextChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(PASSWORD_ALPHABET, currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = PASSWORD_ALPHABET[0];
		} else {
			ret = PASSWORD_ALPHABET[++charOrderInAlphabet % PASSWORD_ALPHABET.length];
		}
		
		return ret;
	}
	
	@Override
	public char getRegularPrevChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(REGULAR_ALPHABET, currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = REGULAR_ALPHABET[REGULAR_ALPHABET.length - 1];
		} else {
			ret = REGULAR_ALPHABET[((--charOrderInAlphabet) + REGULAR_ALPHABET.length) % REGULAR_ALPHABET.length];
		}
		
		return ret;
	}

	@Override
	public char getRegularNextChar(char currentChar) {
		int charOrderInAlphabet = getCharOrderInAlphabet(REGULAR_ALPHABET, currentChar);
		char ret;
		
		if (charOrderInAlphabet == -1) {
			ret = REGULAR_ALPHABET[0];
		} else {
			ret = REGULAR_ALPHABET[++charOrderInAlphabet % REGULAR_ALPHABET.length];
		}
		
		return ret;
	}
	
	@Override
	public char[] getPasswordAlphabet() {
		return PASSWORD_ALPHABET;
	}
	
	@Override
	public char[] getRegularAlphabet() {
		return REGULAR_ALPHABET;
	}
	
	private int getCharOrderInAlphabet(char[] alphabet, char currentChar) {
		int charOrderInAlphabet = 0;
		while (charOrderInAlphabet < alphabet.length && alphabet[charOrderInAlphabet] != currentChar)
			charOrderInAlphabet++;
		
		if (charOrderInAlphabet == alphabet.length) {
			charOrderInAlphabet = -1;
		}
		return charOrderInAlphabet;
	}

}
