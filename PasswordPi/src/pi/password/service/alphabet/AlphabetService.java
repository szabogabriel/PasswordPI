package pi.password.service.alphabet;

public interface AlphabetService {
	
	char getPasswordPrevChar(char currentChar);
	
	char getPasswordNextChar(char currentChar);
	
	char getRegularPrevChar(char currentChar);
	
	char getRegularNextChar(char currentChar);
	
	char[] getPasswordAlphabet();
	
	char[] getRegularAlphabet();

}
