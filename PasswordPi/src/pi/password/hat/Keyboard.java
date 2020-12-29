package pi.password.hat;

import com.waveshare.keyboard.HatKey;
import com.waveshare.keyboard.HatKeyboard;
import com.waveshare.keyboard.listener.KeyInputListener;

import pi.password.Main;
import pi.password.gui.AbstractController;

public enum Keyboard {

	INSTANCE;
	
	private HatKeyboard hat = Main.DI.getServiceImpl(HatKeyboard.class).get();
	
	private AbstractController handler;
	
	private Keyboard() {
		hat.setListener(new KeyInputListener() {
			
			@Override
			public void keyReleased(HatKey key) {
				switch (key) {
				case JOYSTICK_CENTER: getButtonHandler().handleJoystickCenterReleased(); break;
				case JOYSTICK_DOWN: getButtonHandler().handleJoystickDownReleased(); break;
				case JOYSTICK_LEFT: getButtonHandler().handleJoystickLeftReleased(); break;
				case JOYSTICK_RIGHT: getButtonHandler().handleJoystickRightReleased(); break;
				case JOYSTICK_UP: getButtonHandler().handleJoystickUpReleased(); break;
				case KEY_A: getButtonHandler().handleButtonAReleased(); break;
				case KEY_B: getButtonHandler().handleButtonBReleased(); break;
				case KEY_C: getButtonHandler().handleButtonCReleased(); break;
				default: break;
				}
			}
			
			@Override
			public void keyPressed(HatKey key) {
				switch (key) {
				case JOYSTICK_CENTER: getButtonHandler().handleJoystickCenterPressed(); break;
				case JOYSTICK_DOWN: getButtonHandler().handleJoystickDownPressed(); break;
				case JOYSTICK_LEFT: getButtonHandler().handleJoystickLeftPressed(); break;
				case JOYSTICK_RIGHT: getButtonHandler().handleJoystickRightPressed(); break;
				case JOYSTICK_UP: getButtonHandler().handleJoystickUpPressed(); break;
				case KEY_A: getButtonHandler().handleButtonAPressed(); break;
				case KEY_B: getButtonHandler().handleButtonBPressed(); break;
				case KEY_C: getButtonHandler().handleButtonCPressed(); break;
				default: break;
				}
			}
		});
	}
	
	private AbstractController getButtonHandler() {
		return handler;
	}
	
	public void setButtonHandler(AbstractController buttonHandler) {
		this.handler = buttonHandler;
	}
}
