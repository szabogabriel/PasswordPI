package pi.password.gui.components.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import pi.password.Main;
import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;
import pi.password.service.util.ImageUtilService;

public class TextEditorView extends AbstractView implements TextEditorChangeListener {

	private final TextEditorModel MODEL;
	private final String TITLE;
	private final String[] DESCRIPTION;
	
	public TextEditorView(String title, String description, TextEditorModel model) {
		super(Main.getInstance(ImageUtilService.class).getMainBackground());
		
		this.TITLE = title;
		this.DESCRIPTION = wordWrapMessage(description);
		this.MODEL = model;
		this.MODEL.setListener(this);
	}
	
	@Override
	public void paint() {
		DISPLAY.displayImage(getBackground());
		DISPLAY.displayTitle(TITLE);
		
		int infoMessageOffset = (DESCRIPTION.length > 2) ? 0 : 1;
		
		for (int i = 0; i < DESCRIPTION.length; i++) {
			DISPLAY.displayText(DESCRIPTION[i], Color.WHITE, i + infoMessageOffset, TextAlign.CENTER, -1);
		}
		
		int inputStart = infoMessageOffset + DESCRIPTION.length + 1;
		
		int currentSelection = MODEL.getCurrentSelectedCharPosition() + 1;
		String editorData = MODEL.getCurrentContent();
		if (MODEL.getType() == TextEditorType.PASSWORD) {
			editorData = rewriteToPassword(editorData, currentSelection);
		}
		String[] displayEditData = wordWrapMessage(editorData);
		
		int displayed = 0;
		boolean selectionAlreadyDisplayed = false;
		
		for (int i = 0; i < displayEditData.length; i++) {
			int lineSelection = 0;
			displayed += displayEditData[i].length();
		
			if (!selectionAlreadyDisplayed && displayed >= currentSelection) {
				selectionAlreadyDisplayed = true;
				lineSelection = displayEditData[i].length() - (displayed - currentSelection);
			}
				
			DISPLAY.displayText(displayEditData[i], Color.GRAY, inputStart + i, TextAlign.LEFT, lineSelection - 1);
		}
	}
	
	private String rewriteToPassword(String input, int selected) {
		char [] ret = input.toCharArray();
		
		for (int i = 0; i < ret.length; i++) {
			if (i + 1 != selected) {
				ret[i] = '*';
			}
		}
		
		return new String(ret);
	}
	
	private String[] wordWrapMessage(String message) {
		List<String> ret = new ArrayList<>();
		
		String line = "";
		for (String word : message.split("\\s+")) {
			if (!DISPLAY.isFullyVisibleLine(line + word + " ")) {
				if (line.trim().length() > 0) {
					ret.add(line);
				}
				if (DISPLAY.isFullyVisibleLine(word)) {
					line = word + " ";
				} else {
					// Word without whitespace is longer than the line, so we print the leftover
					// string in the next line. This part is currently splitting it only into two
					// lines. Since there is no more space on the currently used display, this is
					// safe for now, need change if more is necessary.
					int toBeTrimmedOf = 0;
					while (!DISPLAY.isFullyVisibleLine(word.substring(0, word.length() - (++toBeTrimmedOf))));
					ret.add(word.substring(0, word.length() - toBeTrimmedOf));
					line = word.substring(word.length() - toBeTrimmedOf) + " ";
				}
			} else {
				line += ((line.length() > 1) ? " " : "") + word + " ";
			}
		}
		
		if (line.trim().length() > 0) {
			ret.add(line);
		}
		
		return ret.stream().toArray(String[]::new);
	}

	@Override
	public void contentChanged() {
		paint();
	}

	@Override
	public void selectionChanged() {
		paint();
	}

}
