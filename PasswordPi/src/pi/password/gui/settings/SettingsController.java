package pi.password.gui.settings;

import java.util.List;
import java.util.stream.Collectors;

import pi.password.Main;
import pi.password.entity.SettingsEntity;
import pi.password.enums.LocalizedTexts;
import pi.password.gui.AbstractController;
import pi.password.gui.components.list.ListModel;
import pi.password.gui.components.list.ListView;
import pi.password.gui.splash.SplashController;
import pi.password.service.settings.SettingsService;
import pi.password.service.settings.SettingsUpdatePropagatorService;
import pi.password.service.util.ImageUtilService;

public class SettingsController extends AbstractController {
	
	private ListModel<SettingsEntityDisplayable> model;
	private ListView<SettingsEntityDisplayable> view;
	
	private final SettingsService SETTINGS_SERVICE;
	private final SettingsUpdatePropagatorService SETTINGS_UPDATE_PROPAGATOR_SERVICE;
	
	public SettingsController(SettingsService settings, SettingsUpdatePropagatorService settingsUpdate) {
		this.SETTINGS_SERVICE = settings;
		this.SETTINGS_UPDATE_PROPAGATOR_SERVICE = settingsUpdate;
		
		model = new ListModel<SettingsEntityDisplayable>();
		view = new ListView<SettingsEntityDisplayable>(LocalizedTexts.VIEW_SETTINGS_TITLE.toString(), Main.getInstance(ImageUtilService.class).getSettingsBackground(), model);
		
		model.setData(getSortedSettings().stream().map(e -> new SettingsEntityDisplayable(e)).collect(Collectors.toList()));
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
		saveSettings();
		Main.getInstance(SplashController.class).activate();
		
	}

	@Override
	public void handleButtonCReleased() {
		saveSettings();
		Main.lock();
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
		SettingsEntity settings = model.getSelectedValue().getEntity();
		int currentOrder = model.getCurrentSelection();
		SettingsEntity newSettings = null;
		
		switch (settings.getKey().getType()) {
		case NUMBERS:
			newSettings = new SettingsEntity(settings.getKey(), Integer.toString(settings.getValueInteger() - 1));
			break;
		case OPTIONS:
			String[] vals = settings.getKey().getValues();
			int currentValIndex = -1;
			while (!vals[++currentValIndex].equals(settings.getValue()));
			if (currentValIndex - 1 >= 0) {
				newSettings = new SettingsEntity(settings.getKey(), vals[currentValIndex - 1]);
			}
			break;
		case PLAINTEXT:
			// TODO: handle text input
			break;
		default:
			break;
		}
		
		if (newSettings != null) {
			model.setData(new SettingsEntityDisplayable(newSettings), currentOrder);
			SETTINGS_UPDATE_PROPAGATOR_SERVICE.handleSettingUpdate(newSettings);
		}
	}

	@Override
	public void handleJoystickRightReleased() {
		SettingsEntity settings = model.getSelectedValue().getEntity();
		int currentOrder = model.getCurrentSelection();
		SettingsEntity newSettings = null;
		
		switch (settings.getKey().getType()) {
		case NUMBERS:
			newSettings = new SettingsEntity(settings.getKey(), Integer.toString(settings.getValueInteger() + 1));
			break;
		case OPTIONS:
			String[] vals = settings.getKey().getValues();
			int currentValIndex = -1;
			while (!vals[++currentValIndex].equals(settings.getValue()));
			if (currentValIndex + 1 < vals.length) {
				newSettings = new SettingsEntity(settings.getKey(), vals[currentValIndex + 1]);
			}
			break;
		case PLAINTEXT:
			// TODO: handle text input
			break;
		default:
			break;
		}
		
		if (newSettings != null) {
			model.setData(new SettingsEntityDisplayable(newSettings), currentOrder);
			SETTINGS_UPDATE_PROPAGATOR_SERVICE.handleSettingUpdate(newSettings);
		}
	}

	@Override
	public void handleJoystickCenterReleased() {
		SettingsEntity settings = model.getSelectedValue().getEntity();
		int currentOrder = model.getCurrentSelection();
		SettingsEntity newSettings = null;
		
		switch (settings.getKey().getType()) {
		case NUMBERS:
			break;
		case OPTIONS:
			String[] vals = settings.getKey().getValues();
			if (vals.length == 2) {
				if (vals[0].equals(settings.getValue())) {
					newSettings = new SettingsEntity(settings.getKey(), vals[1]);
				} else {
					newSettings = new SettingsEntity(settings.getKey(), vals[0]);
				}
			}
			break;
		case PLAINTEXT:
			// TODO: handle text input finish
			break;
		default:
			break;
		}
		
		if (newSettings != null) {
			model.setData(new SettingsEntityDisplayable(newSettings), currentOrder);
			SETTINGS_UPDATE_PROPAGATOR_SERVICE.handleSettingUpdate(newSettings);
		}
	}
	
	private void saveSettings() {
		model.getValues().stream()
			.map(e -> e.getEntity())
			.forEach(e -> SETTINGS_SERVICE.setValue(e));
	}
	
	private List<SettingsEntity> getSortedSettings() {
		return SETTINGS_SERVICE.getSettings()
			.stream()
			.sorted((s1, s2) -> s1.getKey().ordinal() - s2.getKey().ordinal())
			.collect(Collectors.toList());
	}

}
