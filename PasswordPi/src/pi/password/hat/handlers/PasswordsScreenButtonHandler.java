package pi.password.hat.handlers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pi.password.Main;
import pi.password.hat.Display;
import pi.password.hat.Display.TextAlign;
import pi.password.hat.Display.TextFormat;
import pi.password.util.ImageUtil;

public class PasswordsScreenButtonHandler extends ButtonHandler {
	
	private static final int ROW_MAX = 8;
	private static final int ROW_HEIGHT = 12;
	
	private String[] passwords;
	private int selected;
	private int guiSelected;

	public PasswordsScreenButtonHandler() {
		super(ImageUtil.getMainBackground());
	}
	
	@Override
	public void activateHandler() {
		passwords = Main.VAULT.listPasswordEntityNames().toArray(new String[] {});
		selected = 0;
		guiSelected = 0;
		
		Display.INSTANCE.displayImage(getBackground());
		Display.INSTANCE.displayText("Passwords", Color.GREEN, Color.BLACK, TextFormat.SIZE_12_LINE_0, TextAlign.CENTER);
		drawSelctionPoint();
	}

	@Override
	public void handleButtonBPressed() {
		ButtonHandler.VAULT_SCREEN.activate();
	}

	@Override
	public void handleButtonCPressed() {
		ButtonHandler.SETTINGS_SCREEN.activate();
	}

	@Override
	public void handleJoystickUpPressed() {
		if (selected > 0) {
			selected--;
		}
		if (guiSelected > 0) {
			guiSelected--;
		}
		drawSelctionPoint();
		drawPasswordNames();
	}

	@Override
	public void handleJoystickDownPressed() {
		if (selected < ROW_MAX) {
			selected++;
		}
		if (guiSelected < ROW_MAX) {
			guiSelected++;
		}
		drawSelctionPoint();
		drawPasswordNames();
	}

	@Override
	public void handleJoysticlLeftPressed() {
		// not defined
	}

	@Override
	public void handleJoystickRightPressed() {
		// not defined
	}

	@Override
	public void handleJoystickCenterPressed() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawSelctionPoint() {
		Display.INSTANCE.drawSelection(7, guiSelected * ROW_HEIGHT);
	}
	
	private void drawPasswordNames() {
		List<String> visibleStrings = new ArrayList<>(10);
		
		for (int i = 0; i < passwords.length; i++) {
			if (i >= selected - guiSelected && i < (selected - guiSelected + ROW_MAX)) {
				visibleStrings.add(passwords[i]);
			}
		}
		
		for (int i = 0; i < visibleStrings.size(); i++) {
			Display.INSTANCE.displayText(visibleStrings.get(i), Color.GREEN, Color.DARK_GRAY, intToTextFormat(i), TextAlign.RIGHT);
		}
	}
	
	private TextFormat intToTextFormat(int data) {
		if (data <= ROW_MAX) {
			return TextFormat.values()[TextFormat.SIZE_12_LINE_2.ordinal() + data];
		}
		return TextFormat.SIZE_12_LINE_10;
	}
	
}
