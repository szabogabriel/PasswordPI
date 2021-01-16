package pi.password.gui.list;

import java.awt.Color;
import java.awt.image.BufferedImage;

import pi.password.gui.AbstractView;
import pi.password.service.hat.DisplayServiceWaveshareHat.TextAlign;

public class ListView extends AbstractView implements ListModelUpdateListener {
	
	public final int MAX_ROW_NUM = DISPLAY.getMaxAmountOfBodyRows();
	
	private final String TITLE;
	
	private final ListModel MODEL;
	
	private int selected = 0;
	private int offset = 0; 

	public ListView(String title, BufferedImage background, ListModel model) {
		super(background);
		this.TITLE = title;
		this.MODEL = model;
		model.setListener(this);
	}
	
	private void setSelection(int newSelected) {
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
		DISPLAY.displayTitle(TITLE);
		
		for (int i = 0 ; i < MAX_ROW_NUM; i++) {
			if (MODEL.getValues().length + offset > i) {
				DISPLAY.displayText(MODEL.getValues()[i + offset], Color.WHITE, i, TextAlign.LEFT, -1);
				if (selected == i + offset) {
					DISPLAY.drawSelection(selected - offset);
				}
			}
		}		
	}

	@Override
	public void selectionChanged() {
		int currentSelection = MODEL.getCurrentSelection();
		setSelection(currentSelection);
		paint();
	}

	@Override
	public void contentChanged() {
		paint();
	}

}
