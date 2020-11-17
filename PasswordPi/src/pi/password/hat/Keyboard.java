package pi.password.hat;

import com.waveshare.Hat;
import com.waveshare.keyboard.KeyboardHat;
import com.waveshare.keyboard.KeyboardHat.Keys;

import pi.password.hat.handlers.ButtonHandler;

public enum Keyboard {

	INSTANCE;
	
	private KeyboardHat hat = Hat.INSTANCE.keyboardHat;
	
	private ButtonHandler handler;
	
	private Keyboard() {
		hat.setListener(Keys.KEY_A, S -> { if (S.isLow()) getButonHandler().handleButtonAPressed(); });
		hat.setListener(Keys.KEY_B, S -> { if (S.isLow()) getButonHandler().handleButtonBPressed(); });
		hat.setListener(Keys.KEY_C, S -> { if (S.isLow()) getButonHandler().handleButtonCPressed(); });
		hat.setListener(Keys.JOYSTICK_UP, S -> { if (S.isLow()) getButonHandler().handleJoystickUpPressed(); });
		hat.setListener(Keys.JOYSTICK_DOWN, S -> { if (S.isLow()) getButonHandler().handleJoystickDownPressed(); });
		hat.setListener(Keys.JOYSTICK_LEFT, S -> { if (S.isLow()) getButonHandler().handleJoysticlLeftPressed(); });
		hat.setListener(Keys.JOYSTICK_RIGHT, S -> { if (S.isLow()) getButonHandler().handleJoystickRightPressed(); });
		hat.setListener(Keys.JOYSTICK_CENTER, S -> { if (S.isLow()) getButonHandler().handleJoystickCenterPressed(); });
	}

	private ButtonHandler getButonHandler() {
		return handler;
	}
	
	public void setButtonHandler(ButtonHandler buttonHandler) {
		this.handler = buttonHandler;
	}
}
