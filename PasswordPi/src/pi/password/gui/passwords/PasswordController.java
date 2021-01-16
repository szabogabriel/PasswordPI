package pi.password.gui.passwords;

import java.util.List;
import java.util.stream.Collectors;

import pi.password.Main;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.components.list.StringDisplayable;
import pi.password.gui.settings.SettingsController;
import pi.password.gui.splash.SplashController;
import pi.password.service.password.PasswordVaultService;
import pi.password.service.util.ImageUtilService;

public class PasswordController extends AbstractController {

	private ListView<StringDisplayable> view;
	private ListModel<StringDisplayable> model;
	
	private PasswordVaultService PASSWORDS;
	
	public PasswordController(PasswordVaultService passwords) {
		this.PASSWORDS = passwords;
		model = new ListModel<>();
		view = new ListView<>("Passwords", Main.getInstance(ImageUtilService.class).getMainBackground(), model);
		model.setData(getSortedPasswordKeys().stream().map(d -> new StringDisplayable(d)).collect(Collectors.toList()));
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
	public void handleButtonAReleased() {
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
		String nameSelected = getSortedPasswordKeys().get(model.getCurrentSelection());
		String ret = PASSWORDS
				.listPasswordEntities()
				.parallelStream()
				.filter(e -> e.getName().equals(nameSelected))
				.map(e -> e.getPassword())
				.findAny()
				.orElse(null);
		return ret;
	}
	
	private List<String> getSortedPasswordKeys() {
		return PASSWORDS.listPasswordEntityNames().stream().sorted().collect(Collectors.toList());
	}
}
