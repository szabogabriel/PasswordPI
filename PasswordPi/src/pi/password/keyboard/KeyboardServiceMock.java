package pi.password.keyboard;

public class KeyboardServiceMock implements KeyboardService {
	
	@Override
	public boolean sendText(String text) {
		System.out.println(text);
		return true;
	}

}
