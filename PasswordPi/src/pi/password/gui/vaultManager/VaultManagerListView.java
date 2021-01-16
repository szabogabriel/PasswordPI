package pi.password.gui.vaultManager;

import java.awt.Color;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;

public class VaultManagerListView extends AbstractView {
	
	public final int MAX_ROW_NUM = DISPLAY.getMaxAmountOfBodyRows();
	
	private String[] keys = new String[] {};
	private int selected = 0;
	private int offset = 0; 

	public VaultManagerListView() {
		super(Main.getInstance(ImageUtilService.class).getVaultBackground());
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
		DISPLAY.displayTitle("Vaults");
		
		for (int i = 0 ; i < MAX_ROW_NUM; i++) {
			if (keys.length + offset > i) {
				DISPLAY.displayText(keys[i + offset], Color.WHITE, i, TextAlign.LEFT, -1);
				if (selected == i + offset) {
					DISPLAY.drawSelection(selected - offset);
				}
			}
		}
	}

}
