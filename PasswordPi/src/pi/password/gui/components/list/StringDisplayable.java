package pi.password.gui.components.list;

import java.awt.Color;

import pi.password.service.hat.DisplayService;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public class StringDisplayable implements ListBodyDisplayable {
	
	
	private final String VALUE;
	
	private final Color COLOR;
	
	private final boolean SELECTABLE;
	
	public StringDisplayable(String value) {
		this(value, Color.WHITE);
	}
	
	public StringDisplayable(String value, Color color) {
		this(value, color, true);
	}
	
	public StringDisplayable(String value, Color color, boolean selectable) {
		this.VALUE = value;
		this.COLOR = color;
		this.SELECTABLE = selectable;
	}
	
	public String getValue() {
		return VALUE;
	}
	
	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		display.displayText(VALUE, COLOR, row, TextAlign.LEFT, fontSelect);		
	}

	@Override
	public boolean isSelectable() {
		return SELECTABLE;
	}
}
