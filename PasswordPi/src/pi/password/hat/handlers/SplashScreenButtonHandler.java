package pi.password.hat.handlers;

import java.awt.Color;

import pi.password.hat.Display;
import pi.password.hat.Display.TextAlign;
import pi.password.hat.Display.TextFormat;
import pi.password.util.ImageUtil;
import pi.password.util.SystemUtil;

public class SplashScreenButtonHandler extends ButtonHandler {
	
	@Override
	public void activateScreen() {
		Display.INSTANCE.displayImage(ImageUtil.getSplashScreen());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void handleJoystickDownPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleJoysticlLeftPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleJoystickRightPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleJoystickCenterPressed() {
		// TODO Auto-generated method stub

	}

}
