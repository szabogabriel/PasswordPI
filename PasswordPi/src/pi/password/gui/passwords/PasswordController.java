package pi.password.gui.passwords;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.settings.SettingsController;
import pi.password.gui.splash.SplashController;

public class PasswordController extends AbstractController {

	private PasswordView view;
	private PasswordModel model;
	
	@Override
	public void activateHandler() {
		view = new PasswordView();
		model = new PasswordModel(getPasswordVault(), view);
	}

	@Override
	public void handleButtonAPressed() {
		new SplashController().activate();
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
		// not defined
	}

	@Override
	public void handleJoystickRightPressed() {
		// not defined
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
		Main.getInstance(SettingsController.class).activate();
	}

	@Override
	public void handleJoystickUpReleased() {
		model.decreaseSelection();
	}

	@Override
	public void handleJoystickDownReleased() {
		model.increaseSelection();
	}

	@Override
	public void handleJoystickLeftReleased() {
		for (int i = 0; i < view.MAX_ROW_NUM; i++) {
			model.decreaseSelection();
		}
	}

	@Override
	public void handleJoystickRightReleased() {
		for (int i = 0; i < view.MAX_ROW_NUM; i++) {
			model.increaseSelection();
		}		
	}

	@Override
	public void handleJoystickCenterReleased() {
		String selected = model.getSelectedPassword();
		if (selected != null) {
			getKeyboardService().sendText(selected);
		}
	}
	
}
