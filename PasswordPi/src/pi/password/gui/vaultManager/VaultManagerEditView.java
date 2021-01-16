package pi.password.gui.vaultManager;

import java.awt.Color;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;

public class VaultManagerEditView extends AbstractView {
	
	private String name;
	private String password;
	
	private int currentlyEditedRow = -1;
	private int currentlyEditedChar = -1;
	
	public VaultManagerEditView() {
		super(Main.getInstance(ImageUtilService.class).getVaultBackground());
	}
	
	public void setAccountName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCurrentlyEditedRow(int row) {
		currentlyEditedRow = row;
	}
	
	public void setCurrentlyEditedChar(int charOrder) {
		currentlyEditedChar = charOrder;
	}

	@Override
	public void paint() {
		
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle("Edit");
		
		DISPLAY.displayText("Account", Color.WHITE, 0, TextAlign.LEFT, -1);
		DISPLAY.displayText(name, Color.GREEN, 1, TextAlign.LEFT, (currentlyEditedRow == 1) ? currentlyEditedChar : -1);
		DISPLAY.displayText("Password", Color.WHITE, 3, TextAlign.LEFT, -1);
		DISPLAY.displayText(password, Color.GREEN, 4, TextAlign.LEFT, (currentlyEditedRow == 4) ? currentlyEditedChar : -1);
	}

}
