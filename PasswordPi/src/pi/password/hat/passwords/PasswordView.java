package pi.password.hat.passwords;

import java.awt.Color;

import pi.password.hat.AbstractView;
import pi.password.hat.Display;
import pi.password.hat.Display.TextAlign;
import pi.password.util.ImageUtil;

public class PasswordView extends AbstractView {

	private final int ROW_HEIGHT = 12;
	private final int MAX_ROW_NUM = DISPLAY.getMaxAmountOfBodyRows();
	
	private String[] keys = new String[] {};
	private int selected = 0;
	private int offset = 0; 
	
	public PasswordView() {
		super(ImageUtil.getMainBackground());
	}
	
	public void setKeys(String[] keys) {
		this.keys = keys;
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
		DISPLAY.displayTitle("Passwords", false);
		
		for (int i = 0 ; i < MAX_ROW_NUM; i++) {
			if (keys.length + offset > i) {
				DISPLAY.displayText(keys[i + offset], Color.WHITE, i, TextAlign.LEFT);
				if (selected == i + offset) {
					DISPLAY.drawSelection(selected - offset);
				}
			}
		}
	}
}
