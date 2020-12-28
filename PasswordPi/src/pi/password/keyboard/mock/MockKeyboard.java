package pi.password.keyboard.mock;

import pi.password.keyboard.KeyboardService;

public class MockKeyboard implements KeyboardService {
	
	@Override
	public boolean sendText(String text) {
		System.out.println(text);
		return true;
	}

}
