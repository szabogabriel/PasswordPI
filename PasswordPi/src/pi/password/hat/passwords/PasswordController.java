package pi.password.hat.passwords;

import pi.password.Main;
import pi.password.config.Config;
import pi.password.hat.AbstractController;
import pi.password.hat.splash.SplashController;
import pi.password.manager.filesystem.FilesystemPasswordVault;

public class PasswordController extends AbstractController {

	private PasswordView view;
	private PasswordModel model;

	@Override
	public void activateHandler() {
		view = new PasswordView();
		model = new PasswordModel(new FilesystemPasswordVault(Config.VAULT_FILESYSTEM_FILE.toString()), view);
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
		// TODO Auto-generated method stub
		String selected = model.getSelectedPassword();
		if (selected != null) {
			Main.keyboard.sendText(selected);
		}
	}
	
}
