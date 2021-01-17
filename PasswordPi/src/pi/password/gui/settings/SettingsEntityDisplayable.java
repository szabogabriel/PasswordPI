package pi.password.gui.settings;

import java.awt.Color;

import pi.password.entity.SettingsEntity;
import pi.password.gui.components.list.ListBodyDisplayable;
import pi.password.service.hat.DisplayService;

public class SettingsEntityDisplayable implements ListBodyDisplayable {
	
	private final SettingsEntity ENTITY;
	
	public SettingsEntityDisplayable(SettingsEntity data) {
		ENTITY = data;
	}
	
	public SettingsEntity getEntity() {
		return ENTITY;
	}

	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		display.displayKeyValue(ENTITY.getKey().getPrintableName(), ENTITY.getValue(), Color.WHITE, Color.GRAY, row);		
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

}
