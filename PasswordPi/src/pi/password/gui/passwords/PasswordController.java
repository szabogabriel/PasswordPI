package pi.password.gui.passwords;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.list.ListModel;
import pi.password.gui.list.ListView;
import pi.password.gui.settings.SettingsController;
import pi.password.gui.splash.SplashController;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.util.ImageUtilService;

public class PasswordController extends AbstractController {

	private ListView view;
	private ListModel model;
	
	private PasswordVaultService PASSWORDS;
	
	public PasswordController(PasswordVaultService passwords) {
		this.PASSWORDS = passwords;
		model = new ListModel();
		view = new ListView("Passwords", Main.getInstance(ImageUtilService.class).getMainBackground(), model);
		model.setValues(getSortedPasswordKeys());
	}
	
	@Override
	public void activateHandler() {
		view.paint();
	}
	
	@Override
	public void reactivateHandler() {
		view.paint();
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
		String selected = getSelectedPassword();
		if (selected != null) {
			getKeyboardService().sendText(selected);
		}
	}
	
	private String getSelectedPassword() {
		String nameSelected = getSortedPasswordKeys()[model.getCurrentSelection()];
		String ret = PASSWORDS
				.listPasswordEntities()
				.parallelStream()
				.filter(e -> e.getName().equals(nameSelected))
				.map(e -> e.getPassword())
				.findAny()
				.orElse(null);
		return ret;
	}
	
	private String[] getSortedPasswordKeys() {
		return PASSWORDS.listPasswordEntityNames().stream().sorted().toArray(String[]::new);
	}
}
