package pi.password.gui.components.list;

import java.awt.Color;

import pi.password.service.hat.DisplayService;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public class StringDisplayable implements ListBodyDisplayable {
	
	
	private final String VALUE;
	
	private final Color COLOR;
	
	private final boolean SELECTABLE;
	
	private final TextAlign ALIGN;
	
	public StringDisplayable(String value) {
		this(value, Color.WHITE);
	}
	
	public StringDisplayable(String value, Color color) {
		this(value, color, true);
	}
	
	public StringDisplayable(String value, Color color, boolean selectable) {
		this(value, color, selectable, TextAlign.LEFT);
	}
	
	public StringDisplayable(String value, Color color, boolean selectable, TextAlign align) {
		this.VALUE = value;
		this.COLOR = color;
		this.SELECTABLE = selectable;
		this.ALIGN = align;
	}
	
	public String getValue() {
		return VALUE;
	}
	
	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		display.displayText(VALUE, COLOR, row, ALIGN, fontSelect);		
	}

	@Override
	public boolean isSelectable() {
		return SELECTABLE;
	}
}
