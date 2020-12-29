package pi.password.gui.settings;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.splash.SplashController;

public class SettingsController extends AbstractController {
	
	private SettingsModel model;
	private SettingsView view;
	
	@Override
	public void activateHandler() {
		model = new SettingsModel();
		view = new SettingsView();
	}
	
	@Override
	public void handleButtonAPressed() {
		//TODO
		
	}

	@Override
	public void handleButtonBPressed() {
		//TODO
		
	}

	@Override
	public void handleButtonCPressed() {
		//TODO
		
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
	public void handleJoystickLeftPressed() {
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

	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();
	}

	@Override
	public void handleButtonBReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleButtonCReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleJoystickUpReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleJoystickDownReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleJoystickLeftReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleJoystickRightReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleJoystickCenterReleased() {
		// TODO Auto-generated method stub
		
	}

}
