package pi.password.gui.settings;

import java.util.List;
import java.util.stream.Collectors;

import pi.password.entity.SettingsEntity;
import pi.password.gui.AbstractModel;
import pi.password.service.settings.SettingsService;

public class SettingsModel extends AbstractModel {
	
	private final SettingsView VIEW;
	private final SettingsService SETTINGS_SERVICE;
	private int selected;
	
	public SettingsModel(SettingsView view, SettingsService service) {
		this.VIEW = view;
		this.SETTINGS_SERVICE = service;
		
		selected = 0;
		
		VIEW.setSettings(getSortedSettings());
		VIEW.setSelection(selected);
		VIEW.paint();
	}
	
	public void increaseSelection() {
		if (selected + 1 < getSortedSettings().size()) {
			selected++;
			updateViewSelection();
		}
	}
	
	public void decreaseSelection() {
		if (selected > 0) {
			selected--;
			updateViewSelection();
		}
	}
	
	public void handleSelectionConfirm() {
		SettingsEntity settings = VIEW.getCurrentSelection();
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
			updateSetting(newSettings);
		}
	}
	
	public void handleSelectionIncrease() {
		SettingsEntity settings = VIEW.getCurrentSelection();
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
			updateSetting(newSettings);
		}
	}
	
	public void handleSelectionDecrease() {
		SettingsEntity settings = VIEW.getCurrentSelection();
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
			updateSetting(newSettings);
		}
	}
	
	private void updateSetting(SettingsEntity newSettings) {
		SETTINGS_SERVICE.setValue(newSettings);
		VIEW.updateSettings(newSettings);
		VIEW.paint();
	}
	
	private void updateViewSelection() {
		VIEW.setSelection(selected);
		VIEW.paint();
	}
	
	private List<SettingsEntity> getSortedSettings() {
		return SETTINGS_SERVICE.getSettings()
			.stream()
			.sorted((s1, s2) -> s1.getKey().ordinal() - s2.getKey().ordinal())
			.collect(Collectors.toList());
	}
	
}
