package pi.password.gui.components.list;

import java.awt.Color;

import pi.password.service.hat.DisplayService;

public class KeyValueDisplayable implements ListBodyDisplayable {
	
	private final String KEY;
	private final String VALUE;
	
	public KeyValueDisplayable(String key, String value) {
		this.KEY = key;
		this.VALUE = value;
	}

	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		display.displayKeyValue(KEY, VALUE, Color.WHITE, Color.GRAY, row);
	}

}
