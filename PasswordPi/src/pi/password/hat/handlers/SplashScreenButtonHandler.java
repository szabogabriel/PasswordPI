package pi.password.hat.handlers;

import java.awt.Color;

import pi.password.hat.Display;
import pi.password.hat.Display.TextAlign;
import pi.password.hat.Display.TextFormat;
import pi.password.util.ImageUtil;
import pi.password.util.SystemUtil;

public class SplashScreenButtonHandler extends ButtonHandler {
	
	public SplashScreenButtonHandler() {
		super(ImageUtil.getSplashScreen());
	}
	
	@Override
	public void activateHandler() {
		Display.INSTANCE.displayImage(getBackground());
		Display.INSTANCE.displayText(SystemUtil.getIpAddress().orElse("Offline"), Color.GREEN, Color.DARK_GRAY, TextFormat.SIZE_8_LINE_15, TextAlign.CENTER);
	}

	@Override
	public void handleButtonBPressed() {
		ButtonHandler.VAULT_SCREEN.activate();
	}

	@Override
	public void handleButtonCPressed() {
		ButtonHandler.SETTINGS_SCREEN.activate();
	}

	@Override
	public void handleJoystickUpPressed() {
		ButtonHandler.PASSWORDS_SCREEN.activate();
	}

	@Override
	public void handleJoystickDownPressed() {
		ButtonHandler.PASSWORDS_SCREEN.activate();
	}

	@Override
	public void handleJoysticlLeftPressed() {
		ButtonHandler.PASSWORDS_SCREEN.activate();
	}

	@Override
	public void handleJoystickRightPressed() {
		ButtonHandler.PASSWORDS_SCREEN.activate();
	}

	@Override
	public void handleJoystickCenterPressed() {
		ButtonHandler.PASSWORDS_SCREEN.activate();
	}

}
