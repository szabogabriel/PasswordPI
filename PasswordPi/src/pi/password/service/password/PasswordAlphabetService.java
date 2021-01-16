package pi.password.service.password;

public interface PasswordAlphabetService {
	
	char getPrevChar(char currentChar);
	
	char getNextChar(char currentChar);
	
	char[] getPasswordAlphabet();

}
