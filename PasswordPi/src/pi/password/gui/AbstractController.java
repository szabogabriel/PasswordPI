package pi.password.gui;

import pi.password.Main;
import pi.password.service.alphabet.AlphabetService;
import pi.password.service.gui.DialogService;
import pi.password.service.hat.KeyInputService;
import pi.password.service.keyboard.KeyboardService;
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
	
	protected AlphabetService getPasswordAlphabet() {
		return Main.getInstance(AlphabetService.class);
	}
	
	public void activate() {
		Main.getInstance(KeyInputService.class).setButtonHandler(this);
		activateHandler();
	}
	
	public abstract void activateHandler();
	
	public abstract void reactivateHandler();
	
	public void handleButtonAPressed() {
	}
	
	public void handleButtonBPressed() {
	}
	
	public void handleButtonCPressed() {
	}
	
	public void handleJoystickUpPressed() {
	}
	
	public void handleJoystickDownPressed() {
	}
	
	public void handleJoystickLeftPressed() {
	}
	
	public void handleJoystickRightPressed() {
	}
	
	public void handleJoystickCenterPressed() {
	}

	public void handleButtonAReleased() {
	}
	
	public void handleButtonBReleased() {
	}
	
	public void handleButtonCReleased() {
	}
	
	public void handleJoystickUpReleased() {
	}
	
	public void handleJoystickDownReleased() {
	}
	
	public void handleJoystickLeftReleased() {
	}
	
	public void handleJoystickRightReleased() {
	}
	
	public void handleJoystickCenterReleased() {
	}
}
