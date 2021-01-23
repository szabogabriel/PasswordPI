package pi.password.gui.splash;

import java.awt.Color;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.components.list.StringDisplayable;
import pi.password.gui.passwords.PasswordController;
import pi.password.gui.settings.SettingsController;
import pi.password.gui.vaultManager.VaultManagerListController;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class SplashController extends AbstractController {
	
	private final ImageUtilService IMAGE_SERVICE;
	private final SystemUtil SYSTEM_UTIL;
	
	private final StringDisplayable EMPTY_DISPLAYABLE = new StringDisplayable("", Color.WHITE, false, TextAlign.CENTER);
	private final StringDisplayable PASSWORDS_DISPLAYABLE = new StringDisplayable("Passswords", Color.WHITE, true, TextAlign.CENTER);
	private final StringDisplayable VAULT_DISPLAYABLE = new StringDisplayable("Vault", Color.WHITE, true, TextAlign.CENTER);
	private final StringDisplayable SETTINGS_DISPLAYABLE = new StringDisplayable("Settings", Color.WHITE, true, TextAlign.CENTER);
	
	private ListView<StringDisplayable> view;
	private ListModel<StringDisplayable> model;
	
	public SplashController(ImageUtilService imageService, SystemUtil systemUtil) {
		this.IMAGE_SERVICE = imageService;
		this.SYSTEM_UTIL = systemUtil;
	}
	
	public void turnOffDisplay() {
		//TODO: find another way to do this.
		//model.setBacklight(false);
	}
	
	@Override
	public void activateHandler() {
		model = new ListModel<StringDisplayable>();
		model.addData(PASSWORDS_DISPLAYABLE);
		model.addData(EMPTY_DISPLAYABLE);
		model.addData(VAULT_DISPLAYABLE);
		model.addData(EMPTY_DISPLAYABLE);
		model.addData(SETTINGS_DISPLAYABLE);
		view = new ListView<StringDisplayable>(SYSTEM_UTIL.getIpAddress().orElse("Offline"), IMAGE_SERVICE.getMainBackground(), model);
		view.paint();
	}
	
	@Override
	public void reactivateHandler() {
		view.paint();
	}
	
	@Override
	public void handleButtonAReleased() {
		//TODO: handle backlight settings
//		model.setBacklight(!model.getBacklight());
	}

	@Override
	public void handleButtonCReleased() {
		Main.lock();
	}

	@Override
	public void handleJoystickUpReleased() {
		turnOnBacklight();
		model.decreaseSelection();
	}

	@Override
	public void handleJoystickDownReleased() {
		turnOnBacklight();
		model.increaseSelection();
	}

	@Override
	public void handleJoystickCenterReleased() {
		turnOnBacklight();
		StringDisplayable selected = model.getSelectedValue();
		
		if (PASSWORDS_DISPLAYABLE.equals(selected)) {
			Main.getInstance(PasswordController.class).activate();
		} else
		if (VAULT_DISPLAYABLE.equals(selected)) {
			Main.getInstance(VaultManagerListController.class).activate();
		} else
		if (SETTINGS_DISPLAYABLE.equals(selected)) {
			Main.getInstance(SettingsController.class).activate();
		}
	}
	
	private void turnOnBacklight() {
		//TODO: find a way to do this
//		if (!model.getBacklight()) {
//			model.setBacklight(true);
//		}
	}

}
