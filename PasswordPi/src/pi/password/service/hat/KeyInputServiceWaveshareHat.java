package pi.password.service.hat;

import com.waveshare.keyboard.HatKey;
import com.waveshare.keyboard.HatKeyboard;
import com.waveshare.keyboard.listener.KeyInputListener;

import pi.password.Main;
import pi.password.gui.AbstractController;

public class KeyInputServiceWaveshareHat implements KeyInputService {

	private HatKeyboard hat = Main.getInstance(HatKeyboard.class);
	
	private AbstractController handler;
	
	public KeyInputServiceWaveshareHat() {
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
	
	@Override
	public void setButtonHandler(AbstractController buttonHandler) {
		this.handler = buttonHandler;
	}
}
