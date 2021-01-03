package pi.password.gui.dialog;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;
import pi.password.service.util.SystemUtil;

public class DialogView extends AbstractView {
	
	private String [] message;
	private String [] values;
	
	private int selection = 0;
	
	public DialogView() {
		super(Main.getInstance(ImageUtilService.class).getMainBackground());
	}

	public void setMessage(String message) {
		this.message = wordWrapMessage(message);
	}
	
	public void setValues(String [] values) {
		this.values = values;
	}
	
	public void setSelection(int selection) {
		this.selection = selection;
	}

	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(Main.getInstance(SystemUtil.class).getIpAddress().orElse("Offline"));
		
		int maxAmountOfBodyRows = DISPLAY.getMaxAmountOfBodyRows();
		int messageOffset = 0;
		
		int spaceToBeDivided = maxAmountOfBodyRows - message.length - values.length;
		while (spaceToBeDivided > 0) {
			if (messageOffset == 0) {
				messageOffset++;
				spaceToBeDivided--;
			} else {
				spaceToBeDivided--;
			}
		}
		
		for (int i = 0; i < message.length; i++) {
			DISPLAY.displayText(message[i], Color.WHITE, i + messageOffset, TextAlign.CENTER, -1);
		}
		
		for (int i = 0; i < values.length; i++) {
			DISPLAY.displayText(values[i], Color.GRAY, maxAmountOfBodyRows - values.length + i, TextAlign.CENTER, -1);
		}
		
		DISPLAY.drawSelection(maxAmountOfBodyRows - values.length + selection);
	}
	
	private String[] wordWrapMessage(String message) {
		List<String> ret = new ArrayList<>();
		
		String line = "";
		for (String word : message.split("\\s+")) {
			if (!DISPLAY.isFullyVisibleLine(line + " " + word)) {
				ret.add(line);
				line = word;
			} else {
				line += ((line.length() > 1) ? " " : "") + word;
			}
		}
		
		if (line.trim().length() > 1) {
			ret.add(line);
		}
		
		return ret.stream().toArray(String[]::new);
	}

}
