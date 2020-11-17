package pi.password.hat.handlers;

import pi.password.hat.Display;
import pi.password.hat.Keyboard;

public abstract class ButtonHandler {
	
	public static final ButtonHandler SPLASH_SCREEN = new SplashScreenButtonHandler();
	public static final ButtonHandler SETTINGS_SCREEN = new SettingsScreenButtonHandler();
	public static final ButtonHandler VAULT_SCREEN = new VaultManageScreenButtonHandler();
	
	public void activate() {
		Keyboard.INSTANCE.setButtonHandler(this);
		activateScreen();
	}
	
	public abstract void activateScreen();
	
	public void handleButtonAPressed() {
		Display.INSTANCE.flipBacklight();
	}
	
	public abstract void handleButtonBPressed();
	
	public abstract void handleButtonCPressed();
	
	public abstract void handleJoystickUpPressed();
	
	public abstract void handleJoystickDownPressed();
	
	public abstract void handleJoysticlLeftPressed();
	
	public abstract void handleJoystickRightPressed();
	
	public abstract void handleJoystickCenterPressed();

}
