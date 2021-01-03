package pi.password.gui.splash;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.passwords.PasswordController;
import pi.password.gui.settings.SettingsController;

public class SplashController extends AbstractController {
	
	private SplashView view;
	private SplashModel model;
	
	public void turnOffDisplay() {
		model.setBacklight(false);
	}
	
	@Override
	public void activateHandler() {
		view = new SplashView();
		model = new SplashModel(view);
		view.paint();
	}
	
	@Override
	public void handleButtonAPressed() {
		
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
		//TODO
		
	}

	@Override
	public void handleJoystickDownPressed() {
		//TODO
		
	}

	@Override
	public void handleJoystickLeftPressed() {
		//TODO
		
	}

	@Override
	public void handleJoystickRightPressed() {
		//TODO
		
	}

	@Override
	public void handleJoystickCenterPressed() {
		//TODO
		
	}

	@Override
	public void handleButtonAReleased() {
		model.setBacklight(!model.getBacklight());
	}

	@Override
	public void handleButtonBReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleButtonCReleased() {
		turnOnBacklight();
		Main.getInstance(SettingsController.class).activate();
	}

	@Override
	public void handleJoystickUpReleased() {
		turnOnBacklight();
		Main.getInstance(PasswordController.class).activate();		
	}

	@Override
	public void handleJoystickDownReleased() {
		turnOnBacklight();
		Main.getInstance(PasswordController.class).activate();		
	}

	@Override
	public void handleJoystickLeftReleased() {
		turnOnBacklight();
		Main.getInstance(PasswordController.class).activate();		
	}

	@Override
	public void handleJoystickRightReleased() {
		turnOnBacklight();
		Main.getInstance(PasswordController.class).activate();		
	}

	@Override
	public void handleJoystickCenterReleased() {
		turnOnBacklight();
		Main.getInstance(PasswordController.class).activate();
	}
	
	private void turnOnBacklight() {
		if (!model.getBacklight()) {
			model.setBacklight(true);
		}
	}

}
