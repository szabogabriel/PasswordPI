package pi.password.gui.settings;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.splash.SplashController;
import pi.password.service.settings.SettingsService;

public class SettingsController extends AbstractController {
	
	private SettingsModel model;
	private SettingsView view;
	
	public SettingsController() {
	}
	
	@Override
	public void activateHandler() {
		view = new SettingsView();
		model = new SettingsModel(view, Main.getInstance(SettingsService.class));
		view.paint();
	}
	
	@Override
	public void handleButtonAPressed() {
	}

	@Override
	public void handleButtonBPressed() {
	}

	@Override
	public void handleButtonCPressed() {
	}

	@Override
	public void handleJoystickUpPressed() {
	}

	@Override
	public void handleJoystickDownPressed() {
	}

	@Override
	public void handleJoystickLeftPressed() {
	}

	@Override
	public void handleJoystickRightPressed() {
	}

	@Override
	public void handleJoystickCenterPressed() {
	}

	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();
	}

	@Override
	public void handleButtonBReleased() {
	}

	@Override
	public void handleButtonCReleased() {
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
		model.handleSelectionDecrease();
	}

	@Override
	public void handleJoystickRightReleased() {
		model.handleSelectionIncrease();
	}

	@Override
	public void handleJoystickCenterReleased() {
		model.handleSelectionConfirm();
	}

}
