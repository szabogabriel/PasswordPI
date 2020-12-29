package pi.password.hat;

import pi.password.Main;
import pi.password.keyboard.KeyboardService;
import pi.password.manager.PasswordVault;

public abstract class AbstractController {
	
	protected KeyboardService getKeyboardService() {
		return Main.SERVICE_FACTORY.getServiceImpl(KeyboardService.class).get();
	}
	
	protected PasswordVault getPasswordVault() {
		return Main.SERVICE_FACTORY.getServiceImpl(PasswordVault.class).get();
	}
	
	public void activate() {
		Keyboard.INSTANCE.setButtonHandler(this);
		activateHandler();
	}
	
	public abstract void activateHandler();
	
	public abstract void handleButtonAPressed();
	
	public abstract void handleButtonBPressed();
	
	public abstract void handleButtonCPressed();
	
	public abstract void handleJoystickUpPressed();
	
	public abstract void handleJoystickDownPressed();
	
	public abstract void handleJoystickLeftPressed();
	
	public abstract void handleJoystickRightPressed();
	
	public abstract void handleJoystickCenterPressed();

	public abstract void handleButtonAReleased();
	
	public abstract void handleButtonBReleased();
	
	public abstract void handleButtonCReleased();
	
	public abstract void handleJoystickUpReleased();
	
	public abstract void handleJoystickDownReleased();
	
	public abstract void handleJoystickLeftReleased();
	
	public abstract void handleJoystickRightReleased();
	
	public abstract void handleJoystickCenterReleased();
}
