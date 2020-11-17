package pi.password.hat.handlers;

import java.awt.image.BufferedImage;

import pi.password.hat.Display;
import pi.password.hat.Keyboard;

public abstract class ButtonHandler {
	
	public static final ButtonHandler SPLASH_SCREEN = new SplashScreenButtonHandler();
	public static final ButtonHandler SETTINGS_SCREEN = new SettingsScreenButtonHandler();
	public static final ButtonHandler VAULT_SCREEN = new VaultManageScreenButtonHandler();
	public static final ButtonHandler PASSWORDS_SCREEN = new PasswordsScreenButtonHandler();
	
	private final BufferedImage BACKGROUND;
	
	public ButtonHandler(BufferedImage background) {
		this.BACKGROUND = background;
	}
	
	protected BufferedImage getBackground() {
		return BACKGROUND;
	}
	
	public void activate() {
		Keyboard.INSTANCE.setButtonHandler(this);
		activateHandler();
	}
	
	public abstract void activateHandler();
	
	public void handleButtonAPressed() {
		SPLASH_SCREEN.activate();
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
