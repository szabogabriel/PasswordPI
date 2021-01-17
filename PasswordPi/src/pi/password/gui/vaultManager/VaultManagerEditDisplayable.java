package pi.password.gui.vaultManager;

import java.awt.Color;

import pi.password.gui.components.list.ListBodyDisplayable;
import pi.password.service.hat.DisplayService;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public class VaultManagerEditDisplayable implements ListBodyDisplayable {
	
	public static enum Type {
		KEY,
		VALUE,
		PASSWORD,
		OPTION,
		;
	}
	
	private String value;
	
	private final Type TYPE;
	
	public VaultManagerEditDisplayable(String value, Type type) {
		this.value = value;
		this.TYPE = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void display(DisplayService display, int row, int fontSelect) {
		switch (TYPE) {
		case KEY:
			display.displayText(value, Color.GREEN, row, TextAlign.LEFT, -1);
			break;
		case PASSWORD:
			display.displayText(toPasswordString(value), Color.WHITE, row, TextAlign.LEFT, -1);
			break;
		case VALUE:
			display.displayText(value, Color.WHITE, row, TextAlign.LEFT, -1);
			break;
		case OPTION:
			display.displayText(value, Color.GREEN, row, TextAlign.CENTER, -1);
			break;
		}
	}
	
	private String toPasswordString(String data) {
		return data.replaceAll(".", "*");
	}

	@Override
	public boolean isSelectable() {
		return TYPE == Type.VALUE || TYPE == Type.OPTION || TYPE == Type.PASSWORD;
	}

}
