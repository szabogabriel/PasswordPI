package pi.password.gui.vaultManager;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.settings.SettingsController;
import pi.password.gui.splash.SplashController;

public class VaultManagerController extends AbstractController {
	
	private VaultManagerModel model;
	private VaultManagerListView view_list;
	private VaultManagerEditView view_edit;
	
	@Override
	public void activateHandler() {
		view_list = new VaultManagerListView();
		view_edit = new VaultManagerEditView();
		model = new VaultManagerModel(getDialogService(), getPasswordVault(), getPasswordAlphabet(), view_list, view_edit);
		view_list.paint();
	}
	
	@Override
	public void reactivateHandler() {
		model.reactivate();
	}
	
	@Override
	public void handleButtonAReleased() {
		Main.getInstance(SplashController.class).activate();
	}

	@Override
	public void handleButtonBReleased() {
		Main.getInstance(SplashController.class).activate();
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
		model.prevCharacter();
	}

	@Override
	public void handleJoystickRightReleased() {
		model.nextCharacter();
	}

	@Override
	public void handleJoystickCenterReleased() {
		model.confirmSelection();
	}

}
