package pi.password.hat.handlers;

import java.awt.Color;

import pi.password.hat.Display;
import pi.password.hat.Display.TextAlign;
import pi.password.hat.Display.TextFormat;
import pi.password.util.ImageUtil;

public class SettingsScreenButtonHandler extends ButtonHandler {
	
	public SettingsScreenButtonHandler() {
		super(ImageUtil.getSettingsBackground());
	}

	@Override
	public void activateHandler() {
		Display.INSTANCE.displayImage(getBackground());
		Display.INSTANCE.displayText("Settings", Color.GREEN, Color.BLACK, TextFormat.SIZE_12_LINE_0, TextAlign.CENTER);
	}

	@Override
	public void handleButtonBPressed() {
		ButtonHandler.VAULT_SCREEN.activate();
	}

	@Override
	public void handleButtonCPressed() {
		ButtonHandler.SPLASH_SCREEN.activate();
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
