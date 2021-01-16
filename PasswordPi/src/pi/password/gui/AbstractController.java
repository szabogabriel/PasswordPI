package pi.password.gui;

import pi.password.Main;
import pi.password.service.dialog.DialogService;
import pi.password.service.hat.KeyInputService;
import pi.password.service.keyboard.KeyboardService;
import pi.password.service.password.PasswordAlphabetService;
import pi.password.service.password.PasswordVaultService;

public abstract class AbstractController {
	
	protected KeyboardService getKeyboardService() {
		return Main.getInstance(KeyboardService.class);
	}
	
	protected DialogService getDialogService() {
		return Main.getInstance(DialogService.class);
	}
	
	protected PasswordVaultService getPasswordVault() {
		return Main.getInstance(PasswordVaultService.class);
	}
	
	protected PasswordAlphabetService getPasswordAlphabet() {
		return Main.getInstance(PasswordAlphabetService.class);
	}
	
	public void activate() {
		Main.getInstance(KeyInputService.class).setButtonHandler(this);
		activateHandler();
	}
	
	public abstract void activateHandler();
	
	public abstract void reactivateHandler();
	
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
