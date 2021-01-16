package pi.password.gui.components.list;

import java.awt.Color;

import pi.password.service.hat.DisplayService;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public class StringDisplayable implements ListBodyDisplayable{
	
	private String VALUE;
	
	public StringDisplayable(String value) {
		this.VALUE = value;
	}
	
	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		display.displayText(VALUE, Color.WHITE, row, TextAlign.LEFT, fontSelect);		
	}
	
}
