package pi.password.gui.list;

import java.awt.image.BufferedImage;

import pi.password.gui.AbstractView;

public class ListView<T extends ListBodyDisplayable> extends AbstractView implements ListModelUpdateListener {
	
	public final int MAX_ROW_NUM = DISPLAY.getMaxAmountOfBodyRows();
	
	private final String TITLE;
	
	private final ListModel<T> MODEL;
	
	private int selected = 0;
	private int offset = 0; 

	public ListView(String title, BufferedImage background, ListModel<T> model) {
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
			if (MODEL.getValues().size() + offset > i) {
				MODEL.getValues().get(i).display(DISPLAY, i, -1);
				
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
	public void dataChanged() {
		paint();
	}

}
