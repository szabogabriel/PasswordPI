package pi.password.gui.settings;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import pi.password.Main;
import pi.password.entity.SettingsEntity;
import pi.password.gui.AbstractView;
import pi.password.service.util.ImageUtilService;

public class SettingsView extends AbstractView {
	
	public final int MAX_ROW_NUM = DISPLAY.getMaxAmountOfBodyRows();
	
	private List<SettingsEntity> settings = Collections.emptyList();
	
	private int selected = 0;
	private int offset = 0; 

	public SettingsView() {
		super(Main.getInstance(ImageUtilService.class).getSettingsBackground());
	}
	
	public void setSettings(List<SettingsEntity> settings) {
		this.settings = settings;
	}
	
	public void updateSettings(SettingsEntity entity) {
		int num = -1;
		for (int i = 0; i < settings.size() && num == -1; i++) {
			if (settings.get(i).getKey() == entity.getKey()) {
				num = i;
			}
		}
		
		if (num > -1) {
			settings.set(num, entity);
		}
	}
	
	public SettingsEntity getCurrentSelection() {
		return settings.get(selected);
	}
	
	public void setSelection(int newSelected) {
		selected = newSelected;
		while (selected - offset >= MAX_ROW_NUM) {
			offset++;
		}
		while (selected < offset) {
			offset--;
		}
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle("Settings");
		
		for (int i = 0; i < MAX_ROW_NUM; i++) {
			if (settings.size() + offset > i) {
				DISPLAY.displayKeyValue(settings.get(i + offset).getKey().getPrintableName(), settings.get(i + offset).getValue(), Color.WHITE, Color.GRAY, i);
				if (selected == i + offset) {
					DISPLAY.drawSelection(selected - offset);
				}
			}
		}
	}

}
