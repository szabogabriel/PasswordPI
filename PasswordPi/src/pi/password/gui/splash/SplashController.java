package pi.password.gui.splash;

import pi.password.gui.AbstractController;
import pi.password.gui.passwords.PasswordController;

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
		// TODO Auto-generated method stub
		
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
		new PasswordController().activate();		
	}

	@Override
	public void handleJoystickDownReleased() {
		new PasswordController().activate();		
	}

	@Override
	public void handleJoystickLeftReleased() {
		new PasswordController().activate();		
	}

	@Override
	public void handleJoystickRightReleased() {
		new PasswordController().activate();		
	}

	@Override
	public void handleJoystickCenterReleased() {
		new PasswordController().activate();
	}

}
