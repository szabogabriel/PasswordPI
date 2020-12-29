package pi.password.gui;

import pi.password.Main;
import pi.password.service.hat.KeyInputService;
import pi.password.service.keyboard.KeyboardService;
import pi.password.service.password.PasswordVaultService;

public abstract class AbstractController {
	
	protected KeyboardService getKeyboardService() {
		return Main.DI.getServiceImpl(KeyboardService.class).get();
	}
	
	protected PasswordVaultService getPasswordVault() {
		return Main.DI.getServiceImpl(PasswordVaultService.class).get();
	}
	
	public void activate() {
		Main.DI.getServiceImpl(KeyInputService.class).get().setButtonHandler(this);
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
