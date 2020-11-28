package pi.password.hat.splash;

import pi.password.hat.AbstractController;
import pi.password.hat.passwords.PasswordController;

public class SplashController extends AbstractController {
	
	private SplashView view;
	private SplashModel model;
	
	@Override
	public void activateHandler() {
		view = new SplashView();
		model = new SplashModel(view);
		view.paint();
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
		new PasswordController().activate();
	}

}
