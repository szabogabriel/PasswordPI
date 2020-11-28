package pi.password.keyboard.mock;

import pi.password.keyboard.Keyboard;

public class MockKeyboard implements Keyboard {
	
	@Override
	public boolean sendText(String text) {
		System.out.println(text);
		return true;
	}

}
